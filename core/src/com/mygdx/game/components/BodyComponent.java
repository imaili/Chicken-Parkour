package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BodyComponent implements Component, Poolable {

    public Body body;

    @Override
    public void reset() {
        body.getWorld().destroyBody(body);
    }
}
