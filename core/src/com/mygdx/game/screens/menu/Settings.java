package com.mygdx.game.screens.menu;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.Menu;

import java.util.LinkedList;
import java.util.List;

public class Settings extends MenuScreen {

    protected List<MenuButton> createButtons() {
        List<MenuButton> list = new LinkedList<>();
        MenuButton goToMainMenu = new GoToButton(skin, new Vector2(200, 200), "Menu", MainMenu.class);
        MenuButton goBack = new GoBackButton();
        list.add(goToMainMenu);
        list.add(goBack);
        return list;
    }

    public Settings(Menu previousMenu) {
        super(previousMenu);
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(MainMenu.class))
            goBack();
        if (menu.equals(SinglePlayerMenu.class))
            goTo(new SinglePlayerMenu(this));
    }
}
