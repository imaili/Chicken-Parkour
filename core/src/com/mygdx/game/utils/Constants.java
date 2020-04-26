package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final String SERVER_HOST = "http://192.168.43.227:8300";
    public static final float PIXELS_PER_METER = 32.0f;

    public static final float WORLD_WIDTH = Gdx.graphics.getWidth()/PIXELS_PER_METER;
    public static final float WORLD_HEIGHT = Gdx.graphics.getHeight()/PIXELS_PER_METER;

    public static final float PIXELS_TO_METRES = 1.0f / 32.0f;
    public static final int SCALE_TO_PHONE_X = 3;
    public static final int SCALE_TO_PHONE_Y = 4;

    public static final float GROUND_Y = 1.5f;


    public static final String MUSIC_MENU_PATH = "sounds/bensound-sunny.mp3";
    public static final String MUSIC_GAME_PATH = "sounds/jungle_music.mp3";

    public static final String PLATFORM_PATH = "box.png";
    public static final String FLOOR_PATH = "floor.png";
    public static final String SPIKE_PATH = "opponent.png";

    public static final String DEAD_ATLAS_PATH = "TextureAtlas/Dead.atlas";
    public static final String JUMP_ATLAS_PATH = "TextureAtlas/Jump.atlas";
    public static final String WALK_ATLAS_PATH = "TextureAtlas/Walk.atlas";
    public static final String SLIDE_ATLAS_PATH = "TextureAtlas/Slide.atlas";
    public static final String DINOSAUR_TEXTURE_PATH = "Walk (1).png";

    public static final String BACKGROUND_TUTORIAL_PATH="Tutorial.png";
    public static final String BACKGROUND_MENU_EMPTY_PATH="menu_backgrounds/background_empty.jpg";
    public static final String BACKGROUND_MENU_PATH = "menu_backgrounds/background1.jpg";
    public static final String BACKGROUND_GAME_OVER_PATH = "menu_backgrounds/background_gameover.png";
    public static final String PAUSE_MENU_PATH = "menu_backgrounds/pause.png";
    public static final String EXIT_MENU_EMPTY_PATH = "menu_backgrounds/exit1.png";
    public static final String EXIT_MENU_TEXT_PATH = "menu_backgrounds/exitgame.png";


    public static final String GAME_BACKGROUND_1_PATH = "game_backgrounds/plx-1.png";
    public static final String GAME_BACKGROUND_2_PATH = "game_backgrounds/plx-2.png";
    public static final String GAME_BACKGROUND_3_PATH = "game_backgrounds/plx-3.png";
    public static final String GAME_BACKGROUND_4_PATH = "game_backgrounds/plx-4.png";
    public static final String GAME_BACKGROUND_5_PATH = "game_backgrounds/plx-5.png";

    public static final String SKIN_ATLAS = "skin/uiskin.atlas";
    public static final String SKIN_PATH = "skin/uiskin.json";

    public static final String SPEED_UP_BONE_PATH = "powerups/bone.png";
    public static final String SPEED_UP_LEAF_PATH = "powerups/leaf.png";

}
