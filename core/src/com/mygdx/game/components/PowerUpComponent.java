package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PowerUpComponent implements Component, Pool.Poolable {

    public static final int SPEEDUP = 0;
    public static final float SPEEDUP_DURATION = 10f;

    public int powerUp;
    public float duration;

    @Override
    public void reset() {

    }
}
