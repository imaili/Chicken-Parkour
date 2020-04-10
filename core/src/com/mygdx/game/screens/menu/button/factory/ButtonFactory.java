package com.mygdx.game.screens.menu.button.factory;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.menu.button.ExitButton;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.menu.button.MusicButton;
import com.mygdx.game.screens.menu.button.PauseButton;

public interface ButtonFactory {

    MenuButton createButton(Class<? extends MenuButton> buttonClass, String name, Vector2 position);

    ExitButton createExitButton(String name, Vector2 position);

    GoBackButton createGoBackButton(String name, Vector2 position);

    MusicButton createMusicButton(String name, Vector2 position);

    PauseButton createPauseButton(String name, Vector2 position);

}
