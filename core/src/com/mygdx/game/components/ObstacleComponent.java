package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool. Poolable;

public class ObstacleComponent implements Component, Poolable {

    public static final int SPIKES = 0;
    public static final int BOX = 1;
    public static final int SINGLE = 0;
    public static final int DOUBLE = 1;

    public int type;
    public int size;
    @Override
    public void reset() {

    }
}
