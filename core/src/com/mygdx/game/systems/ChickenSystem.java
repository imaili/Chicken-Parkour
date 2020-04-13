package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;

public class ChickenSystem extends IteratingSystem {




    public ChickenSystem() {
        super(Family.all(ChickenComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyComponent = Mappers.BODY.get(entity);
        StateComponent stateComponent = Mappers.STATE.get(entity);
        if(Gdx.input.isTouched() && stateComponent.get() == StateComponent.STATE_WALKING){
           Body body = bodyComponent.body;
           stateComponent.set(StateComponent.STATE_JUMPING);
           body.applyLinearImpulse(0,60, body.getPosition().x, body.getPosition().y, true);
        }
        if(bodyComponent.body.getLinearVelocity().y < 0) {
            stateComponent.set(StateComponent.STATE_FALLING);
        }








    }
}
