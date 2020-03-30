package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.PlayerComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.utils.Mappers;

public class PlayerSystem extends IteratingSystem {


    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            BodyComponent.class,
            StateComponent.class
    ).get();

    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = Mappers.PLAYER.get(entity);
        BodyComponent bodyComponent = Mappers.BODY.get(entity);
        StateComponent stateComponent = Mappers.STATE.get(entity);

        //TODO implement here logic of player movement
    }
}
