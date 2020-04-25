package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.factories.TableFactory;
import com.mygdx.game.factories.TextFieldFactory;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.socket.emitter.Emitter;

public class MultiPlayerJoinMenu extends MenuScreen {
    Table table;
    Server server;
    GoToButton goToGameScreen;
    TextField playerName;
    TextField gameId;
    String[] gameData;

    public MultiPlayerJoinMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = BACK_GROUND_TEXTURE_EMPTY;
        server = Server.getInstance();
    }

    protected List<MenuButton> createButtons() {
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        goToGameScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Join game", new Vector2(buttonX, Gdx.graphics.getHeight() * (float) 0.1 - buttonHeight / 2), GameScreen.class);
        Collections.addAll(list, goBack, goToGameScreen);
        return list;
    }


    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        playerName = TextFieldFactory.create("");
        gameId = TextFieldFactory.create("");
        table = TableFactory.create();
        list.add(table);

        table.add("Player name");
        table.row();
        table.add(playerName);
        table.row();
        table.add("GameID");
        table.row();
        table.add(gameId);
        table.row();
        table.setFillParent(true);
        table.center();
        table.padBottom(30);

        return list;
    }

    public void goToGameScreen(JSONArray players) {
        stopMusic();
        MainGame main = MainGame.getSingleton();
        GameScreen gameScreen = new GameScreen(main);
        main.setGame(gameScreen);
        gameScreen.setMultiPlayer(true);
        gameScreen.setJoinedMultiplayer(true);
        gameScreen.setPlayers(players);
        gameScreen.startMusic();

        gameScreen.setPreviousMenu(this);
        gameScreen.setGameData(gameData[0], gameData[1]);
        goTo(gameScreen);
    }

    @Override
    public void goBack() {
        goTo(previousMenu);
        server.leaveGame();
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameScreen.class)) {
            gameData = server.joinGame(gameId.getText(), playerName.getText(), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject server_message = (JSONObject) args[0];
                        String type = server_message.getString("type");
                        if (type.equals("join_game")) {
                            //this means there was an error joining the game, rest the menu
                            System.out.println(server_message.getJSONObject("data").getString("message"));
                            goToGameScreen.updateText("Start");
                            goToGameScreen.enable();
                        } else if (type.equals("start_game")) {
                            Gdx.app.postRunnable(() -> {
                                try {
                                    JSONObject data = ((JSONObject) args[0]).getJSONObject("data");

                                    JSONArray players = data.getJSONArray("players");
                                    goToGameScreen(players);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                        server.removeListener("join_game", this);
                        server.removeListener("start_game", this);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
            goToGameScreen.updateText("Waiting for host...");
            goToGameScreen.disable();
        }
    }
}
