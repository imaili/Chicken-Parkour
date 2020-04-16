package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.Menu;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class HighscoresMenu extends MenuScreen {
    Table table;
    private Server server;

    protected List<MenuButton> createButtons() {
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        list.add(goBack);
        return list;
    }

    protected Table createTable(){
        table.add("#");
        table.add("Name").center().padLeft(50).padRight(50);
        table.add("Score");

        table.row();

        table.setFillParent(true);
        table.center();
        return table;
    }

    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        Skin skin = MainGame.getSingleton().getAssetsManager().get(Constants.TABLE_SKIN);
        table = new Table(skin);
        list.add(table);
        Label title = this.createLabel(skin);
        list.add(title);
        return list;
    }

    private Label createLabel(Skin skin) {
        Label title = new Label("Highscores", skin);
        int screenSize = Gdx.graphics.getWidth() /2;
        float screenHight = Gdx.graphics.getHeight() * (float)0.85;
        float labelSize = title.getWidth() / 2;
        title.setPosition(screenSize - labelSize, screenHight);
        return title;
    }

    public HighscoresMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;

        this.createTable();
        server = Server.getInstance();


        server.getHighscores(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject message = (JSONObject) args[0];
                JSONArray highscores = (JSONArray) message.get("data");
                int x = 1;
                for (Object highscore :
                        highscores) {
                    String y = String.valueOf(x);
                    table.add(y);
                    x++;
                    table.add(((JSONObject)highscore).getString("name")).center().padLeft(50).padRight(50);
                    table.add(String.valueOf(((JSONObject)highscore).get("score")));





                    table.row();
                }

                server.removeListener("get_highscores",this);
            }

        });
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(Settings.class))
            goToSettings();
    }

    public void goToSettings() {
        goTo(new Settings(previousMenu));
    }
}
