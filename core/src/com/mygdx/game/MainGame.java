package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.BaseScreen;

public abstract class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new BaseScreen(this));
	}
}
