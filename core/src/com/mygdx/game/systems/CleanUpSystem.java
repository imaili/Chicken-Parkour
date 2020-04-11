package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.utils.Mappers;

public class CleanUpSystem extends IteratingSystem {


    public CleanUpSystem() {
        super(Family.all(CleanUpComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BodyComponent body = Mappers.BODY.get(entity);
        if(body.body.getPosition().y < 0){
            getEngine().removeEntity(entity);
        }

    }
}
