package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ExitButton extends MenuButton {
    private static final String MENU_NAME = "Exit";

    public ExitButton(Skin skin, Vector2 position, String menuName) {
        super(new TextButton(menuName, skin), position);
    }

    public ExitButton(Skin skin, Vector2 position) {
        this(skin, position, MENU_NAME);
    }

    public ExitButton(Texture texture, Vector2 position, int width, int height) {
        super(texture, position, width, height);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
