package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CoinComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.utils.Mappers;


public class CollisionSystem  extends IteratingSystem {
    ComponentMapper<CollisionComponent> collisionMapper;
    ComponentMapper<ChickenComponent> chickenMapper;

    public CollisionSystem() {
        // only need to worry about chicken collisions
        super(Family.all(CollisionComponent.class, ChickenComponent.class).get());

        collisionMapper = Mappers.COLLISION;
        chickenMapper = Mappers.CHICKEN;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get player collision component
        CollisionComponent collisionCom = collisionMapper.get(entity);
        Entity collidedEntity = collisionCom.collisionEntity;
        if (collidedEntity != null) {
            BodyComponent body = Mappers.BODY.get(entity);
            ObstacleComponent obstacle = Mappers.OBSTACLE.get(collidedEntity);
            CoinComponent coin = Mappers.COIN.get(collidedEntity);
            PowerUpComponent powerup = Mappers.POWERUP.get(collidedEntity);

            if (obstacle != null) {

                switch (obstacle.type) {
                    case ObstacleComponent.BOX:
                        //check if chicken is jumping on box, or crashing into it
                        collisionWithBox(entity);

                        break;
                    case ObstacleComponent.SPIKES:
                        //set chicken state to hit
                        StateComponent state = Mappers.STATE.get(entity);
                        if (state != null) {
                            state.set(StateComponent.STATE_HIT);
                        }
                        break;
                    default:
                }

            } else if (coin != null) {
                //TODO increment coin counter and delete coin entity

            } else if (powerup != null) {
                //TODO add powerup to chicken
            } else {
                StateComponent stateComponent = Mappers.STATE.get(entity);
                stateComponent.set(StateComponent.STATE_WALKING);
            }


        }
        collisionCom.collisionEntity = null;

    }

    private void collisionWithBox(Entity chickenEntity) {
        StateComponent state = Mappers.STATE.get(chickenEntity);
        state.set(StateComponent.STATE_WALKING);


    }

}
