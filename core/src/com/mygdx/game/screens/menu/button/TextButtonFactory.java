package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextButtonFactory implements ButtonFactory {
    private final static TextButtonFactory DEFAULT_TEXT_BUTTON_FACTORY = new TextButtonFactory();
    private final Skin skin;

    public TextButtonFactory(Skin skin) {
        this.skin = skin;
    }

    private TextButtonFactory() {
        this(createBasicSkin());
    }

    public static TextButtonFactory getDefaultInstance() {
        return DEFAULT_TEXT_BUTTON_FACTORY;
    }

    @Override
    public MenuButton createButton(Class<? extends MenuButton> buttonClass, String name, Vector2 position) {
        if (buttonClass.equals(ExitButton.class))
            return createExitButton(name, position);
        else if (buttonClass.equals(GoBackButton.class))
            return createGoBackButton(name, position);
        else if (buttonClass.equals(MusicButton.class))
            return createMusicButton(name, position);
        else if (buttonClass.equals(PauseButton.class))
            return createPauseButton(name, position);
        return null;
    }

    @Override
    public ExitButton createExitButton(String name, Vector2 position) {
        return new ExitButton(skin, position, name);
    }

    @Override
    public GoBackButton createGoBackButton(String name, Vector2 position) {
        return new GoBackButton(skin, position, name);
    }

    @Override
    public MusicButton createMusicButton(String name, Vector2 position) {
        return new MusicButton(skin, position);
    }

    @Override
    public PauseButton createPauseButton(String name, Vector2 position) {
        return new PauseButton(skin, position, name);
    }

    private static Skin createBasicSkin() {
        // Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        // Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

    public static Skin createSkin(int width, int height, Texture up, Texture down) {
        // Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        // Change Textures to drawables
        Drawable buttonUp = new TextureRegionDrawable(new TextureRegion(up, width, height));
        Drawable buttonDown = new TextureRegionDrawable(new TextureRegion(down, width, height));

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonUp;
        textButtonStyle.down = buttonDown;
        textButtonStyle.checked = buttonUp;
        textButtonStyle.over = buttonUp;
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

}
