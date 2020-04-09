package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screens.Menu;

public abstract class MenuButton {
    private final Button button;
    private Menu menu;

    protected MenuButton (Menu menu, Button button, Vector2 position) {
        this.menu = menu;
        this.button = button;
        this.button.setPosition(position.x, position.y);
        this.button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action();
            }
        });
    }

    protected MenuButton(Button button, Vector2 position) {
        this(null, button, position);
    }

    protected MenuButton(Texture texture, Vector2 position, int width, int height) {
        this(new ImageButton(new TextureRegionDrawable(new TextureRegion(texture, width, height))), position);
    }

    protected MenuButton(Texture texture, Vector2 position) {
        this(new ImageButton(new TextureRegionDrawable(new TextureRegion(texture))), position);
    }

    public abstract void action();

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Button getButton() {
        return button;
    }

}
