package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.factories.LabelFactory;
import com.mygdx.game.factories.TableFactory;
import com.mygdx.game.factories.TextFieldFactory;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.socket.emitter.Emitter;

public class MultiPlayerHostMenu extends MenuScreen {
    Table table;
    Server server;
    Label gameId;
    TextField nameText;
    ArrayList<JSONObject> players = new ArrayList<>();
    GoToButton goToGameScreen;
    String[] gameData;

    public MultiPlayerHostMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = BACK_GROUND_TEXTURE_EMPTY;
        server = Server.getInstance();
        String[] data = server.newGame(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject message = (JSONObject) args[0];
                    String type = message.getString("type");

                    if (type.equals("join_game")) {
                        JSONObject player = message.getJSONObject("data").getJSONObject("player");
                        players.add(player);
                        table.add(String.valueOf(players.size())).padRight(50);
                        table.add(player.getString("name"));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        this.gameId.setText("GameID: " + data[1]);
    }

    protected List<MenuButton> createButtons() {
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        goToGameScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("START", new Vector2(buttonX, Gdx.graphics.getHeight() * (float) 0.1 - buttonHeight / 2), GameScreen.class);
        Collections.addAll(list, goBack, goToGameScreen);
        return list;
    }

    @Override
    public void goBack() {
        goTo(previousMenu);
        this.server.clearListeners();
    }


    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        nameText = TextFieldFactory.create("");
        table = TableFactory.create();
        gameId = LabelFactory.create("Game ID");
        list.add(table);

        table.add(gameId).colspan(2);
        table.row();
        table.add("Player name").colspan(2).padTop(50);
        table.row();
        table.add(nameText).colspan(2).width(Gdx.graphics.getWidth() / 5).fill();
        table.row();
        table.add("Other players").colspan(2).padTop(20);
        table.row();
        table.add("#").padRight(50);
        table.add("Name");
        table.row();
        table.setFillParent(true);
        table.center();
        table.padBottom(30);

        return list;
    }

    public void goToGameScreen(JSONArray players) {
        stopMusic();
        MainGame main = MainGame.getSingleton();
        GameScreen gameScreen = new GameScreen(main, true, false);
        gameScreen.setPlayers(players);
        gameScreen.setGameData(gameData[0], gameData[1]);
        gameScreen.startMusic();
        gameScreen.setPreviousMenu(this);
        goTo(gameScreen);
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameScreen.class)) {
            goToGameScreen.updateText("Waiting...");
            gameData = this.server.startGame(this.nameText.getText(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Gdx.app.postRunnable(() -> {
                        try {
                            JSONObject data = ((JSONObject) args[0]).getJSONObject("data");

                            JSONArray players = data.getJSONArray("players");
                            MultiPlayerHostMenu.this.goToGameScreen(players);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                    server.removeListener("start_game", this);
                    server.removeListener("end_game", this);
                }
            });
        }
    }

}