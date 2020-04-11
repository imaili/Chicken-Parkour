package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.screens.Menu;

public class PauseButton extends GoBackButton {

    public PauseButton() {
        super();
    }

    public PauseButton(Menu menu) {
        this();
        setMenu(menu);
    }

    public PauseButton(Skin skin, Vector2 position, String menuName) {
        super(skin, position, menuName);
    }

    public PauseButton(Texture texture, Vector2 position, int width, int height) {
        super(texture, position, width, height);
    }

    @Override
    public void action() {
        getMenu().pause();
    }

}
