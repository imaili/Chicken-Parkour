package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MainGame;
import com.mygdx.game.factories.LabelFactory;
import com.mygdx.game.factories.TableFactory;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.Font;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.socket.emitter.Emitter;

public class GameOverMenu extends PauseMenu {
    private HashMap<String, Label> scores;
    protected static final Texture GAME_OVER_BACK_GROUND_TEXTURE = MainGame.getSingleton().getAssetManager().get(Constants.BACKGROUND_GAME_OVER_PATH, Texture.class);
    private Server server;
    GoToButton exit;

    @Override
    protected List<MenuButton> createButtons() {

        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();

        if (!((GameScreen) previousMenu).isMultiPlayer()) {
            list.add(DEFAULT_TEXT_BUTTON_FACTORY.createGoBackButton("Play again", new Vector2(buttonX, Gdx.graphics.getHeight() / 2 - buttonHeight / 2)));
        }
        exit = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Exit", new Vector2(buttonX, Gdx.graphics.getHeight() / 2 - 2 * buttonHeight), MainMenu.class);

        if (((GameScreen) previousMenu).isMultiPlayer() && !((GameScreen) previousMenu).isJoinedMultiplayer()) {
            exit.updateText("Waiting for other players...");
            exit.disable();
        }

        list.add(exit);
        return list;
    }

    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        //Skin skin = MainGame.getSingleton().getAssetManager().get(Constants.TABLE_SKIN);

        GameScreen game = ((GameScreen) previousMenu);

        if (game.isMultiPlayer()) {
            Table t = TableFactory.create();
            scores = new HashMap<>();
            list.add(t);
            t.add("Player");
            t.add("Score");
            t.row();

            float height = Gdx.graphics.getHeight() / 10;
            float width = Gdx.graphics.getWidth() / 4;

            t.setPosition(Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() / 2);
            t.setWidth(width);
            t.setHeight(height);

            JSONArray players = game.getPlayers();
            for (int i = 0; i < players.length(); i++) {
                try {
                    JSONObject player = players.getJSONObject(i);
                    t.add(player.getString("name"));
                    Label score;
                    if (player.getString("id").equals(game.getPlayerId())){
                        score = LabelFactory.create(String.valueOf(game.getScore()));
                    }
                    else if (player.has("score")) {
                        score = LabelFactory.create(String.valueOf(player.getInt("score")));
                    } else {
                        score = LabelFactory.create("playing...");
                    }
                    scores.put(player.getString("id"), score);
                    t.add(score);
                    t.row();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (allPlayersDone()) {
                exit.updateText("Exit");
                exit.enable();
            } else {
                this.server = Server.getInstance();

                game.removeEndGameListener();

                this.server.listenForEndGame(args -> {
                    JSONObject message = (JSONObject) args[0];
                    String player_id = null;
                    try {
                        player_id = message.getString("player_id");
                        JSONObject data = message.getJSONObject("data");
                        Label l = scores.get(player_id);
                        l.setText(data.getString("score"));

                        if (allPlayersDone()) {
                            exit.updateText("Exit");
                            exit.enable();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        Label title = LabelFactory.create("Score: " + game.getScore());
        //int screenSize = Gdx.graphics.getWidth() /2;
        //float screenHight = Gdx.graphics.getHeight() * (float)0.85;
        float labelSize = title.getWidth() / 2;
        title.setPosition(Gdx.graphics.getWidth() / 2 - labelSize, Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 10);
        list.add(title);
        return list;
    }

    private boolean allPlayersDone() {
        JSONArray players = ((GameScreen) previousMenu).getPlayers();
        for (int i = 0; i < players.length(); i++) {
            try {
                for (Label score :
                        scores.values()) {
                    if (score.getText().equals("playing...")) {
                        return false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public GameOverMenu(GameScreen gameScreen) {
        super(gameScreen, GAME_OVER_BACK_GROUND_TEXTURE);
    }

    @Override
    public void goBack() {
        goTo(GameScreen.class);
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        //previousMenu.dispose();
        super.goTo(menu);
        if (menu.equals(GameScreen.class))
            goToGameScreen();
    }

    @Override
    public void goToMainMenu() {
        goTo(new MainMenu());
    }

    public void goToGameScreen() {
        if (!((GameScreen) previousMenu).isMultiPlayer()) {
            stopMusic();
            MainGame main = MainGame.getSingleton();
            GameScreen gameScreen = new GameScreen(main);
            main.setGame(gameScreen);
            gameScreen.startMusic();
            gameScreen.setMultiPlayer(false);
            gameScreen.setJoinedMultiplayer(false);
            gameScreen.setPreviousMenu(((GameScreen) previousMenu).getPreviousMenu());
            goTo(gameScreen);
        } else {
            goTo(((GameScreen) previousMenu).getPreviousMenu());
        }
    }

    @Override
    public void startMusic() {
        ((GameScreen) previousMenu).getPreviousMenu().startMusic();
    }

    @Override
    public void stopMusic() {
        ((GameScreen) previousMenu).getPreviousMenu().stopMusic();
    }

}
