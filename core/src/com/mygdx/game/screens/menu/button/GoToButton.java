package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screens.Menu;

public class GoToButton extends MenuButton {
    private Class<? extends Menu> menuClass;

    public GoToButton(Skin skin, Vector2 position, String menuName, Class<? extends Menu> menuClass) {
        super(new TextButton(menuName, skin), position);
        this.menuClass = menuClass;

    }

    public GoToButton(Skin skin, Vector2 position, String menuName) {
        this(skin, position, menuName, null);
    }

    public GoToButton(Texture texture, Vector2 position, int width, int height) {
        super(texture, position, width, height);
    }

    @Override
    public void action() {
        getMenu().goTo(getMenuClass());
    }

    public void updateText(String text) {
        ((TextButton)this.getButton()).setText(text);
    }

    public void enable() {
        this.getButton().setTouchable(Touchable.enabled);
    }

    public void disable() {
        this.getButton().setTouchable(Touchable.disabled);
    }

    protected Class<? extends Menu> getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(Class<? extends Menu> menuClass) {
        this.menuClass = menuClass;
    }
}
