package com.mygdx.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CameraComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.factories.BasicBodyFactory;
import com.mygdx.game.factories.BasicObstaclesFactory;
import com.mygdx.game.factories.BasicPowerUpFactory;
import com.mygdx.game.factories.BodyFactory;
import com.mygdx.game.factories.LabelFactory;
import com.mygdx.game.factories.ObstaclesFactory;
import com.mygdx.game.factories.PowerUpFactory;
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
import com.mygdx.game.utils.Background;
import com.mygdx.game.utils.ChickenContactListener;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.Mappers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.socket.emitter.Emitter;

public class GameScreen extends BaseScreen implements Menu {
    private static final String MUSIC_TYPE = "game";
    private World world;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private PooledEngine engine;
    private Entity player;
    private Entity ground1;
    private Entity ground2;
    private int ground1end = 50;
    private int ground2end = 150;
    //private Entity background;
    private MainGame game;

    private boolean isMultiPlayer;
    private boolean isJoinedMultiplayer;
    private long startTime = 0;

    private PauseMenu pauseMenu;
    private boolean paused;
    private Texture pauseTexture;
    private int pauseTextureX;
    private int pauseTextureY;
    private RenderingSystem renderingSystem;
    private BodyFactory bodyFactory;
    private ObstaclesFactory obstaclesFactory;
    private PowerUpFactory powerUpFactory;
    private RandomLevelSystem randomLevelSystem;
    private Server server;
    private String game_id;
    private String player_id;
    Emitter.Listener endGameListener;
    Label score;


    private Background background;

    protected static final String MUSIC_PATH = Constants.MUSIC_GAME_PATH;
    protected final Music MUSIC = MainGame.getSingleton().getAssetManager().get(MUSIC_PATH, Music.class);
    private JSONArray players;

