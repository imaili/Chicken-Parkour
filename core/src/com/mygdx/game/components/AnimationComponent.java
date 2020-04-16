package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {


    //Strings as key for assertmanager
    public IntMap<Animation> animationsMap = new IntMap<Animation>();
    @Override
    public void reset() {animationsMap = null;}

}
