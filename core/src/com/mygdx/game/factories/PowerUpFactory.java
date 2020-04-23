package com.mygdx.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public abstract class PowerUpFactory {

    private PooledEngine engine;

    public PowerUpFactory(PooledEngine engine) {
        this.engine = engine;
    }

    public abstract Entity createSpeedUp(float x, float y);


    protected PooledEngine getEngine() {
        return engine;
    }
}
