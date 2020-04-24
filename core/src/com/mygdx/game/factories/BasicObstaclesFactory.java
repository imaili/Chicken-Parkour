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

public class BasicObstaclesFactory extends ObstaclesFactory {



    public BasicObstaclesFactory(PooledEngine engine, BodyFactory bodyFactory) {
        super(engine, bodyFactory);
    }

    @Override
    public void createPlatform(float x, float y) {
        PooledEngine engine = getEngine();
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);
        body.body = getBodyFactory().createRectangle(x,y,1,1,false);
        body.body.setUserData(entity);
        texture.region = new TextureRegion((Texture) MainGame.getSingleton().getAssetManager().get(Constants.PLATFORM_PATH));
        transform.scale.set(0.45f, 0.45f);
        obstacle.type = ObstacleComponent.BOX;
        transform.position.set(x, 1, 0);
        entity.add(body);
        entity.add(collision);
        entity.add(texture);
        entity.add(obstacle);
        entity.add(transform);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    @Override
    public void createSpike(float x, float y) {
        PooledEngine engine = getEngine();
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        body.body = getBodyFactory().createTriangle(x, y, false);
        body.body.setUserData(entity);

        texture.region = new TextureRegion(new Texture(new Pixmap(Gdx.files.internal(Constants.SPIKE_PATH))));
        transform.scale.set(0.15f, 0.15f);

        obstacle.type = ObstacleComponent.SPIKES;

        entity.add(body);
        entity.add(collision);
        entity.add(texture);
        entity.add(obstacle);
        entity.add(transform);
        entity.add(cleanUp);

        engine.addEntity(entity);

    }


}
