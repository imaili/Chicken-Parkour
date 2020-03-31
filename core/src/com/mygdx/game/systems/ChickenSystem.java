package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.utils.Mappers;

public class ChickenSystem extends IteratingSystem {


    private static final Family FAMILY = Family.all(
            ChickenComponent.class,
            BodyComponent.class,
            StateComponent.class
    ).get();

    public ChickenSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ChickenComponent playerComponent = Mappers.PLAYER.get(entity);
        BodyComponent bodyComponent = Mappers.BODY.get(entity);
        StateComponent stateComponent = Mappers.STATE.get(entity);

        //TODO implement here logic of player movement
    }
}
