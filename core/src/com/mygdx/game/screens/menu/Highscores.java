package com.mygdx.game.screens.menu;

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

public class Highscores extends MenuScreen {
    Table table;
    private Server server;

    protected List<MenuButton> createButtons() {
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        list.add(goBack);
        return list;
    }

    protected Table createTable(){
        table.add("Number").padLeft(-200).width(0);
        table.add("Name").padLeft(10).width(100);
        table.add("Score").padRight(12).width(55);

        table.row();

        table.setFillParent(true);
        table.top().center();
        return table;
    }

    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        Skin skin = MainGame.getSingleton().getAssetsManager().get(Constants.TABLE_SKIN);
        table = new Table(skin);
        list.add(table);
        return list;
    }

    public Highscores(Menu previousMenu) {
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
                    table.add(y).padLeft(-190);
                    x++;
                    table.add(((JSONObject)highscore).getString("name")).padLeft(10).width(100);
                    table.add((((JSONObject)highscore).get("score")).toString());





                    table.row();
                }

                server.removeListener("get_highscores",this);
            }

        });

//        highscores = new JSONArray();
//        JSONObject fredScore = new JSONObject();
//        fredScore.put("name", "fred");
//        fredScore.put("score", 34342);
//
//        highscores.put(fredScore);
//        highscores.put(fredScore);
//        highscores.put(fredScore);
//        highscores.put(fredScore);
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
