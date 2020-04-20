package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.socket.emitter.Emitter;

public class SinglePlayerMenu extends MenuScreen {

    Table table;
    Server server;
    Label gameId;TextField nameText;
    ArrayList<JSONObject> players = new ArrayList<>();

    protected List<MenuButton> createButtons() {
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        GoToButton goToGameScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("START", new Vector2(buttonX, Gdx.graphics.getHeight()* (float)0.1 - buttonHeight/2), GameScreen.class);
        Collections.addAll(list, goBack, goToGameScreen);
        return list;
    }
    private Label createLabel(Skin skin) {
        Label title = new Label("GameId", skin);
        return title;
    }

    private Table createTable(Skin skin) {
        return new Table(skin);
    }

    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        Skin skin = MainGame.getSingleton().getAssetManager().get(Constants.TABLE_SKIN);
        nameText = new TextField("", skin);
        table = createTable(skin);
        gameId = this.createLabel(skin);
        list.add(table);

        table.add(gameId).colspan(2);
        table.row();
        table.add("Player name").colspan(2).padTop(50);
        table.row();
        table.add(nameText).colspan(2);
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

    private void updateGameId(String gameId) {
        this.gameId.setText("GameID: " + gameId);
    }


    public SinglePlayerMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = BACK_GROUND_TEXTURE_EMPTY;
        server = Server.getInstance();
        String[] data = server.newGame(new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            try {
                    JSONObject message = (JSONObject) args[0];
                String type = message.getString("type");

                System.out.println(message);

                    JSONObject player = message.getJSONObject("data").getJSONObject("player");
                    players.add(player);
                    table.add(String.valueOf(players.size())).padRight(50);
                    table.add(player.getString("name"));
                } catch (Exception e) {
                System.out.println(e);
            }}
    });

    updateGameId(data[1]);
    }



    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameScreen.class))
            goToGameScreen();
                    }


    public void goToGameScreen() {
                        stopMusic();
        GameScreen gameScreen = new GameScreen(MainGame.getSingleton());
        gameScreen.startMusic();
        gameScreen.setMultiPlayer(false);
        goTo(gameScreen);
    }
}
