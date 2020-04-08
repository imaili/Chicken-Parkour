package com.mygdx.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;


public class AssetsManager {
    //Music Playback
    private Music music_menu = Gdx.audio.newMusic(Gdx.files.internal("sounds/bensound-sunny.mp3"));
    private Music music_game = Gdx.audio.newMusic(Gdx.files.internal("sounds/bensound-scifi.mp3"));
    private float volume_menu = 0.75f;
    private float volume_game = 0.75f;
    private static HashMap<String, TextureRegion> texturesMap = new HashMap<>();
    private static HashMap<String, Animation> animationsMap = new HashMap<>();
    private static TextureAtlas textureAtlas;


    public void play_music(String music_type)  // "game" or "menu" as music type
    {
        music_game.setLooping(true);
        music_menu.setLooping(true);
        if (music_type.equals("menu")){
            if (music_game.isPlaying())
                music_game.pause();
            music_menu.setVolume(volume_menu);
            music_menu.play();
        }
        else if (music_type.equals("game"))
        {
            if (music_menu.isPlaying())
                music_menu.pause();
            music_game.setVolume(volume_game);
            music_game.play();
        }
    }

    public void stop_music(){
        music_menu.stop();
        music_game.stop();
    }

    public void pause_music() {
        music_game.pause();
        music_menu.pause();
    }

    public void change_volume_menu(float volume_change) {
        volume_menu = music_menu.getVolume() + volume_change;
        music_menu.setVolume(volume_menu);
    }

    public void change_volume_game(float volume_change){
        volume_game = music_game.getVolume() + volume_change;
        music_game.setVolume(volume_game);
    }

    //Animation


    public static void loadAssets() {

        texturesMap.put(Constants.BACKGROUND_MENU_ID, new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_MENU_PATH))));
        texturesMap.put(Constants.EXIT_MENU_ID, new TextureRegion(new Texture(Gdx.files.internal(Constants.EXIT_MENU_PATH))));
        texturesMap.put(Constants.MULTIPLAYER_BUTTON_ID, new TextureRegion(new Texture(Gdx.files.internal(Constants.MULTIPLAYER_BUTTON_PATH))));
        texturesMap.put(Constants.SINGLEPLAYER_BUTTON_ID, new TextureRegion(new Texture(Gdx.files.internal(Constants.SINGLEPLAYER_BUTTON_PATH))));

        ArrayList <TextureRegion> walk_frames = new ArrayList<>();
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_1_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_2_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_3_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_4_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_5_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_6_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_7_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_8_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_9_PATH))));
        walk_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.WALK_10_PATH))));

        animationsMap.put(Constants.WALK_ANIMATION_ID, new Animation<>(0.1f, walk_frames));

        ArrayList <TextureRegion> sliding_frames = new ArrayList<>();
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_1_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_2_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_3_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_4_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_5_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_6_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_7_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_8_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_9_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_10_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_11_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_12_PATH))));
        sliding_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.SLIDING_13_PATH))));

        animationsMap.put(Constants.SLIDING_ANIMATION_ID, new Animation<>(0.1f, sliding_frames));

        ArrayList<TextureRegion> dead_frames = new ArrayList<>();
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_1_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_2_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_3_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_4_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_5_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_6_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_7_PATH))));
        dead_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.DEAD_8_PATH))));
        animationsMap.put(Constants.DEAD_ANIMATION_ID, new Animation<>(0.1f, dead_frames));

        ArrayList<TextureRegion> idle_frames = new ArrayList<>();
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_1_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_2_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_3_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_4_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_5_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_6_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_7_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_8_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_9_PATH))));
        idle_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.IDLE_10_PATH))));
        animationsMap.put(Constants.IDLE_ANIMATION_ID, new Animation<>(0.1f, idle_frames));

        ArrayList<TextureRegion> jump_frames = new ArrayList<>();
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_1_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_2_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_3_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_4_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_5_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_6_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_7_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_8_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_9_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_10_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_11_PATH))));
        jump_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.JUMP_12_PATH))));
        animationsMap.put(Constants.JUMP_ANIMATION_ID, new Animation<>(0.1f, jump_frames));

        ArrayList<TextureRegion> run_frames = new ArrayList<>();
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_1_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_2_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_3_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_4_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_5_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_6_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_7_PATH))));
        run_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.RUN_8_PATH))));
        animationsMap.put(Constants.RUN_ANIMATION_ID, new Animation<>(0.1f, run_frames));

       /* ArrayList <TextureRegion> game_background_frames = new ArrayList<>();
        game_background_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.GAME_BACKGROUND_1_PATH))));
        game_background_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.GAME_BACKGROUND_2_PATH))));
        game_background_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.GAME_BACKGROUND_3_PATH))));
        game_background_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.GAME_BACKGROUND_4_PATH))));
        game_background_frames.add(new TextureRegion(new Texture(Gdx.files.internal(Constants.GAME_BACKGROUND_5_PATH))));
        animationsMap.put(Constants.GAME_BACKGROUND_ANIMATION_ID, new Animation<>(0.1f, game_background_frames));
        */

    }

    public static TextureRegion getTextureRegion(String key) {
        return texturesMap.get(key);
    }

    public static Animation getAnimation(String key) {
        return animationsMap.get(key);
    }

    public static void dispose() {
        textureAtlas.dispose();
        texturesMap.clear();
        animationsMap.clear();
    }


}


