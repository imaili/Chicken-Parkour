package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.CameraComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;

public class CameraSystem extends IteratingSystem {

    public CameraSystem() {
        super(Family.all(CameraComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CameraComponent camera = Mappers.CAMERA.get(entity);
        TransformComponent position = Mappers.TRANSFORM.get(camera.target);
        if(camera.camera.position.x < position.position.x+7)
            camera.camera.translate(deltaTime*5, 0);

    }
}
