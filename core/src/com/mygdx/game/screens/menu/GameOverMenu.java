package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameOverMenu extends PauseMenu {

    protected static final Texture GAME_OVER_BACK_GROUND_TEXTURE = new Texture("background_gameover.png");

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
        super(gameScreen, GAME_OVER_BACK_GROUND_TEXTURE);
    }

    @Override
    public void goBack() {
        goTo(GameScreen.class);
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        //previousMenu.dispose();
        super.goTo(menu);
        if (menu.equals(GameScreen.class))
            goToGameScreen();
    }

    @Override
    public void goToMainMenu() {
        goTo(new MainMenu());
    }

    public void goToGameScreen() {
        stopMusic();
        GameScreen gameScreen = new GameScreen(MainGame.getSingleton());
        gameScreen.startMusic();
        goTo(gameScreen);
    }

}
