package com.mygdx.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public abstract class PowerUpFactory {

    private PooledEngine engine;
    private BodyFactory bodyFactory;

    public PowerUpFactory(PooledEngine engine, BodyFactory bodyFactory) {
        this.engine = engine;
        this.bodyFactory = bodyFactory;
    }

    public abstract Entity createSpeedUp(float x, float y);


    protected PooledEngine getEngine() {
        return engine;
    }

    protected BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}
