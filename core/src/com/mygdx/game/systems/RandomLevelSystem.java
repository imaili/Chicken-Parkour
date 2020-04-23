package com.mygdx.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MainGame;
import com.mygdx.game.factories.PlatformFactory;
import com.mygdx.game.factories.SpeedPowerUpFactory;
import com.mygdx.game.factories.SpikesFactory;
import com.mygdx.game.server.Server;
import com.mygdx.game.utils.Mappers;
import com.mygdx.game.utils.QueuedEntity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import io.socket.emitter.Emitter;

public class RandomLevelSystem extends IteratingSystem {

    PooledEngine engine;
    private float accumulatedTime = 0;
    private long startTime;
    private boolean joined = false;
    private Server server;
    private Entity player;
    private ArrayList<QueuedEntity> renderQueue = new ArrayList<>();

    public RandomLevelSystem(World world, Entity player) {
        super(Family.all().get());
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

            entities.add(SpeedPowerUpFactory.create(Mappers.TRANSFORM.get(player).position.x + 20, 3, engine));
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
                        switch (qe.entity) {
                            case "speed_power_up":
                                entities.add(SpeedPowerUpFactory.create(Mappers.TRANSFORM.get(player).position.x + 20, 3, engine));
                                break;
                            case "platform":
                                entities.add(PlatformFactory.create(Mappers.TRANSFORM.get(player).position.x + 20, 1, engine));
                                break;
                            case "spikes":
                                entities.add(SpikesFactory.create(Mappers.TRANSFORM.get(player).position.x + 25, engine));
                        }
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
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine) engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
