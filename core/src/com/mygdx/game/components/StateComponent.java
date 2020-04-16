package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StateComponent implements Component, Poolable {
    public static final int STATE_WALKING = 0; //previously STATE_NORMAL
    public static final int STATE_JUMPING = 1;
    public static final int STATE_HIT = 2; // does this mean "Dead"?
    public static final int STATE_FALLING = 3; //does this mean "Sliding"?

    private int state = 0;
    public float time =0;

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }

    public int get() {
        return state;
    }

    @Override
    public void reset() {
    }
}