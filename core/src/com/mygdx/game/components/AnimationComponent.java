package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utils.AssetsManager;

public class AnimationComponent implements Component, Pool.Poolable {

    @Override
    public void reset() {}

    public void jump(){
        AssetsManager.getJumpanimation();
    }

    public void walk(){
        AssetsManager.getwalkanimation();
    }


    //only used with SpeedPowerUp
    public void run(){
        AssetsManager.getRunanimation();
    }

    public void slide(){
        AssetsManager.getSlideanimation();
    }

    public void idle(){
        AssetsManager.getIdleanimation();
    }

    public void die(){};


}
