package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.factories.ObstaclesFactory;
import com.mygdx.game.factories.PowerUpFactory;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Constants;
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
    private float accumulatedTime = 0;
    private Server server;
    private Entity player;
    private ArrayList<QueuedEntity> renderQueue = new ArrayList<>();
    private ObstaclesFactory obstaclesFactory;
    private PowerUpFactory powerUpFactory;

    public RandomLevelSystem(Entity player, ObstaclesFactory obstaclesFactory, PowerUpFactory powerUpFactory) {
        super(Family.all().get());
        this.obstaclesFactory = obstaclesFactory;
        this.powerUpFactory = powerUpFactory;

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
        ArrayList<Entity> entities = new ArrayList<>();
        ArrayList<String> entityTypes = new ArrayList<>();
        GameScreen game = MainGame.getSingleton().getGame();
        boolean multiplayer =game.isMultiPlayer();
        boolean joined = game.isJoinedMultiplayer();
        long startTime = game.getStartTime();
        if (!joined && accumulatedTime > 2) {
            accumulatedTime = 0;
            //generate the to be added entities here...

            ObstaclesGenerator.create(Obstacles.SPEED_UP, this);
            entityTypes.add(Integer.toString(Obstacles.SPEED_UP));
            obstaclesFactory.createPlatform(Mappers.TRANSFORM.get(player).position.x+25, 1);


//            entities.add(SpikesFactory.create(Mappers.TRANSFORM.get(player).position.x + 25, engine));
            //      entityTypes.add("spikes");
        }

        if (multiplayer) {
            long offset = new Date().getTime() - startTime;
            if (joined) {
                Iterator<QueuedEntity> i = renderQueue.iterator();
                while (i.hasNext()) {
                    QueuedEntity qe = i.next(); // must be called before you can call i.remove()
                    if (qe.offset <= offset) {

                        ObstaclesGenerator.create(Integer.parseInt(qe.entity), this);

                        i.remove();
                    }
                    else {
                        break;
                    }
                }
            } else {
                for (String type : entityTypes) {
                    server.addObstacle(offset, type);
                }
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




        private static void create(int obstacle, RandomLevelSystem system) {
            switch (obstacle) {
                case Obstacles.DOUBLE_STAIRS:
                    createStairs(2, system);
                    break;
                case Obstacles.TRIPLE_STAIRS:
                    createStairs(3, system);
                    break;
                case Obstacles.SINGLE_SPIKE:
                    system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 20, 1, 1);
                    break;
                case Obstacles.DOUBLE_SPIKE:
                    system.obstaclesFactory.createSpikes(Mappers.TRANSFORM.get(system.player).position.x + 20, 1, 2);
                    break;
                case Obstacles.SPEED_UP:
                    system.powerUpFactory.createSpeedUp(Mappers.TRANSFORM.get(system.player).position.x + 20, 3);
                    break;
                default:
            }
        }

        private static void createStairs(int stairs, RandomLevelSystem system) {

            for (int i = 1; i <= stairs; i++) {
                TransformComponent playerPosition = Mappers.TRANSFORM.get(system.player);
                system.obstaclesFactory.createPlatform(playerPosition.position.x + 20 + i * 3, 1, 1, i);
            }


        }
    }
}
