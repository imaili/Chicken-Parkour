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
<<<<<<< HEAD

=======
    //Strings as key for assertmanager
    public IntMap<Animation> animationsMap = new IntMap<Animation>();
>>>>>>> 707b97e16aa953717b71f10005d177957a822e59
    @Override
    public void reset() {animation = null;}

}