    public GameScreen(MainGame game, boolean isMultiPlayer, boolean isJoinedMultiplayer) {
        super(game);
        this.game = game;
        this.isMultiPlayer = isMultiPlayer;
        this.isJoinedMultiplayer = isJoinedMultiplayer;
        this.paused = false;
        this.server = Server.getInstance();
        this.startTime = new Date().getTime();
        pauseTexture = game.getAssetManager().get(Constants.EXIT_MENU_PATH);
        pauseTextureX = Gdx.graphics.getWidth() - pauseTexture.getWidth();
        pauseTextureY = Gdx.graphics.getHeight() - pauseTexture.getHeight();

        this.server.listenForEndGame(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject message = (JSONObject) args[0];
                for (int i = 0; i < players.length(); i++) {
                    try {
                        JSONObject player = players.getJSONObject(i);
                        if (player.getString("id").equals(message.getString("player_id"))) {
                            JSONObject data = message.getJSONObject("data");
                            player.put("score", data.getString("score"));
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        this.server.listenForLeaveGame(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //host left, migrate to own game
                randomLevelSystem.disabledJoined();
            }
        });
    }

    @Override
    public void show() {
        super.show();

        world = new World(new Vector2(0, -36f), true);
        world.setContactListener(new ChickenContactListener());
        spriteBatch = new SpriteBatch();
        renderingSystem = new RenderingSystem(spriteBatch, 2);

        camera = renderingSystem.getCamera();
        engine = new PooledEngine();

        bodyFactory = new BasicBodyFactory(world);
        obstaclesFactory = new BasicObstaclesFactory(engine, bodyFactory);
        powerUpFactory = new BasicPowerUpFactory(engine, bodyFactory);

        createPlayer();
        randomLevelSystem = new RandomLevelSystem(player, obstaclesFactory, powerUpFactory, isMultiPlayer, isJoinedMultiplayer, startTime);
        background = Background.createGameBackground();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new ChickenSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(randomLevelSystem);
        engine.addSystem(new CleanUpSystem(world, camera));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new CameraSystem());
        ground1 = createGround(0);
        ground2 = createGround(100);
        engine.addEntity(ground1);
        engine.addEntity(ground2);

        createCameraEntity();
        createScoreLabel();
    }

    private void createScoreLabel() {
        score = LabelFactory.create("Score: 0");
        score.setPosition(0, Gdx.graphics.getHeight() - score.getHeight());
    }

    private boolean buttonPressed() {
        if (Gdx.input.isTouched()) {
            Rectangle buttonRectangle = new Rectangle(pauseTextureX, pauseTextureY, pauseTexture.getWidth(), pauseTexture.getHeight());
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            return buttonRectangle.contains(mousePosition);
        }
        return false;
    }

    private final SpriteBatch buttonBatch = new SpriteBatch();
    private final SpriteBatch scoreBatch = new SpriteBatch();

    private void drawButton() {
        buttonBatch.begin();
        buttonBatch.draw(pauseTexture, pauseTextureX, pauseTextureY);
        buttonBatch.end();
    }

    public Entity getPlayer() {
        return player;
    }

    private void drawScore() {
        scoreBatch.begin();
        score.setText("Score: " + this.getScore());
        score.draw(scoreBatch, 1);
        scoreBatch.end();
    }

    public String getPlayerId() {
        return player_id;
    }

    public void setGameData(String player_id, String game_id) {
        this.player_id = player_id;
        this.game_id = game_id;
    }

    public void setPlayers(JSONArray players) {
        this.players = players;
    }

    public JSONArray getPlayers() {
        return this.players;
    }

    @Override
    public void render(float delta) {
        if (!paused || isMultiPlayer) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            background.render();
            engine.update(delta);
            drawButton();
            drawScore();

            //server.updatePlayerLocation(Mappers.BODY.get(player).body.getPosition().x, Mappers.BODY.get(player).body.getPosition().y);

            if (Mappers.STATE.get(player).get() == StateComponent.STATE_HIT) {
                //game.setScreen(new GameOverScreen(game));
                server.endGame(getScore());
                goTo(GameOverMenu.class);

            }
            if (Mappers.BODY.get(player).body.getPosition().x > ground1end) {

                Mappers.BODY.get(ground1).body = bodyFactory.createRectangle(ground2end + 50, 1, 100, 1, false);
                ground1end += 200;
            } else if (Mappers.BODY.get(player).body.getPosition().x > ground2end) {

                Mappers.BODY.get(ground2).body = bodyFactory.createRectangle(ground1end + 50, 1, 100, 1, false);
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


    private void createPlayer() {
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
        texture.region = new TextureRegion((Texture) game.getAssetManager().get(Constants.WALK_1_PATH));

        TextureAtlas atlas = new TextureAtlas(Constants.WALK_ATLAS_PATH);
        Animation ani = new Animation<TextureRegion>(0.1f, atlas.getRegions(), Animation.PlayMode.LOOP);
        animation.animationsMap.put(StateComponent.STATE_WALKING, ani);

        atlas = new TextureAtlas(Constants.JUMP_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_JUMPING, ani);

        atlas = new TextureAtlas(Constants.DEAD_ATLAS_PATH);
        ani = new Animation<TextureRegion>(0.1f, atlas.getRegions());
        animation.animationsMap.put(StateComponent.STATE_HIT, ani);

        body.body = bodyFactory.createRectangle(2, 3, 1.3f, 3, true);
        body.body.setLinearVelocity(15, 0);
        // set object position (x,y,z) z used to define draw order 0 first drawn
        position.position.set(2, 1, 0);
        position.scale.set(0.7f, 0.7f);

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


    private Entity createGround(int x) {
        Entity entity = engine.createEntity();
        BodyComponent body = engine.createComponent(BodyComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);

        texture.region = new TextureRegion((Texture) game.getAssetManager().get(Constants.FLOOR_PATH));
        body.body = bodyFactory.createRectangle(x, 1, 100, 1, false);
        body.body.setUserData(entity);
        transform.position.set(x, 1, 0);
        transform.scale.set(30, 4.3f);
        entity.add(transform);
        entity.add(texture);
        entity.add(body);
        return entity;
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
        try {
            throw new Exception("bleh");
        } catch (Exception e) {
            e.printStackTrace();
        }

        pauseMenu.removeInputProcessor();
        if (!isMultiPlayer)
            renderingSystem.setProcessing(true);
    }

    public int getScore() {
        return (int) Mappers.BODY.get(player).body.getPosition().x + Mappers.CHICKEN.get(player).leaves * 1000;
    }

    public boolean isMultiPlayer() {
        return isMultiPlayer;
    }

    public boolean isJoinedMultiplayer() {
        return isJoinedMultiplayer;
    }

    public long getStartTime() {
        return startTime;
    }

    private Menu previousMenu;

    public void setPreviousMenu(Menu menu) {
        this.previousMenu = menu;
    }

    public Menu getPreviousMenu() {
        return previousMenu;
    }

}
