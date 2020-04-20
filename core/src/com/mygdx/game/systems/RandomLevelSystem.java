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
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PowerUp;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Mappers;

import java.util.Map;
import java.util.function.Consumer;

public class RandomLevelSystem extends IteratingSystem {

    private ObstaclesFactory obstaclesFactory;
    private float accumulatedTime = 0;
    private Server server;
    private Entity player;
    public RandomLevelSystem(World world, Entity player) {
        super(Family.all().get());
        obstaclesFactory = new ObstaclesFactory(world);
        server = Server.getInstance();
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        accumulatedTime+=deltaTime;
        if(accumulatedTime>2){
            accumulatedTime = 0;

            obstaclesFactory.createSpeedPowerUp(Mappers.TRANSFORM.get(player).position.x+20, 3);
           // server.addObstacle(deltaTime, "platform");
            //obstaclesFactory.createSpikes(playerPosition+25, 1);
            //server.addObstacle(deltaTime, "spikes");

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
            CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);
            body.body = createBox(x,1,length,height,true);
            body.body.setUserData(entity);
            texture.region = createTexture(Color.GREEN, false, 32*length, 32*height);
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

                body.body = createTriangle(x + i, 1);
                body.body.setUserData(entity);

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

                obstacle.type = ObstacleComponent.SPIKES;

                entity.add(body);
                entity.add(collision);
               // entity.add(texture);
                entity.add(obstacle);
                entity.add(transform);
                entity.add(cleanUp);
                engine.addEntity(entity);

            }

        }
        private Body createTriangle(float x, float y){
            BodyDef bodyDef = new BodyDef();
            bodyDef.position.x = x;
            bodyDef.position.y = y;
            bodyDef.fixedRotation = true;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.gravityScale = 0.0f;

            Body body = world.createBody(bodyDef);
            //create the body to attach said definition
            Vector2[] vertices = {new Vector2(-0.4f, -0.4f), new Vector2(0.4f, -0.4f), new Vector2(0,0.4f)};
            PolygonShape poly = new PolygonShape();
            poly.set(vertices);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = poly;
            fixtureDef.friction = 0;
            fixtureDef.isSensor = true;

            body.createFixture(fixtureDef);
            poly.dispose();
            return body;
        }

        public void createSpeedPowerUp(float x, float _y){
            Entity entity = engine.createEntity();
            final PowerUpComponent powerUp = engine.createComponent(PowerUpComponent.class);
            TextureComponent texture = engine.createComponent(TextureComponent.class);
            CollisionComponent collision = engine.createComponent(CollisionComponent.class);
            BodyComponent body = engine.createComponent(BodyComponent.class);
            TransformComponent transform = engine.createComponent(TransformComponent.class);

            body.body = createTriangle(x, _y);
            body.body.setUserData(entity);
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
            Consumer<Entity> action = new Consumer<Entity>() {
                @Override
                public void accept(Entity entity) {
                    BodyComponent body = Mappers.BODY.get(entity);
                    body.body.setLinearVelocity(8, body.body.getLinearVelocity().y);
                    AnimationComponent animation = Mappers.ANIMATION.get(entity);
                    animation.animationsMap.get(StateComponent.STATE_WALKING).setFrameDuration(0.07f);

                }
            };
            Consumer<Entity> reset = new Consumer<Entity>() {
                @Override
                public void accept(Entity entity) {
                    BodyComponent body = Mappers.BODY.get(entity);
                    body.body.setLinearVelocity(5, body.body.getLinearVelocity().y);
                    AnimationComponent animation = Mappers.ANIMATION.get(entity);
                    animation.animationsMap.get(StateComponent.STATE_WALKING).setFrameDuration(0.1f);

                }
            };
            powerUp.powerUp = new PowerUp(action, reset);
            powerUp.duration = 5;

            entity.add(texture);
            entity.add(transform);
            entity.add(body);
            entity.add(powerUp);
            entity.add(collision);
            engine.addEntity(entity);

        }


    }




}
