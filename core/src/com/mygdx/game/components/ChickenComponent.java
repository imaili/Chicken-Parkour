package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ChickenComponent implements Component, Poolable {

    public static final int JUMP_IMPULSE_NORMAL = 6300;
    public static final int SPEED_NORMAL = 15;

    public int leaves = 0;
    public int jumpImpulse = 6300;


    @Override
    public void reset() {

    }
}
