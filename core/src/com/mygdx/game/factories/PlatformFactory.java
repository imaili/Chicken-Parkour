package com.mygdx.game.factories;

import com.badlogic.ashley.core.Engine;
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

public class PlatformFactory {
    public static Entity create(float x, float y, PooledEngine engine){
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);
        body.body = MainGame.getSingleton().getGame().createBox(x,1,1,1,true);
        body.body.setUserData(entity);
        texture.region = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal(Constants.PLATFORM_PATH))));
        transform.scale.set(0.45f, 0.45f);
        obstacle.type = ObstacleComponent.BOX;
        transform.position.set(x, 1, 0);
        entity.add(body);
        entity.add(collision);
        entity.add(texture);
        entity.add(obstacle);
        entity.add(transform);
        entity.add(cleanUp);

        return entity;
    }
}
