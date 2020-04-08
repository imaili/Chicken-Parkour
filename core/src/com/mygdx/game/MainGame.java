package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.menu.MainMenu;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.utils.Constants;

public class MainGame extends Game {
	private static MainGame singleton;
	AssetManager manager;

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
		manager = new AssetManager();
		loadAssetManager();

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


	private void loadAssetManager(){

		manager.load(Constants.RUN_2_PATH, Texture.class);
		//TODO load textures and animations

		manager.load(Constants.WALK_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.DEAD_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.IDLE_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.JUMP_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.RUN_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.SLIDE_ATLAS_PATH, TextureAtlas.class);

		manager.load(Constants.EXIT_MENU_PATH, Texture.class);
		manager.load(Constants.BACKGROUND_MENU_PATH, Texture.class);
		manager.load(Constants.MULTIPLAYER_BUTTON_PATH, Texture.class);
		manager.load(Constants.SINGLEPLAYER_BUTTON_PATH, Texture.class);

		manager.load(Constants.MUSIC_GAME_PATH, Music.class);
		manager.load(Constants.MUSIC_MENU_PATH, Music.class);


		manager.finishLoading();

	}
}

