package com.mygdx.game.screens;

import com.mygdx.game.MainGame;

public abstract class BaseScreen implements Menu {

    private MainGame game;

    public BaseScreen(MainGame game){
        this.game = game;
    }

    public BaseScreen() {
        this(null);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public MainGame getGame() {
        return game;
    }
    
}
