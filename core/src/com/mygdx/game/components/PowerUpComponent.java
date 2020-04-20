package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PowerUpComponent implements Component, Pool.Poolable {



    public PowerUp powerUp;
    public float duration;

    @Override
    public void reset() {powerUp = null;}
}
