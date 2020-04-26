package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ChickenComponent implements Component, Poolable {

    public int leaves = 0;
    public int jumpImpulse = 6300;


    @Override
    public void reset() {

    }
}
