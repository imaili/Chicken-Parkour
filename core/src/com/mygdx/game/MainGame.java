package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.menu.MainMenu;
import com.mygdx.game.screens.Menu;

public class MainGame extends Game {
	private static MainGame singleton;

	public static MainGame getSingleton() {
		return singleton;
	}

	private Menu menu;

	@Override
	public void create() {
		singleton = this;
		//setMenu(new GameScreen(this));
		MainMenu menu = new MainMenu();
		setMenu(menu);
		Gdx.input.setInputProcessor(new InputMultiplexer());
		menu.setInputProcessor();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		setScreen(menu);
	}

	@Override
	public void render() {
		super.render();
	}
}
