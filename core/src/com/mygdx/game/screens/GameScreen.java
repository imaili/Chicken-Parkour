package com.mygdx.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CameraComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.CameraSystem;
import com.mygdx.game.systems.ChickenSystem;
import com.mygdx.game.systems.CleanUpSystem;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.RandomLevelSystem;
import com.mygdx.game.systems.PhysicsDebugSystem;
import com.mygdx.game.systems.PhysicsSystem;
import com.mygdx.game.systems.RenderingSystem;
import com.mygdx.game.utils.ChickenContactListener;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.Mappers;

public class GameScreen extends BaseScreen implements Menu {
    private static final String MUSIC_TYPE = "game";
    private World world;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private PooledEngine engine;
    private Entity player;
    private Entity ground1;
    private Entity ground2;
    private int ground1end = 100;
    private int ground2end = 200;
    private MainGame game;
/*

    private PauseMenu pauseMenu;
    private Stage stage;
    private boolean paused;*/

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        /*this.pauseMenu = new PauseMenu(this);
        this.stage = createPauseButtonStage();
        this.paused = false;*/
    }

    /*private Stage createPauseButtonStage() {
        Stage stage = new Stage();
        stage.addActor((new PauseButton(this)).getButton());
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!multiplexer.getProcessors().contains(stage, true))
            multiplexer.addProcessor(stage);
        return stage;
    }*/

    @Override
    public void show() {
        super.show();

        world = new World(new Vector2(0, -13f), true);
        world.setContactListener(new ChickenContactListener());
        spriteBatch = new SpriteBatch();
        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch, 2);
        camera = renderingSystem.getCamera();
        engine = new PooledEngine();

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new ChickenSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RandomLevelSystem(world));
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new CameraSystem());
        createPlayer();
        ground1 = createGround(50);
        ground2 = createGround(150);
        engine.addEntity(ground1);
        engine.addEntity(ground2);

        createCameraEntity();


    }

    @Override
    public void render(float delta) {
        /*if (!paused) {*/
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            engine.update(delta);
            /*stage.draw();
            stage.act();*/
            if (Mappers.STATE.get(player).get() == StateComponent.STATE_HIT) {
                //game.setScreen(new GameOverScreen(game));
                goTo(GameOverScreen.class);
            }
            if(Mappers.BODY.get(player).body.getPosition().x > ground1end) {

                Mappers.BODY.get(ground1).body = createBox(ground2end+50, 0.5f, 100, 0, false);
                ground1end += 200;
            }
            else if(Mappers.BODY.get(player).body.getPosition().x > ground2end) {

                Mappers.BODY.get(ground2).body = createBox(ground1end+50, 0.5f, 100, 0, false);
                ground2end += 200;
            }
            System.out.println(Mappers.BODY.get(player).body.getPosition().x);
        /*} else {
            pauseMenu.render(delta);
        } */
    }

    @Override
    public void dispose() {
        world.dispose();
        spriteBatch.dispose();

    }

    public void startMusic() {

    }

    public void stopMusic() {
    }



    private void createPlayer(){
        //create an empty entity
        player = engine.createEntity();

        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        ChickenComponent chicken = engine.createComponent(ChickenComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        StateComponent state = engine.createComponent(StateComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        AnimationComponent animation = engine.createComponent(AnimationComponent.class);

        // set the components data

        texture.region = new TextureRegion((Texture)game.getAssetsManager().get(Constants.RUN_2_PATH));
        position.scale.set(0.15f, 0.15f);

        body.body = createBox(2,1,1,1, true);
        body.body.setLinearVelocity(5,0);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(2,1,0);

        body.body.setUserData(player);
        // add components to entity
        player.add(body);
        player.add(position);
        player.add(chicken);
        player.add(texture);
        player.add(animation);
        player.add(state);
        player.add(collision);
        player.add(texture);

        engine.addEntity(player);




    }





    private Entity createGround(int x){
        Entity ground = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        body.body = createBox(x,0.5f,100,0,false);
        body.body.setUserData(ground);
        ground.add(body);
        return ground;
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


    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameOverScreen.class))
            goToGameOverScreen();
    }

    @Override
    public void goBack() {

    }

    public void goToGameOverScreen() {
        stopMusic();
        GameOverScreen gameOverScreen = new GameOverScreen(game);
        gameOverScreen.startMusic();
        game.setMenu(gameOverScreen);
    }

    private void createCameraEntity() {
        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera.camera = this.camera;
        camera.target = player;

        Entity entity = engine.createEntity();
        entity.add(camera);
        engine.addEntity(entity);
    }
    /*
    @Override
    public void pause() {
        paused = true;
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        multiplexer.removeProcessor(stage);
        pauseMenu.setInputProcessor();
    }

    @Override
    public void resume() {
        paused = false;
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!multiplexer.getProcessors().contains(stage, true))
            multiplexer.addProcessor(stage);
        pauseMenu.removeInputProcessor();
    } */
}
