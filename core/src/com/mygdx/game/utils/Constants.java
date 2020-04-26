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
    //public static final String MUSIC_GAME_PATH = "sounds/bensound-scifi.mp3";
    public static final String MUSIC_GAME_PATH = "sounds/jungle_music.mp3";

    public static final String PLATFORM_PATH = "box.png";
    public static final String FLOOR_PATH = "floor.png";
    public static final String SPIKE_PATH = "opponent.png";

    public static final String RUN_ATLAS_PATH = "TextureAtlas/Run.atlas";
    public static final String RUN_ANIMATION_ID = "run_animation";
    public static final String RUN_1_ID = "run1";
    static final String RUN_1_PATH = "Run (1).png";
    public static final String RUN_2_ID = "run2";
    public static final String RUN_2_PATH = "Run (2).png";
    public static final String RUN_3_ID = "run3";
    public static final String RUN_3_PATH = "Run (3).png";
    public static final String RUN_4_ID = "run4";
    public static final String RUN_4_PATH = "Run (4).png";
    public static final String RUN_5_ID = "run5";
    public static final String RUN_5_PATH = "Run (5).png";
    public static final String RUN_6_ID = "run6";
    public static final String RUN_6_PATH = "Run (6).png";
    public static final String RUN_7_ID = "run7";
    public static final String RUN_7_PATH = "Run (7).png";
    public static final String RUN_8_ID = "run8";
    public static final String RUN_8_PATH = "Run (8).png";

    public static final String DEAD_ATLAS_PATH = "TextureAtlas/Dead.atlas";
    public static final String DEAD_ANIMATION_ID = "dead_animation";
    public static final String DEAD_1_ID = "dead1";
    public static final String DEAD_1_PATH = "Dead (1).png";
    public static final String DEAD_2_ID = "dead2";
    public static final String DEAD_2_PATH = "Dead (2).png";
    public static final String DEAD_3_ID = "dead3";
    public static final String DEAD_3_PATH = "Dead (3).png";
    public static final String DEAD_4_ID = "dead4";
    public static final String DEAD_4_PATH = "Dead (4).png";
    public static final String DEAD_5_ID = "dead5";
    public static final String DEAD_5_PATH = "Dead (5).png";
    public static final String DEAD_6_ID = "dead6";
    public static final String DEAD_6_PATH = "Dead (6).png";
    public static final String DEAD_7_ID = "dead7";
    public static final String DEAD_7_PATH = "Dead (7).png";
    public static final String DEAD_8_ID = "dead8";
    public static final String DEAD_8_PATH = "Dead (8).png";

    public static final String IDLE_ATLAS_PATH = "TextureAtlas/Idle.atlas";
    public static final String IDLE_ANIMATION_ID = "idle_animation";
    public static final String IDLE_1_ID = "idle1";
    public static final String IDLE_1_PATH = "Idle (1).png";
    public static final String IDLE_2_ID = "idle2";
    public static final String IDLE_2_PATH = "Idle (2).png";
    public static final String IDLE_3_ID = "idle3";
    public static final String IDLE_3_PATH = "Idle (3).png";
    public static final String IDLE_4_ID = "idle4";
    public static final String IDLE_4_PATH = "Idle (4).png";
    public static final String IDLE_5_ID = "idle5";
    public static final String IDLE_5_PATH = "Idle (5).png";
    public static final String IDLE_6_ID = "idle6";
    public static final String IDLE_6_PATH = "Idle (6).png";
    public static final String IDLE_7_ID = "idle7";
    public static final String IDLE_7_PATH = "Idle (7).png";
    public static final String IDLE_8_ID = "idle8";
    public static final String IDLE_8_PATH = "Idle (8).png";
    public static final String IDLE_9_ID = "idle9";
    public static final String IDLE_9_PATH = "Idle (9).png";
    public static final String IDLE_10_ID = "idle10";
    public static final String IDLE_10_PATH = "Idle (10).png";

    public static final String SLIDE_ATLAS_PATH = "TextureAtlas/Slide.atlas";
    public static final String SLIDE_ANIMATION_ID = "sliding_animation";
    public static final String SLIDE_1_ID = "slide1";
    public static final String SLIDE_1_PATH = "Sliding (1).png";
    public static final String SLIDE_2_ID = "slide2";
    public static final String SLIDE_2_PATH = "Sliding (2).png";
    public static final String SLIDE_3_ID = "slide3";
    public static final String SLIDE_3_PATH = "Sliding (3).png";
    public static final String SLIDE_4_ID = "slide4";
    public static final String SLIDE_4_PATH = "Sliding (4).png";
    public static final String SLIDE_5_ID = "slide5";
    public static final String SLIDE_5_PATH = "Sliding (5).png";
    public static final String SLIDE_6_ID = "slide6";
    public static final String SLIDE_6_PATH = "Sliding (6).png";
    public static final String SLIDE_7_ID = "slide7";
    public static final String SLIDE_7_PATH = "Sliding (7).png";
    public static final String SLIDE_8_ID = "slide8";
    public static final String SLIDE_8_PATH = "Sliding (8).png";
    public static final String SLIDE_9_ID = "slide9";
    public static final String SLIDE_9_PATH = "Sliding (9).png";
    public static final String SLIDE_10_ID = "slide10";
    public static final String SLIDE_10_PATH = "Sliding (10).png";
    public static final String SLIDE_11_ID = "slide11";
    public static final String SLIDE_11_PATH = "Sliding (11).png";
    public static final String SLIDE_12_ID = "slide12";
    public static final String SLIDE_12_PATH = "Sliding (12).png";
    public static final String SLIDE_13_ID = "slide13";
    public static final String SLIDE_13_PATH = "Sliding (13).png";

    public static final String JUMP_ATLAS_PATH = "TextureAtlas/Jump.atlas";
    public static final String JUMP_ANIMATION_ID = "jump_animation";
    public static final String JUMP_1_ID = "jump1";
    public static final String JUMP_1_PATH = "Jump (1).png";
    public static final String JUMP_2_ID = "jump2";
    public static final String JUMP_2_PATH = "Jump (2).png";
    public static final String JUMP_3_ID = "jump3";
    public static final String JUMP_3_PATH = "Jump (3).png";
    public static final String JUMP_4_ID = "jump4";
    public static final String JUMP_4_PATH = "Jump (4).png";
    public static final String JUMP_5_ID = "jump5";
    public static final String JUMP_5_PATH = "Jump (5).png";
    public static final String JUMP_6_ID = "jump6";
    public static final String JUMP_6_PATH = "Jump (6).png";
    public static final String JUMP_7_ID = "jump7";
    public static final String JUMP_7_PATH = "Jump (7).png";
    public static final String JUMP_8_ID = "jump8";
    public static final String JUMP_8_PATH = "Jump (8).png";
    public static final String JUMP_9_ID = "jump9";
    public static final String JUMP_9_PATH = "Jump (9).png";
    public static final String JUMP_10_ID = "jump10";
    public static final String JUMP_10_PATH = "Jump (10).png";
    public static final String JUMP_11_ID = "jump11";
    public static final String JUMP_11_PATH = "Jump (11).png";
    public static final String JUMP_12_ID = "jump12";
    public static final String JUMP_12_PATH = "Jump (12).png";

    public static final String WALK_ATLAS_PATH = "TextureAtlas/Walk.atlas";
    public static final String WALK_ANIMATION_ID = "walk_animation";
    public static final String WALK_1_ID = "walking1";
    public static final String WALK_1_PATH = "Walk (1).png";
    public static final String WALK_2_ID = "walk2";
    public static final String WALK_2_PATH = "Walk (2).png";
    public static final String WALK_3_ID = "walk3";
    public static final String WALK_3_PATH = "Walk (3).png";
    public static final String WALK_4_ID = "walk4";
    public static final String WALK_4_PATH = "Walk (4).png";
    public static final String WALK_5_ID = "walk5";
    public static final String WALK_5_PATH = "Walk (5).png";
    public static final String WALK_6_ID = "walk6";
    public static final String WALK_6_PATH = "Walk (6).png";
    public static final String WALK_7_ID = "walk7";
    public static final String WALK_7_PATH = "Walk (7).png";
    public static final String WALK_8_ID = "walk8";
    public static final String WALK_8_PATH = "Walk (8).png";
    public static final String WALK_9_ID = "walk9";
    public static final String WALK_9_PATH = "Walk (9).png";
    public static final String WALK_10_ID = "walk10";
    public static final String WALK_10_PATH = "Walk (10).png";

    public static final String BACKGROUND_MENU_ID = "background_menu";
    public static final String BACKGROUND_TUTORIAL_PATH="Tutorial.png";
    public static final String BACKGROUND_MENU_EMPTY_PATH="background_empty.jpg";
    public static final String BACKGROUND_MENU_PATH = "background1.jpg";
    public static final String BACKGROUND_GAME_OVER_PATH = "background_gameover.png";
    public static final String PAUSE_MENU_PATH = "pause.png";
    public static final String EXIT_MENU_ID = "exit_menu";
    public static final String EXIT_MENU_PATH = "exit1.png";
    public static final String MULTIPLAYER_BUTTON_ID = "multiplayer_button";
    public static final String MULTIPLAYER_BUTTON_PATH = "multiplayerButton.PNG";
    public static final String SINGLEPLAYER_BUTTON_ID = "singleplayer_button";
    public static final String SINGLEPLAYER_BUTTON_PATH = "SinglePlayerButton.PNG";

    public static final String GAME_BACKGROUND_ANIMATION_ID = "game_background_animation";
    public static final String GAME_BACKGROUND_1_ID = "game_background_1";
    public static final String GAME_BACKGROUND_1_PATH = "background/plx-1.png";
    public static final String GAME_BACKGROUND_2_ID = "game_background_2";
    public static final String GAME_BACKGROUND_2_PATH = "background/plx-2.png";
    public static final String GAME_BACKGROUND_3_ID = "game_background_3";
    public static final String GAME_BACKGROUND_3_PATH = "background/plx-3.png";
    public static final String GAME_BACKGROUND_4_ID = "game_background_4";
    public static final String GAME_BACKGROUND_4_PATH = "background/plx-4.png";
    public static final String GAME_BACKGROUND_5_ID = "game_background_5";
    public static final String GAME_BACKGROUND_5_PATH = "background/plx-5.png";

    public static final String SKIN_ATLAS = "skin/uiskin.atlas";
    public static final String SKIN_PATH = "skin/uiskin.json";

    public static final String SPEED_UP_BONE_PATH = "powerups/bone.png";
    public static final String SPEED_UP_LEAF_PATH = "powerups/leaf.png";


    //public static final String = "key" running (1)
}
