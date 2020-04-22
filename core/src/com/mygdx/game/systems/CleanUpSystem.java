package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CleanUpComponent;
import com.mygdx.game.utils.Mappers;

public class CleanUpSystem extends IteratingSystem {

    World world;
    OrthographicCamera camera;
    public CleanUpSystem(World world, OrthographicCamera camera)
    {
        super(Family.all(CleanUpComponent.class).get());
        this.world = world;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.BODY.get(entity);
        if(body.body != null && body.body.getPosition().x+1 < camera.position.x- Gdx.graphics.getWidth()/64){
            getEngine().removeEntity(entity);

        }

    }
}
