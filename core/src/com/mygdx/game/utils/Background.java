package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;
import java.util.List;

public class Background {
    private final List<BackgroundTexture> textures;
    private final SpriteBatch batch;

    public Background(BackgroundTexture... backgroundTextures) {
        textures = new LinkedList<>();
        for (BackgroundTexture texture : backgroundTextures) {
            textures.add(texture);
        }
        batch = new SpriteBatch();
    }

    public void render() {
        // render
        batch.begin();
        for (BackgroundTexture texture : textures) {
            texture.draw(batch);
        }
        batch.end();

        // update
        for (BackgroundTexture texture : textures) {
            texture.update();
        }
    }

    public static Background createGameBackground() {
        BackgroundTexture firstLayer = new BackgroundTexture(Constants.GAME_BACKGROUND_1_PATH);
        BackgroundTexture secondLayer = new BackgroundTexture(Constants.GAME_BACKGROUND_2_PATH);
        BackgroundTexture thirdLayer = new BackgroundTexture(Constants.GAME_BACKGROUND_3_PATH);
        BackgroundTexture forthLayer = new BackgroundTexture(Constants.GAME_BACKGROUND_4_PATH);
        BackgroundTexture fifthLayer = new BackgroundTexture(Constants.GAME_BACKGROUND_5_PATH);
        firstLayer.setSpeed(0);
        secondLayer.setSpeed(-0.5f);
        thirdLayer.setSpeed(-1);
        forthLayer.setSpeed(-1.5f);
        fifthLayer.setSpeed(-2);
        return new Background(firstLayer, secondLayer, thirdLayer, forthLayer, fifthLayer);
    }

}
