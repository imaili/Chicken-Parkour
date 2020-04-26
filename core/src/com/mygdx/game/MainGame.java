package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.menu.MainMenu;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.utils.Constants;

public class MainGame extends Game {
	private static MainGame singleton;
	public static MainGame getSingleton() {
		return singleton;
	}

	private GameScreen game;
	private AssetManager manager;


	private Menu menu;
	private boolean music;


	@Override
	public void create() {
		singleton = this;
		manager = new AssetManager();
		loadAssetManager();
		MainMenu menu = new MainMenu();
		setMenu(menu);
		Gdx.input.setInputProcessor(new InputMultiplexer());
		menu.setInputProcessor();
		music = true;
		menu.startMusic();
		//setScreen(new GameScreen(this));

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

	public void setMusic(boolean value) {
		music = value;
	}

	public boolean getMusic() {
		return music;
	}

	@Override
	public void render() {
		super.render();
	}


	private void loadAssetManager(){
		manager.load(Constants.PLATFORM_PATH, Texture.class);
		manager.load(Constants.FLOOR_PATH, Texture.class);
		manager.load(Constants.SPIKE_PATH, Texture.class);
		manager.load(Constants.BACK_BUTTON, Texture.class);

		manager.load(Constants.WALK_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.SKIN_PATH, Skin.class, new SkinLoader.SkinParameter(Constants.SKIN_ATLAS));
		manager.load(Constants.DEAD_ATLAS_PATH, TextureAtlas.class);
		manager.load(Constants.JUMP_ATLAS_PATH, TextureAtlas.class);

		manager.load(Constants.EXIT_MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.PAUSE_MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.MAIN_MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.OTHER_MENU_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.TUTORIAL_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.GAME_OVER_BACKGROUND_PATH, Texture.class);
		manager.load(Constants.GAME_BACKGROUND_1_PATH, Texture.class);
		manager.load(Constants.GAME_BACKGROUND_2_PATH, Texture.class);
		manager.load(Constants.GAME_BACKGROUND_3_PATH, Texture.class);
		manager.load(Constants.GAME_BACKGROUND_4_PATH, Texture.class);
		manager.load(Constants.GAME_BACKGROUND_5_PATH, Texture.class);
		manager.load(Constants.MUSIC_GAME_PATH, Music.class);
		manager.load(Constants.MUSIC_MENU_PATH, Music.class);

		manager.load(Constants.SPEED_UP_BONE_PATH, Texture.class);
		manager.load(Constants.SPEED_UP_LEAF_PATH, Texture.class);
		manager.load(Constants.FLOOR_PATH, Texture.class);
		manager.finishLoading();

	}

	public AssetManager getAssetManager() {
		return manager;
	}
}

