package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.GoToButton;
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
        MenuButton Play = new GoBackButton(skin, new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2), "Play again");
        MenuButton goToMainMenu = new GoToButton(skin, new Vector2(buttonX, Gdx.graphics.getHeight()/2 - 2*buttonHeight), "Exit", MainMenu.class);
        Collections.addAll(list,Play, goToMainMenu);
        return list;
    }

    public GameOverMenu(GameScreen gameScreen) {
        super(gameScreen);
        backGroundTexture = GAME_OVER_BACK_GROUND_TEXTURE;
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
        getStage().getBatch().begin();
        getStage().getBatch().draw(backGroundTexture, X, Y, WIDTH, HEIGHT);
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
