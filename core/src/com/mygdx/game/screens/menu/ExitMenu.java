package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.utils.Constants;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExitMenu extends MenuScreen {

    protected static final Texture DEFAULT_EXIT_TEXTURE = new Texture(Constants.EXIT_MENU_BACKGROUND_PATH);
    protected static final int X = Gdx.graphics.getWidth() / 4;
    protected static final int Y = Gdx.graphics.getHeight() / 4;
    protected static final int WIDTH = Gdx.graphics.getWidth() / 2;
    protected static final int HEIGHT = Gdx.graphics.getHeight()/ 2;

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton exit = DEFAULT_TEXT_BUTTON_FACTORY.createExitButton("Yes", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2));
        MenuButton cancel = DEFAULT_TEXT_BUTTON_FACTORY.createGoBackButton("Cancel", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - 2*buttonHeight));
        Collections.addAll(list, exit, cancel);
        return list;
    }

    public ExitMenu(MainMenu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_EXIT_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {

    }

    public void draw() {
        ((MainMenu) previousMenu).draw();
        getStage().getBatch().begin();
        getStage().getBatch().draw(backGroundTexture, X, Y, WIDTH, HEIGHT);
        getStage().getBatch().end();
        getStage().draw();
    }

}
