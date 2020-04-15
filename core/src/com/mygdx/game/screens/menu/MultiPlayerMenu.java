package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.utils.Constants;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MultiPlayerMenu extends MenuScreen {
    public MultiPlayerMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }


    protected List<MenuButton> createButtons() {
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
        float buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        GoToButton goToGameScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("PLAY", new Vector2(buttonX, Gdx.graphics.getHeight()* (float)0.1 - buttonHeight/2), GameScreen.class);
        Collections.addAll(list, goBack, goToGameScreen);
        return list;
    }


    @Override
    public List<Actor> getActors() {
        List<Actor> list = new LinkedList<>();
        Skin skin = MainGame.getSingleton().getAssetsManager().get(Constants.TABLE_SKIN);
        TextField nameText = new TextField("", skin);
        Table table = new Table(skin);
        list.add(table);

        table.add("Player name").colspan(2);
        table.row();
        table.add(nameText).colspan(2);
        table.row();
        table.row();
        table.add("Other players").colspan(2).padTop(20);
        table.row();
        table.add("Player name").padRight(50);
        table.add("Player name");
        table.row();
        table.add("Player name").padRight(50);
        table.add("Player name");
        table.setFillParent(true);
        table.center();
        table.padBottom(30);
        return list;
    }

    public void goToGameScreen() {
        goTo(new GameScreen(MainGame.getSingleton()));
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameScreen.class))
            goToGameScreen();
    }
}
