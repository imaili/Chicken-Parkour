package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.LinkedList;
import java.util.List;

public class Tutorial extends MenuScreen {


    @Override
    protected List<MenuButton> createButtons() {
       // int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
       // int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();

        list.add(goBack);
        return list;
    }

    public Tutorial(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = BACK_GROUND_TUTORIAL;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(Settings.class))
            goToTutorial();
    }

    public void goToTutorial() {
        goTo(new Tutorial(previousMenu));
    }



}
