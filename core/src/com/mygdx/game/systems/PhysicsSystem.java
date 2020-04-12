package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;

import java.util.Map;

public class PhysicsSystem extends IteratingSystem {
    // create variables to stabilize speed
    private static final float MAX_STEP_TIME = 1/60f;
    private static float accumulator = 0f;

    private World world;
    private Array<Entity> bodiesQueue;

    private ComponentMapper<BodyComponent> bm = Mappers.BODY;
    private ComponentMapper<TransformComponent> tm = Mappers.TRANSFORM;

    public PhysicsSystem(World world) {
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if(accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            //Loop through all Entities and update our components
            for (Entity entity : bodiesQueue) {
                // get components
                TransformComponent tfm = tm.get(entity);
                BodyComponent bodyComp = bm.get(entity);
                // get position from body
                Vector2 position = bodyComp.body.getPosition();
                // update our transform to match body position
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;


            }
        }
        // empty queue
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add Items to queue
        bodiesQueue.add(entity);
    }
}
