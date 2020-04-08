package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.ExitButton;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExitMenu extends MenuScreen {

    protected static final Texture DEFAULT_EXIT_TEXTURE = new Texture("exitgame.png");
    protected static final int X = Gdx.graphics.getWidth() / 4;
    protected static final int Y = Gdx.graphics.getHeight() / 4;
    protected static final int WIDTH = Gdx.graphics.getWidth() / 2;
    protected static final int HEIGHT = Gdx.graphics.getHeight()/ 2;

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton exit = new ExitButton(skin, new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2), "Yes");
        MenuButton cancel = new GoBackButton(skin, new Vector2(buttonX, Gdx.graphics.getHeight()/2 - 2*buttonHeight), "Cancel");
        Collections.addAll(list, exit, cancel);
        return list;
    }

    public ExitMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_EXIT_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {

    }

    protected void draw() {
        getStage().getBatch().begin();
        getStage().getBatch().draw(backGroundTexture, X, Y, WIDTH, HEIGHT);
        getStage().getBatch().end();
        getStage().draw();
    }

}
