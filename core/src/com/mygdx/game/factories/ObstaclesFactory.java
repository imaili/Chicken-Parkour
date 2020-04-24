package com.mygdx.game.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.List;

public abstract class ObstaclesFactory {

    private PooledEngine engine;
    private BodyFactory bodyFactory;


    public ObstaclesFactory(PooledEngine engine, BodyFactory bodyFactory) {
        this.engine = engine;
        this.bodyFactory = bodyFactory;
    }

    public abstract void createPlatform(float x, float y);
    public abstract void createSpike(float x, float y);

    public void createSpikes(float x, float y, int length){
        for(int i = 0; i<length; i++)
            createSpike(x+i*3, y);
    }

    public void createPlatform(float x, float y, int width, int height){
        List<Entity> list = new LinkedList<>();

        for(int i = 0; i<height; i++)
            for(int j = 0; j < width; j++)
                createPlatform(x+j*3, y+i*3);


    }

    protected PooledEngine getEngine() {
        return engine;
    }

    protected BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}

