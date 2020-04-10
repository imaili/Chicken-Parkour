package com.mygdx.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.screens.menu.GameOverMenu;
import com.mygdx.game.server.Server;
import com.mygdx.game.systems.AnimationSystem;
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
    private Entity background;
    private MainGame game;
/*

    private PauseMenu pauseMenu;
    private Stage stage;
    private boolean paused;*/
    private Server server;
    private String game_id;
    private String player_id;

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        /*this.pauseMenu = new PauseMenu(this);
        this.stage = createPauseButtonStage();
        this.paused = false;*/
        this.server = Server.getInstance();
        String[] info = this.server.startGame();
        game_id = info[0];
        player_id = info[1];
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
        RenderingSystem renderingSystem = new RenderingSystem(spriteBatch);
        camera = renderingSystem.getCamera();
        engine = new PooledEngine();

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new ChickenSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RandomLevelSystem(world));
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        createPlayer();
        createFloor();
        createBackground();


    }

    @Override
    public void render(float delta) {
        /*if (!paused) {*/
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            engine.update(delta);
            /*stage.draw();
            stage.act();*/
            if (Mappers.BODY.get(player).body.getPosition().x < 0.5 || Mappers.STATE.get(player).get() == StateComponent.STATE_HIT) {
                goTo(GameOverMenu.class);
            }
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
        if (game.getMusic())
            game.getAssetsManager().play_music(MUSIC_TYPE);
    }

    public void stopMusic() {
        game.getAssetsManager().stop_music();
    }


    private void createBackground(){
        background = engine.createEntity();
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent textureRegion = engine.createComponent(TextureComponent.class);

        Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(Constants.GAME_BACKGROUND_5_PATH));
        Pixmap pixmapScreenSize = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), pixmapOriginal.getFormat());
        pixmapScreenSize.drawPixmap(pixmapOriginal,
                0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(),
                0, 0, pixmapScreenSize.getWidth(), pixmapScreenSize.getHeight()
        );
        Texture texture = new Texture(pixmapScreenSize);
        pixmapOriginal.dispose();
        pixmapScreenSize.dispose();

        //texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        textureRegion.region = new TextureRegion(texture);
        position.scale.set(1,1);
        float x = camera.position.x;
        float y = camera.position.y;
        position.position.set(x,y,2);

        background.add(textureRegion);
        background.add(position);

        engine.addEntity(background);

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

        Pixmap pmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
        pmap.setColor(Color.RED);
        pmap.fill();
        //texture.region = new TextureRegion(new Texture(pmap));
        pmap.dispose();
        TextureAtlas atlas = new TextureAtlas(Constants.WALK_ATLAS_PATH);
        Animation ani = new Animation<TextureRegion>(0.1f, atlas.getRegions(), Animation.PlayMode.LOOP);
        animation.animationsMap.put(StateComponent.STATE_WALKING, ani );

        atlas = new TextureAtlas(Constants.JUMP_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_JUMPING, ani );

        atlas = new TextureAtlas(Constants.DEAD_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_HIT, ani );

        atlas = new TextureAtlas(Constants.SLIDE_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_FALLING, ani );

        atlas = new TextureAtlas(Constants.RUN_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_RUN, ani );

        body.body = createBox(10,10,10,10, true); // used to be (1,1,1,1,true) --> Dinosaur outside of the screen??

        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(1,1,0);
        //position.scale.set(0.3f,0.3f);     // this line resizes the Dinosaur... but brings some more wrong behaviour..

        state.set(StateComponent.STATE_WALKING);

        body.body.setUserData(player);
        // add components to entity
        player.add(body);
        player.add(position);
        player.add(chicken);
        player.add(texture);
        player.add(animation);
        player.add(state);
        player.add(collision);

        engine.addEntity(player);




    }





    private void createFloor(){
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        body.body = createBox(0,0.5f,100,0,false);
        body.body.setUserData(entity);
        entity.add(body);
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


    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(GameOverMenu.class))
            goToGameOverMenu();
    }

    @Override
    public void goBack() {

    }

    public void goToGameOverMenu() {
        stopMusic();
        GameOverMenu menu = new GameOverMenu(this);
        menu.setInputProcessor();
        game.setMenu(menu);
        menu.startMusic();
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
