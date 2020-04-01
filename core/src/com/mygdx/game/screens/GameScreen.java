package com.mygdx.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.ChickenSystem;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.PhysicsDebugSystem;
import com.mygdx.game.systems.PhysicsSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.game.utils.ChickenContactListener;
import com.mygdx.game.utils.Mappers;

public class GameScreen extends BaseScreen {

    private World world;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private PooledEngine engine;
    private Entity chicken;

    public GameScreen(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new ChickenContactListener());
        spriteBatch = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        camera = renderingSystem.getCamera();
        engine = new PooledEngine();

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new ChickenSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(renderingSystem);
        createPlayer();
        createFloor();
        createSpikes();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
        if(Mappers.STATE.get(chicken).get() == StateComponent.STATE_HIT)
            System.out.println("game finished");
    }

    @Override
    public void dispose() {
        world.dispose();
        spriteBatch.dispose();

    }

    private void createPlayer(){
        //create an empty entity
        Entity entity = engine.createEntity();

        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        ChickenComponent chicken = engine.createComponent(ChickenComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        texture.region = this.createTexture(Color.RED, false,32,32);

        // set the components data
        body.body = createBox(1,1,1,1,true);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(10,10,0);

        body.body.setUserData(entity);
        body.body.setLinearVelocity(5,0);
        // add components to entity
        entity.add(body);
        entity.add(position);
        entity.add(chicken);
        entity.add(texture);
        entity.add(state);
        entity.add(collision);

        engine.addEntity(entity);
        this.chicken = entity;

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
        fixtureDef.friction = 0.5f;
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

    private void createFloor(){
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        body.body = createBox(0,0.5f,100,0,false);
        body.body.setUserData(entity);
        entity.add(body);

        engine.addEntity(entity);
    }

    private void createSpikes() {
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);

        body.body = createBox(10, 1f, 1, 1, false);
        body.body.setUserData(entity);
        texture.region = createTexture(Color.GREEN, false, 32, 32);
        obstacle.type = ObstacleComponent.SPIKES;

        entity.add(body);
        entity.add(collision);
        entity.add(texture);
        entity.add(obstacle);
        entity.add(transform);
        engine.addEntity(entity);

    }


}
