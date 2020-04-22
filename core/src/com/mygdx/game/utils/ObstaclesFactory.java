package com.mygdx.game.utils;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ObstaclesFactory {

    PooledEngine engine;
    World world;

    public ObstaclesFactory(PooledEngine engine, World world) {
        this.engine = engine;
        this.world = world;
    }

    public abstract void createPlatform(float x, float y);
    public abstract void createSpike(float x, float y);

    public void createSpikes(float x, float y, int length){
        for(int i = 0; i<length; i++){
            createSpike(x+i, y);
        }
    }

    public void createPlatform(float x, float y, int width, int height){

        for(int i = 0; i<height; i++)
            for(int j = 0; j < width; j++)
            createPlatform(x+j, y+i);

    }

    public PooledEngine getEngine() {
        return engine;
    }
}

