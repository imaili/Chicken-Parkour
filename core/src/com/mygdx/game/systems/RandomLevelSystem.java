package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

public class RandomLevelSystem extends IteratingSystem {

    private ObstaclesFactory obstaclesFactory;
    private float accumulatedTime = 0;

    public RandomLevelSystem(World world) {
        super(Family.all().get());
        obstaclesFactory = new ObstaclesFactory(world);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        accumulatedTime+=deltaTime;
        if(accumulatedTime>2){
            accumulatedTime = 0;

            obstaclesFactory.createPlatform(20, 1, 1);
            obstaclesFactory.createPlatform(23,    1, 2);
            obstaclesFactory.createSpikes(25, 1);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        obstaclesFactory.engine = (PooledEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }


    private static class ObstaclesFactory {

        PooledEngine engine;
        World world;

        private ObstaclesFactory(World world){
            this.engine = engine;
            this.world = world;
        }
        private void createPlatform(float x, int length, int height){
            Entity entity = engine.createEntity();
            BodyComponent body = engine.createComponent(BodyComponent.class);
            CollisionComponent collision = engine.createComponent(CollisionComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
            TransformComponent transform = engine.createComponent(TransformComponent.class);

            body.body = createBox(x,1,length,height,true);
            body.body.setUserData(entity);
            body.body.setLinearVelocity(-5f, 0);
            texture.region = createTexture(Color.GREEN, false, 32*length, 32*height);
            obstacle.type = ObstacleComponent.BOX;
            transform.position.set(x, 1, 0);
            entity.add(body);
            entity.add(collision);
            entity.add(texture);
            entity.add(obstacle);
            entity.add(transform);
            engine.addEntity(entity);

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
        private TextureRegion createTexture(Color color, boolean circle, int w, int h){
            Pixmap pmap = new Pixmap(w,h, Pixmap.Format.RGBA8888);
            pmap.setColor(color);
            if(circle){
                pmap.fillCircle(15,15,15);
            }else{
                pmap.fill();
            }
            TextureRegion texr = new TextureRegion(new Texture(pmap));
            pmap.dispose();
            return texr;

        }

        private void createSpikes(float x, int lenght) {
            for (int i = 0; i < lenght; i++) {
                Entity entity = engine.createEntity();
                BodyComponent body = engine.createComponent(BodyComponent.class);
                CollisionComponent collision = engine.createComponent(CollisionComponent.class);
                TextureComponent texture = engine.createComponent(TextureComponent.class);
                ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
                TransformComponent transform = engine.createComponent(TransformComponent.class);
                CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

                body.body = createTriangle(x + i);
                body.body.setUserData(entity);
                body.body.setLinearVelocity(-5f, 0);
                //texture.region = createTexture(Color.GREEN, false, 32, 32);
                obstacle.type = ObstacleComponent.SPIKES;

                entity.add(body);
                entity.add(collision);
                //entity.add(texture);
                entity.add(obstacle);
                entity.add(transform);
                entity.add(cleanUp);
                engine.addEntity(entity);

            }

        }
        private Body createTriangle(float x){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.x = x;
            bodyDef.position.y = 1;
            bodyDef.fixedRotation = true;
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            Body body = world.createBody(bodyDef);
            //create the body to attach said definition
            Vector2[] vertices = {new Vector2(-0.4f, -0.4f), new Vector2(0.4f, -0.4f), new Vector2(0,0.4f)};
            PolygonShape poly = new PolygonShape();
            poly.set(vertices);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = poly;
            fixtureDef.friction = 0;

            body.createFixture(fixtureDef);
            poly.dispose();
            return body;
        }


    }




}
