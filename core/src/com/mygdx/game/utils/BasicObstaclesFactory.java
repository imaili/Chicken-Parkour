package com.mygdx.game.utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class BasicObstaclesFactory extends ObstaclesFactory {


    public BasicObstaclesFactory(PooledEngine engine, World world) {
        super(engine, world);
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
        body.body = createBox(x,1,1,1,true);
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
        engine.addEntity(entity);

    }

    @Override
    public void createSpike(float x, float y) {

    }

    private Body createBox(float x, float y, float w, float h, boolean dynamic){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        if(dynamic){
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        }else{
            boxBodyDef.type = BodyDef.BodyType.StaticBody;
        }

        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = true;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w/2, h/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
        poly.dispose();

        return boxBody;

    }
}
