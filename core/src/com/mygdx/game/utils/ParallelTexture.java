package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ParallelTexture {
    private final TextureRegion[] backgrounds;
    private final float[] parallax; //For example {0.2f, 0.1f}
    private final OrthographicCamera camera;

    public ParallelTexture(TextureRegion[] backgrounds, float[] parallax) {
        this.backgrounds = backgrounds;
        this.parallax = parallax;
        camera=new OrthographicCamera();
    }

    public ParallelTexture() {
        backgrounds = new TextureRegion[5];
        parallax = new float[5];
        for (int i = 0; i < 5; i++) {
            backgrounds[i] = new TextureRegion(new Texture("background/plx-" + (i+1) + ".png"));
            parallax[i] = (i+1)*0.1f;
        }
        camera=new OrthographicCamera(400,240);
        camera.position.x=200;
        camera.position.y=120;
        camera.update();
    }

    public void drawLayers(Stage stage) {
        drawLayers(stage.getBatch(), camera);
        for (int i = 0; i < parallax.length; i++) {
            parallax[i] += 0.0f;
        }
    }

    public void drawLayers(Batch batch, OrthographicCamera camera) {
        batch.setColor(Color.WHITE);
        for(int b = backgrounds.length - 1; b >= 0; b--) {
            TextureRegion background = backgrounds[b];

            if(background != null) {
                float x = (camera.position.x - camera.viewportWidth / 2f * camera.zoom);
                float y = camera.position.y - camera.viewportHeight / 2f * camera.zoom + camera.viewportHeight / 15f * camera.zoom;

                float rWidth = camera.viewportWidth * 1.5f * camera.zoom;
                float rHeight = (rWidth / background.getRegionWidth()) * background.getRegionHeight();

                drawParallaxLayer(batch, background, parallax[b], x, y, rWidth, rHeight);
            }
        }
    }

    public static void drawParallaxLayer(Batch batch, TextureRegion region, float parallax, float x, float y, float width, float height) {
        for(int j = 0; j < 3; j++) {
            batch.draw(region, x + (j * width) - ((x * parallax) % width) - (width / 2f), y, width, height);
        }
    }
}
