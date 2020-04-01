package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CoinComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.utils.Mappers;

public class CollisionSystem  extends IteratingSystem {
    ComponentMapper<CollisionComponent> collisionMapper;
    ComponentMapper<ChickenComponent> chickenMapper;

    public CollisionSystem(World world) {
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
        if(collidedEntity != null){
            ObstacleComponent obstacle = Mappers.OBSTACLE.get(entity);
            CoinComponent coin = Mappers.COIN.get(entity);
            PowerUpComponent powerup = Mappers.POWERUP.get(entity);

            if(obstacle != null){
                switch(obstacle.type){
                    case ObstacleComponent.BOX:
                        break;
                    case ObstacleComponent.SPIKES:
                        System.out.println("DIE");
                        break;
                    default:
                }
                collisionCom.collisionEntity = null;

            } else if(coin != null) {
                //TODO increment coin counter and delete coin entity

            }else if(powerup != null) {
                //TODO add powerup to chicken
            }
        }

    }

}
