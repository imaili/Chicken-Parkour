package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final String SERVER_HOST = "http://192.168.43.227:8300";

    public static final float PIXELS_PER_METER = 32.0f;
    public static final float WORLD_WIDTH = Gdx.graphics.getWidth()/PIXELS_PER_METER;
    public static final float WORLD_HEIGHT = Gdx.graphics.getHeight()/PIXELS_PER_METER;
    public static final float PIXELS_TO_METRES = 1.0f / PIXELS_PER_METER;

    public static final String MUSIC_MENU_PATH = "sounds/bensound-sunny.mp3";
    public static final String MUSIC_GAME_PATH = "sounds/jungle_music.mp3";

    public static final String BACK_BUTTON = "textures/buttons/back.png";

    public static final String GAME_BACKGROUND_1_PATH = "backgrounds/game/layer-1.png";
    public static final String GAME_BACKGROUND_2_PATH = "backgrounds/game/layer-2.png";
    public static final String GAME_BACKGROUND_3_PATH = "backgrounds/game/layer-3.png";
    public static final String GAME_BACKGROUND_4_PATH = "backgrounds/game/layer-4.png";
    public static final String GAME_BACKGROUND_5_PATH = "backgrounds/game/layer-5.png";
    public static final String GAME_OVER_BACKGROUND_PATH = "backgrounds/menu/game_over.png";
    public static final String TUTORIAL_BACKGROUND_PATH ="backgrounds/menu/tutorial.png";
    public static final String MAIN_MENU_BACKGROUND_PATH ="backgrounds/menu/main_menu.jpg";
    public static final String OTHER_MENU_BACKGROUND_PATH = "backgrounds/menu/other_menu.jpg";
    public static final String EXIT_MENU_BACKGROUND_PATH = "backgrounds/menu/exit.png";
    public static final String PAUSE_MENU_BACKGROUND_PATH = "backgrounds/menu/pause.png";

    public static final String SKIN_ATLAS = "skin/uiskin.atlas";
    public static final String SKIN_PATH = "skin/uiskin.json";

    public static final String SPEED_UP_BONE_PATH = "textures/powerups/bone.png";
    public static final String SPEED_UP_LEAF_PATH = "textures/powerups/leaf.png";
    public static final String PLATFORM_PATH = "textures/obstacles/box.png";
    public static final String FLOOR_PATH = "textures/obstacles/floor.png";
    public static final String SPIKE_PATH = "textures/obstacles/opponent.png";
    public static final String DEAD_ATLAS_PATH = "textures/player/dead.atlas";
    public static final String JUMP_ATLAS_PATH = "textures/player/jump.atlas";
    public static final String WALK_ATLAS_PATH = "textures/player/walk.atlas";
}
