package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screens.Menu;

public class GoToButton extends MenuButton {
    private final Class<? extends Menu> menuClass;

    public GoToButton(Menu menu, Skin skin, Vector2 position, String menuName, Class<? extends Menu> menuClass) {
        super(menu, new TextButton(menuName, skin), position);
        this.menuClass = menuClass;
    }

    public GoToButton(Skin skin, Vector2 position, String menuName, Class<? extends Menu> menuClass) {
        this(null, skin, position, menuName, menuClass);
    }

    @Override
    public void action() {
        getMenu().goTo(menuClass);
    }
}
