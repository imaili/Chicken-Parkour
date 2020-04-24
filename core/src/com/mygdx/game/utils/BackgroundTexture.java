package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;

public class BackgroundTexture {
    private static final float DEFAULT_SPEED = -2;
    private static final SpriteBatch DEFAULT_BATCH = new SpriteBatch();

    private final int width = Gdx.graphics.getWidth();
    private final int height = Gdx.graphics.getHeight();
    private final Texture texture;
    private float speed;
    private float x;

    public BackgroundTexture(Texture texture) {
        this.texture = texture;
        this.speed = DEFAULT_SPEED;
        this.x = 0;
    }

    public BackgroundTexture(String texturePath) {
        this(MainGame.getSingleton().getAssetManager().get(texturePath, Texture.class));
        //this(new Texture(texturePath));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, 0, width, height);
        batch.draw(texture, x + width, 0, width, height);
    }

    public  void draw() {
        DEFAULT_BATCH.begin();
        draw(DEFAULT_BATCH);
        DEFAULT_BATCH.end();
    }

    public void update() {
        x = (x + speed) % width;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
