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

    public abstract Entity createPlatform(float x, float y);
    public abstract Entity createSpike(float x, float y);

    public List<Entity> createSpikes(float x, float y, int length){
        List<Entity> list = new LinkedList<>();
        for(int i = 0; i<length; i++){
            list.add(createSpike(x+i, y));
        }
        return list;
    }

    public List<Entity> createPlatform(float x, float y, int width, int height){
        List<Entity> list = new LinkedList<>();

        for(int i = 0; i<height; i++)
            for(int j = 0; j < width; j++)
                list.add(createPlatform(x+j, y+i));

            return list;

    }

    protected PooledEngine getEngine() {
        return engine;
    }

    protected BodyFactory getBodyFactory() {
        return bodyFactory;
    }
}

