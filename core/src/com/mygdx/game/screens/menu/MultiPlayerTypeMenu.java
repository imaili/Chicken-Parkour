package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MultiPlayerTypeMenu extends MenuScreen {

    public MultiPlayerTypeMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }

    protected List<MenuButton> createButtons() {

        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int nButtons = 5;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        int buttonOffset = (Gdx.graphics.getHeight() - buttonHeight * nButtons) / (nButtons + 1);
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        GoToButton goToHostScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("HOST", new Vector2(buttonX, buttonOffset + 2 * (buttonHeight + buttonOffset)), MultiPlayerHostMenu.class);
        GoToButton goToJoinScreen = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("JOIN", new Vector2(buttonX, buttonOffset +  (buttonHeight + buttonOffset)), MultiPlayerJoinMenu.class);
        Collections.addAll(list, goBack, goToHostScreen, goToJoinScreen);
        return list;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(MultiPlayerHostMenu.class))
            goTo(new MultiPlayerHostMenu(this));
        else if (menu.equals(MultiPlayerJoinMenu.class))
            goTo(new MultiPlayerJoinMenu(this));
    }
}
