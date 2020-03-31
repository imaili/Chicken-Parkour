package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.BaseScreen;
import com.mygdx.game.screens.GameScreen;

public class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
