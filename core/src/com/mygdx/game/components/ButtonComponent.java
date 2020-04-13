package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Pool;

public class ButtonComponent implements Component, Pool.Poolable {

    public Button button;

    @Override
    public void reset() {
        button = null;
    }
}
