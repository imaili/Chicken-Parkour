package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent implements Component, Poolable {

    public TextureRegion region = null;

    @Override
    public void reset() {
        region = null;
    }
}
