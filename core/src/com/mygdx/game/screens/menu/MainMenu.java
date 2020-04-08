package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.ExitButton;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.Menu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends MenuScreen {

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
        int nButtons = 4;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        int buttonOffset = (Gdx.graphics.getHeight() - buttonHeight * nButtons) / (nButtons + 1);
        List<MenuButton> list = new LinkedList<>();
        // TODO Remove SinglePlayerMenu
        MenuButton goToSinglePlayerMenu = new GoToButton(skin, new Vector2(buttonX, buttonOffset + 3*(buttonHeight+buttonOffset)), "Single Player", SinglePlayerMenu.class);
        MenuButton goToMultiPlayerMenu = new GoToButton(skin, new Vector2(buttonX, buttonOffset + 2*(buttonHeight+buttonOffset)), "MultiPlayer", SinglePlayerMenu.class);
        MenuButton goToSettings = new GoToButton(skin, new Vector2(buttonX, buttonOffset + (buttonHeight+buttonOffset)), "Settings", Settings.class);
        MenuButton exitGame = new ExitButton(skin, new Vector2(buttonX, buttonOffset));
        Collections.addAll(list, goToSinglePlayerMenu, goToMultiPlayerMenu, goToSettings, exitGame);
        return list;
    }

    public MainMenu() {
        super();
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(Settings.class))
            goToSettings();
        else if (menu.equals(SinglePlayerMenu.class))
            goToSinglePlayerMenu();
        /* TODO
        else if (menu.equals(MultiPlayerMenu.class))
            goToMultiPlayerMenu();
         */
    }

    public void goToSettings() {
        goTo(new Settings(this));
    }

    public void goToSinglePlayerMenu() {
        goTo(new SinglePlayerMenu(this));
    }

    public void goToMultiPlayerMenu() {
        // TODO
    }
}
