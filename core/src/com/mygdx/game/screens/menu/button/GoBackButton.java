package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screens.Menu;

public class GoBackButton extends MenuButton {

    private static final String GO_BACK_FILE_NAME = "exit1.png";

    private static Button createButton() {
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(GO_BACK_FILE_NAME))));
    }

    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 50;

    private static Vector2 defaultPosition() {
        return new Vector2(Gdx.graphics.getWidth() - BUTTON_WIDTH, Gdx.graphics.getHeight() - BUTTON_HEIGHT);
    }

    public GoBackButton(Vector2 position) {
        super(createButton(), position);
    }

    public GoBackButton(Skin skin, Vector2 position, String menuName) {
        super(new TextButton(menuName, skin), position);
    }

    public GoBackButton() {
        this(defaultPosition());
    }

    @Override
    public void action() {
        getMenu().goBack();
    }
}
