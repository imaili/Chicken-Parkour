package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.factories.ObstaclesFactory;
import com.mygdx.game.factories.PowerUpFactory;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Mappers;
import com.mygdx.game.utils.Obstacles;
import com.mygdx.game.utils.QueuedEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import io.socket.emitter.Emitter;

public class RandomLevelSystem extends IteratingSystem {

    PooledEngine engine;
    boolean multiplayer;
    boolean joined;
    long startTime;
    private float accumulatedTime = 0;
    private Server server;
    private Entity player;
    private ArrayList<QueuedEntity> renderQueue = new ArrayList<>();
    private ObstaclesFactory obstaclesFactory;
    private PowerUpFactory powerUpFactory;

    public RandomLevelSystem(Entity player, ObstaclesFactory obstaclesFactory, PowerUpFactory powerUpFactory,
                             boolean multiplayer, boolean joined, long startTime) {

        super(Family.all().get());
        this.obstaclesFactory = obstaclesFactory;
        this.powerUpFactory = powerUpFactory;
        this.multiplayer = multiplayer;
        this.joined = joined;
        this.startTime = startTime;
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
                        int entity = data.getInt("entity");

                        renderQueue.add(new QueuedEntity(offset, entity));
                        System.out.println(renderQueue.size());
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
        int generatedObstacle = 0;

        if (!joined && accumulatedTime > 3) {
            accumulatedTime = 0;
            //generate the to be added entities here...

            generatedObstacle = ObstaclesGenerator.createRandom(this);
        }

        if (multiplayer) {
            long offset = new Date().getTime() - startTime;
            if (joined) {
                Iterator<QueuedEntity> i = renderQueue.iterator();
                while (i.hasNext()) {
                    QueuedEntity qe = i.next(); // must be called before you can call i.remove()
                    if (qe.offset <= offset) {

                        ObstaclesGenerator.create(qe.entity, this);

                        renderQueue.remove(qe);
                    } else {
                        break;
                    }
                }
            } else {
                server.addObstacle(offset, generatedObstacle);
            }
        }
    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }


    private static class ObstaclesGenerator {


        private static int createRandom(RandomLevelSystem system) {
            int obstacle = (int) (Math.random() * Obstacles.OBSTACLE_NUMBER);
            create(obstacle, system);
            return obstacle;
        }

        private static void create(int obstacle, RandomLevelSystem system) {
            switch (obstacle) {
                case Obstacles.SINGLE_PLATFORM:
                    system.obstaclesFactory.createPlatform(Mappers.TRANSFORM.get(system.player).position.x + 60, 4.3f);
                case Obstacles.DOUBLE_STAIRS:
                    createStairs(2, system);
                    break;
                case Obstacles.TRIPLE_STAIRS:
                    createStairs(3, system);
                    break;
                case Obstacles.SINGLE_SPIKE:
                    system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 60, 3.5f, 1);
                    break;
                case Obstacles.DOUBLE_SPIKE:
                    system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 60, 3.5f, 2);
                    break;
                case Obstacles.SPEED_UP:
                    system.powerUpFactory.createSpeedUp(Mappers.TRANSFORM.get(system.player).position.x + 60, 9);
                    break;
                case Obstacles.LEAF:
                    system.powerUpFactory.createLeaf(Mappers.TRANSFORM.get(system.player).position.x + 60, 9f);
                default:
            }
        }

        private static void createStairs(int stairs, RandomLevelSystem system) {

            for (int i = 1; i <= stairs; i++) {
                TransformComponent playerPosition = Mappers.TRANSFORM.get(system.player);
                system.obstaclesFactory.createPlatform(playerPosition.position.x + 60 + i * 10, 4.3f, 1, i);
            }


        }


    }
}
