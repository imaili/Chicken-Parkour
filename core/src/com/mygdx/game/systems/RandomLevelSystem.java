package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.factories.BasicBodyFactory;
import com.mygdx.game.factories.BasicObstaclesFactory;
import com.mygdx.game.factories.BodyFactory;
import com.mygdx.game.factories.ObstaclesFactory;
import com.mygdx.game.factories.PlatformFactory;
import com.mygdx.game.factories.BasicPowerUpFactory;
import com.mygdx.game.factories.PowerUpFactory;
import com.mygdx.game.factories.SpikesFactory;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Mappers;
import com.mygdx.game.utils.QueuedEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.socket.emitter.Emitter;

public class RandomLevelSystem extends IteratingSystem {

    PooledEngine engine;
    private float accumulatedTime = 0;
    private long startTime;
    private boolean joined = false;
    private Server server;
    private Entity player;
    private ArrayList<QueuedEntity> renderQueue = new ArrayList<>();
    private ObstaclesFactory obstaclesFactory;
    private BodyFactory bodyFactory;
    private PowerUpFactory powerUpFactory;


    public RandomLevelSystem(World world, Entity player, PooledEngine engine) {
        super(Family.all().get());
        this.engine = engine;
        bodyFactory = new BasicBodyFactory(world);
        obstaclesFactory = new BasicObstaclesFactory(engine, bodyFactory);
        powerUpFactory = new BasicPowerUpFactory(engine);

        server = Server.getInstance();
        this.player = player;

        server.listenForObstacles(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject server_message = (JSONObject) args[0];
                    String type = server_message.getString("type");
                    if (type.equals("add_obstacle")) {
                        JSONObject data = server_message.getJSONObject("data");
                        long offset = data.getLong("offset");
                        String entity = data.getString("entity");

                        renderQueue.add(new QueuedEntity(offset, entity));
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });
        server.listenForStartGame(new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject server_message = (JSONObject) args[0];
                    String type = server_message.getString("type");
                    if (type.equals("start_game")) {
                        JSONObject data = server_message.getJSONObject("data");
                        joined = !data.getBoolean("host");
                        startTime = new Date().getTime();
                        System.out.println(joined);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        });
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
        accumulatedTime += deltaTime;
        ArrayList<Entity> entities = new ArrayList<>();
        ArrayList<String> entityTypes = new ArrayList<>();
        boolean multiplayer = MainGame.getSingleton().getGame().isMultiPlayer();
        if (!joined && accumulatedTime > 2) {
            accumulatedTime = 0;
            //generate the to be added entities here...

            entities.addAll(ObstaclesGenerator.create(ObstaclesGenerator.TRIPLE_STAIRS, this));
            entityTypes.add("speed_power_up");

//            entities.add(PlatformFactory.create(Mappers.TRANSFORM.get(player).position.x + 20, 1, engine));
            // entityTypes.add("platform");

//            entities.add(SpikesFactory.create(Mappers.TRANSFORM.get(player).position.x + 25, engine));
            //      entityTypes.add("spikes");
        }

        if (multiplayer) {
            long offset = new Date().getTime() - startTime;
            if (joined) {
                Iterator<QueuedEntity> i = renderQueue.iterator();
                while (i.hasNext()) {
                    QueuedEntity qe = i.next(); // must be called before you can call i.remove()
                    if (qe.offset <= offset + deltaTime * 1000) {

                        entities.addAll(ObstaclesGenerator.create(Integer.parseInt(qe.entity), this ));
                        i.remove();
                    }
                }
            } else {
                for (String type : entityTypes) {
                    server.addObstacle(offset, type);
                }
            }
        }

        for (Entity entity :
                entities) {
            engine.addEntity(entity);
        }
    }



    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }



    private static class ObstaclesGenerator {

        public static final int DOUBLE_STAIRS = 1;
        public static final int TRIPLE_STAIRS = 2;
        public static final int SINGLE_SPIKE = 3;
        public static final int DOUBLE_SPIKE = 4;
        public static final int SPEED_UP = 5;


        private static List<Entity> create(int obstacle, RandomLevelSystem system) {
            switch(obstacle) {
                case DOUBLE_STAIRS:
                    return createStairs(2, system);
                case TRIPLE_STAIRS:
                    return createStairs(3, system);
                case SINGLE_SPIKE:
                    return system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 20, 1, 1);
                case DOUBLE_SPIKE:
                    return system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 20, 1, 2);
                case SPEED_UP:
                    LinkedList<Entity> list = new LinkedList<>();
                    list.add(system.powerUpFactory.createSpeedUp(Mappers.TRANSFORM.get(system.player).position.x+20, 3));
                    return list;
                default:
                    return new LinkedList<>();
            }
        }
        private static List<Entity> createStairs(int stairs, RandomLevelSystem system){

            List<Entity> entities = new LinkedList<>();

            for(int i = 1; i<=stairs; i++) {
                TransformComponent playerPosition = Mappers.TRANSFORM.get(system.player);
                entities.addAll(system.obstaclesFactory.createPlatform(playerPosition.position.x+20+i*3, 1, 1, i));
            }

            return entities;

        }
    }
}
