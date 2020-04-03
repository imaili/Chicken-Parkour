package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;

public interface Menu extends Screen {
    void goTo(Class<? extends Menu> menu);
    void goBack();
}
