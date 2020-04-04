package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StateComponent implements Component, Poolable {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_HIT = 2;
    public static final int STATE_FALLING = 3;

    private int state = 0;

    public void set(int newState) {
        state = newState;
    }

    public int get() {
        return state;
    }

    @Override
    public void reset() {
    }
}