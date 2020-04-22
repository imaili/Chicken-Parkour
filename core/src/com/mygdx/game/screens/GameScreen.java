package com.mygdx.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
import com.mygdx.game.components.PowerUp;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.systems.CameraSystem;
import com.mygdx.game.screens.menu.GameOverMenu;
import com.mygdx.game.screens.menu.PauseMenu;
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
    private Entity ground1;
    private Entity ground2;
    private int ground1end = 100;
    private int ground2end = 200;
    private Entity background;
    private MainGame game;

    private boolean isMultiPlayer = true;

    private PauseMenu pauseMenu;
    private boolean paused;
    private Texture pauseTexture = new Texture(Constants.EXIT_MENU_PATH);
    private int pauseTextureX = Gdx.graphics.getWidth() - pauseTexture.getWidth();
    private int pauseTextureY = Gdx.graphics.getHeight() - pauseTexture.getHeight();
    private RenderingSystem renderingSystem;

    private Server server;
    private String game_id;
    private String player_id;

    protected static final String MUSIC_PATH = Constants.MUSIC_GAME_PATH;
    protected final Music MUSIC = MainGame.getSingleton().getAssetManager().get(MUSIC_PATH, Music.class);

    public GameScreen(MainGame game) {
        super(game);
        this.game = game;
        this.paused = false;
        this.server = Server.getInstance();
    }

    @Override
    public void show() {
        super.show();

        String[] info = this.server.startGame();
        game_id = info[1];
        player_id = info[0];

        world = new World(new Vector2(0, -13f), true);
        world.setContactListener(new ChickenContactListener());
        spriteBatch = new SpriteBatch();
        renderingSystem = new RenderingSystem(spriteBatch, 2);

        camera = renderingSystem.getCamera();
        engine = new PooledEngine();
        createPlayer();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new ChickenSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RandomLevelSystem(world, player));
        engine.addSystem(new CleanUpSystem(world, camera));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new CameraSystem());
        ground1 = createGround(50);
        ground2 = createGround(150);
        engine.addEntity(ground1);
        engine.addEntity(ground2);

        createCameraEntity();
       // createBackground();


    }

    private boolean buttonPressed() {
        if (Gdx.input.isTouched()) {
            Rectangle buttonRectangle = new Rectangle(pauseTextureX, pauseTextureY, pauseTexture.getWidth(), pauseTexture.getHeight());
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            return buttonRectangle.contains(mousePosition);
        }
        return false;
    }

    @Override
    public void render(float delta) {
        if (!paused || isMultiPlayer) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            engine.update(delta);
            spriteBatch = new SpriteBatch();
            spriteBatch.begin();
            spriteBatch.draw(pauseTexture, pauseTextureX, pauseTextureY);
            spriteBatch.end();

            server.updatePlayerLocation(Mappers.BODY.get(player).body.getPosition().x, Mappers.BODY.get(player).body.getPosition().y);

            if (Mappers.STATE.get(player).get() == StateComponent.STATE_HIT) {
                //game.setScreen(new GameOverScreen(game));
                goTo(GameOverMenu.class);
                int score = 50000000;
                server.endGame(score);
            }
            if (Mappers.BODY.get(player).body.getPosition().x > ground1end) {

                Mappers.BODY.get(ground1).body = createBox(ground2end + 50, 0.5f, 100, 0, false);
                ground1end += 200;
            } else if (Mappers.BODY.get(player).body.getPosition().x > ground2end) {

                Mappers.BODY.get(ground2).body = createBox(ground1end + 50, 0.5f, 100, 0, false);
                ground2end += 200;
            }

            if (buttonPressed())
                pause();
        }
        if (paused) {
            pauseMenu.render(delta);
        }

    }

    @Override
    public void dispose() {
        world.dispose();
        spriteBatch.dispose();

    }

    public void startMusic() {
        if (game.getMusic()) {
            MUSIC.setLooping(true);
            if (MUSIC.isPlaying())
                MUSIC.pause();
            MUSIC.setVolume(0.75f);
            MUSIC.play();
        }
    }

    public void stopMusic() {
        MUSIC.stop();
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
        PowerUpComponent powerUp = engine.createComponent(PowerUpComponent.class);
        // set the components data

        texture.region = new TextureRegion((Texture)game.getAssetManager().get(Constants.WALK_1_PATH));

        TextureAtlas atlas = new TextureAtlas(Constants.WALK_ATLAS_PATH);
        Animation ani = new Animation<TextureRegion>(0.1f, atlas.getRegions(), Animation.PlayMode.LOOP);
        animation.animationsMap.put(StateComponent.STATE_WALKING, ani );

        atlas = new TextureAtlas(Constants.JUMP_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_JUMPING, ani );

        atlas = new TextureAtlas(Constants.DEAD_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_HIT, ani );
        /*
        atlas = new TextureAtlas(Constants.SLIDE_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_FALLING, ani );*/


       // body.body = createBox(10,10,10,10, true); // used to be (1,1,1,1,true) --> Dinosaur outside of the screen??

        body.body = createBox(2,1,0.5f,2.1f, true);
        body.body.setLinearVelocity(5,0);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(2,1,0);
        position.scale.set(0.2f,0.2f);     // this line resizes the Dinosaur... but brings some more wrong behaviour..

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
        player.add(powerUp);
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

    private void createCameraEntity() {
        CameraComponent camera = engine.createComponent(CameraComponent.class);
        camera.camera = this.camera;
        camera.target = player;

        Entity entity = engine.createEntity();
        entity.add(camera);
        engine.addEntity(entity);
    }

    @Override
    public void pause() {
        paused = true;
        pauseMenu = new PauseMenu(this);
        pauseMenu.setInputProcessor();
        if (!isMultiPlayer) {
            renderingSystem.setProcessing(false);

        }
    }

    @Override
    public void resume() {
        paused = false;
        pauseMenu.removeInputProcessor();
        if (!isMultiPlayer)
            renderingSystem.setProcessing(true);
    }

    public int getScore() {
        // TODO
        return 40;
    }

    public boolean isMultiPlayer() {
        return isMultiPlayer;
    }

    public void setMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
    }

}
