package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.math.Vector2;

public interface ButtonFactory {

    MenuButton createButton(Class<? extends MenuButton> buttonClass, String name, Vector2 position);

    ExitButton createExitButton(String name, Vector2 position);

    GoBackButton createGoBackButton(String name, Vector2 position);

    MusicButton createMusicButton(String name, Vector2 position);

    PauseButton createPauseButton(String name, Vector2 position);

}
