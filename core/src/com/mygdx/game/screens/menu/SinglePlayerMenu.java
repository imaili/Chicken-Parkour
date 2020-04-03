package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;

import java.util.LinkedList;
import java.util.List;

public class SinglePlayerMenu extends MenuScreen {

    private static final List<MenuButton> buttons = createButtons();

    private static List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = new GoBackButton();
        MenuButton goToGameScreen = new GoToButton(skin, new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2), "PLAY", GameScreen.class);
        list.add(goBack);
        list.add(goToGameScreen);
        return list;
    }

    public SinglePlayerMenu(Menu previousMenu) {
        super(previousMenu, buttons);
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
