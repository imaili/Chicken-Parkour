package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;

public class ChickenSystem extends IteratingSystem {


    private static final Family FAMILY = Family.all(
            ChickenComponent.class,
            BodyComponent.class,
            StateComponent.class,
            TextureComponent.class,
            TransformComponent.class,
            CollisionComponent.class
    ).get();

    public ChickenSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BodyComponent bodyComponent = Mappers.BODY.get(entity);
        StateComponent stateComponent = Mappers.STATE.get(entity);
        if(Gdx.input.isTouched() && stateComponent.get() == StateComponent.STATE_NORMAL){
           Body body = bodyComponent.body;
           stateComponent.set(StateComponent.STATE_JUMPING);
           body.applyLinearImpulse(0,60, body.getPosition().x, body.getPosition().y, true);
        }
        if(bodyComponent.body.getLinearVelocity().y < 0) {
            stateComponent.set(StateComponent.STATE_FALLING);
        }








    }
}
