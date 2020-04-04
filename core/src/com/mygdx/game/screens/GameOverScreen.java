package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.mygdx.game.MainGame;


public class GameOverScreen extends BaseScreen {

    Label coordinates;
    Label.LabelStyle textStyle;
    BitmapFont font;
    SpriteBatch batch = new SpriteBatch();

    public GameOverScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        font = new BitmapFont();
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        coordinates = new Label("YOU LOST",textStyle);
        coordinates.setFontScale(1f,1f);
        coordinates.setBounds(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 10,10);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        coordinates.draw(batch, 1);
        batch.end();

    }
}
