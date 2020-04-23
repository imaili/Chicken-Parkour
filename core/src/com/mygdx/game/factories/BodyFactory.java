package com.mygdx.game.factories;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BodyFactory {

    World world;
    public BodyFactory(World world) {

        this.world = world;
    }

    public abstract Body createRectangle(float x, float y, float width, float height, boolean dynamic);
    public abstract Body createTriangle(float x, float y, boolean floating);

    protected World getWorld(){
        return world;
    }
}
