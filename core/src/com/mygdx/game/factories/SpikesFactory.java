package com.mygdx.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Constants;

public class SpikesFactory {
    public static Entity create(float x, PooledEngine engine) {
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        body.body = MainGame.getSingleton().getGame().createTriangle(x, 1);
        body.body.setUserData(entity);
                /*
                Pixmap pmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
                pmap.setColor(Color.GRAY);
                pmap.fillTriangle(32,0,32,32 ,0,16 );
                final int width = pmap.getWidth();
                final int height = pmap.getHeight();
                Pixmap rotatedPmap = new Pixmap(height, width, pmap.getFormat());

                for (int x2 = 0; x2 < height; x2++) {
                    for (int y = 0; y < width; y++) {
                        rotatedPmap.drawPixel(x2, y, pmap.getPixel(y, x2));
                    }
                }
                texture.region = new TextureRegion(new Texture(rotatedPmap));
                rotatedPmap.dispose();
                pmap.dispose();
                */
        texture.region = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal(Constants.SPIKE_PATH))));
        transform.scale.set(0.15f, 0.15f);

        obstacle.type = ObstacleComponent.SPIKES;

        entity.add(body);
        entity.add(collision);
        entity.add(texture);
        entity.add(obstacle);
        entity.add(transform);
        entity.add(cleanUp);
        return entity;
    }
}
