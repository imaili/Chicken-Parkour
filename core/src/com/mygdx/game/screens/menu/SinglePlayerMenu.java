package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SinglePlayerMenu extends MenuScreen {

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        GoToButton goToGameScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("PLAY", new Vector2(buttonX, Gdx.graphics.getHeight()* (float)0.1 - buttonHeight/2), GameScreen.class);
        Collections.addAll(list, goBack, goToGameScreen);
        return list;
    }

    public SinglePlayerMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameScreen.class))
            goToGameScreen();
    }

    public void goToGameScreen() {
        goTo(new GameScreen(MainGame.getSingleton()));
    }
}
