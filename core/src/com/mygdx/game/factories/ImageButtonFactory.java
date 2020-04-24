package com.mygdx.game.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.menu.button.ExitButton;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.menu.button.MusicButton;
import com.mygdx.game.screens.menu.button.PauseButton;

import java.util.HashMap;
import java.util.Map;

public class ImageButtonFactory implements ButtonFactory {
    public static final ImageButtonFactory SINGLETON = new ImageButtonFactory();

    private final Map<String, Texture> textures = new HashMap<>();

    private ImageButtonFactory() {
        super();
    }

    public static ImageButtonFactory getInstance() {
        return SINGLETON;
    }

    private Texture getTexture(String filename) {
        Texture texture = textures.get(filename);
        if (texture == null) {
            texture = new Texture(filename);
            textures.put(filename, texture);
        }
        return texture;
    }

    public void dispose() {
        for (Texture texture : textures.values())
            texture.dispose();
        textures.clear();
    }

    @Override
    public MenuButton createButton(Class<? extends MenuButton> buttonClass, String name, Vector2 position) {
        if (buttonClass.equals(ExitButton.class))
            return createExitButton(name, position);
        else if (buttonClass.equals(GoBackButton.class))
            return createGoBackButton(name, position);
        else if (buttonClass.equals(GoToButton.class))
            return createGoToButton(name, position);
        else if (buttonClass.equals(MusicButton.class))
            return createMusicButton(name, position);
        else if (buttonClass.equals(PauseButton.class))
            return createPauseButton(name, position);
        return null;
    }

    @Override
    public ExitButton createExitButton(String name, Vector2 position) {
        Texture texture = getTexture(name);
        return createExitButton(name, position, texture.getWidth(), texture.getHeight());
    }

    @Override
    public GoBackButton createGoBackButton(String name, Vector2 position) {
        Texture texture = getTexture(name);
        return createGoBackButton(name, position, texture.getWidth(), texture.getHeight());
    }

    public GoBackButton createGoBackButton() {
        return createGoBackButton(GoBackButton.GO_BACK_FILE_NAME, GoBackButton.defaultPosition());
    }

    @Override
    public GoToButton createGoToButton(String name, Vector2 position) {
        Texture texture = getTexture(name);
        return createGoToButton(name, position, texture.getWidth(), texture.getHeight());
    }

    @Override
    public GoToButton createGoToButton(String name, Vector2 position, Class<? extends Menu> menuClass) {
        GoToButton button = createGoToButton(name, position);
        button.setMenuClass(menuClass);
        return button;
    }

    @Override
    public MusicButton createMusicButton(String name, Vector2 position) {
        Texture texture = getTexture(name);
        return createMusicButton(name, position, texture.getWidth(), texture.getHeight());
    }

    @Override
    public PauseButton createPauseButton(String name, Vector2 position) {
        Texture texture = getTexture(name);
        return createPauseButton(name, position, texture.getWidth(), texture.getHeight());
    }

    public MenuButton createButton(Class<? extends MenuButton> buttonClass, String name, Vector2 position, int width, int height) {
        if (buttonClass.equals(ExitButton.class))
            return createExitButton(name, position, width, height);
        else if (buttonClass.equals(GoBackButton.class))
            return createGoBackButton(name, position, width, height);
        else if (buttonClass.equals(GoToButton.class))
            return createGoToButton(name, position, width, height);
        else if (buttonClass.equals(MusicButton.class))
            return createMusicButton(name, position, width, height);
        else if (buttonClass.equals(PauseButton.class))
            return createPauseButton(name, position, width, height);
        return null;
    }

    public ExitButton createExitButton(String name, Vector2 position, int width, int height) {
        return new ExitButton(getTexture(name), position, width, height);
    }

    public GoBackButton createGoBackButton(String name, Vector2 position, int width, int height) {
        return new GoBackButton(getTexture(name), position, width, height);
    }

    public GoToButton createGoToButton(String name, Vector2 position, int width, int height) {
        return new GoToButton(getTexture(name), position, width, height);
    }

    public MusicButton createMusicButton(String name, Vector2 position, int width, int height) {
        return new MusicButton(getTexture(name), position, width, height);
    }

    public PauseButton createPauseButton(String name, Vector2 position, int width, int height) {
        return new PauseButton(getTexture(name), position, width, height);
    }

}
