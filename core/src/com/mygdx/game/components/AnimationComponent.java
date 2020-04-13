package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utils.AssetsManager;

public class AnimationComponent implements Component, Pool.Poolable {

    public Animation animation;

    //Strings as key for assertmanager
    public IntMap<Animation> animationsMap = new IntMap<Animation>();
    @Override
    public void reset() {animation = null;}

}
