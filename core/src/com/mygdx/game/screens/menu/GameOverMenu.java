package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameOverMenu extends MenuScreen {

    protected static final Texture GAME_OVER_BACK_GROUND_TEXTURE = new Texture("background_gameover.png");
    protected static final int X = Gdx.graphics.getWidth() / 4;
    protected static final int Y = Gdx.graphics.getHeight() / 4;
    protected static final int WIDTH = Gdx.graphics.getWidth() / 2;
    protected static final int HEIGHT = Gdx.graphics.getHeight()/ 2;



    @Override
    protected List<MenuButton> createButtons() {

        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton play = DEFAULT_TEXT_BUTTON_FACTORY.createGoBackButton("Play again", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2));
        MenuButton goToMainMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Exit", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - 2*buttonHeight), MainMenu.class);
        Collections.addAll(list, play, goToMainMenu);
        return list;
    }

    public GameOverMenu(GameScreen gameScreen) {
        super(gameScreen);
        //backGroundTexture = GAME_OVER_BACK_GROUND_TEXTURE;
        backGroundTexture = getCurrentBackGroundTexture();
    }

    private Texture getCurrentBackGroundTexture() {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for(int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        return new Texture(pixmap);
    }


    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(MainMenu.class))
           goToMainMenu();
    }

    public void goToMainMenu() {
        goTo(new MainMenu());
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().getBatch().begin();
        getStage().getBatch().draw(backGroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getStage().getBatch().draw(GAME_OVER_BACK_GROUND_TEXTURE, X, Y, WIDTH, HEIGHT);
        getStage().getBatch().end();
        getStage().draw();
    }

    @Override
    public void goBack() {
        stopMusic();
        previousMenu.startMusic();
        super.goBack();
    }




}
