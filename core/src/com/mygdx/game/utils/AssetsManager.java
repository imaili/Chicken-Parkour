package com.mygdx.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class AssetsManager {
    //Music Playback
    private Music music_menu = Gdx.audio.newMusic(Gdx.files.internal("sounds/bensound-sunny.mp3"));
    private Music music_game = Gdx.audio.newMusic(Gdx.files.internal("sounds/bensound-scifi.mp3"));
    private float volume_menu = 0.75f;
    private float volume_game = 0.75f;


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


    public static void loadAssets() {

        // Background
        //texturesMap.put(Constants.BACKGROUND_ASSETS_ID,
        //        new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH))));

        // Ground
        //texturesMap.put(Constants.GROUND_ASSETS_ID,
        //        new TextureRegion(new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH))));

        //textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);

        // Chicken walk
        TextureRegion walk1 = new TextureRegion(new Texture("Walk (1).png"));
        TextureRegion walk2 = new TextureRegion(new Texture("Walk (2).png"));
        TextureRegion walk3 = new TextureRegion(new Texture("Walk (3).png"));
        TextureRegion walk4 = new TextureRegion(new Texture("Walk (4).png"));
        TextureRegion walk5 = new TextureRegion(new Texture("Walk (5).png"));
        TextureRegion walk6 = new TextureRegion(new Texture("Walk (6).png"));
        TextureRegion walk7 = new TextureRegion(new Texture("Walk (7).png"));
        TextureRegion walk8 = new TextureRegion(new Texture("Walk (8).png"));
        TextureRegion walk9 = new TextureRegion(new Texture("Walk (9).png"));
        TextureRegion walk10 = new TextureRegion(new Texture("Walk (10).png"));
        Animation walkanimation = new Animation(0.1f, walk1, walk2, walk3, walk4, walk5, walk6, walk7, walk8, walk9, walk10);
        walkanimation.setPlayMode(Animation.PlayMode.LOOP);

        //Chicken run
        TextureRegion run1 = new TextureRegion(new Texture("Run (1).png"));
        TextureRegion run2 = new TextureRegion(new Texture("Run (2).png"));
        TextureRegion run3 = new TextureRegion(new Texture("Run (3).png"));
        TextureRegion run4 = new TextureRegion(new Texture("Run (4).png"));
        TextureRegion run5 = new TextureRegion(new Texture("Run (5).png"));
        TextureRegion run6 = new TextureRegion(new Texture("Run (6).png"));
        TextureRegion run7 = new TextureRegion(new Texture("Run (7).png"));
        TextureRegion run8 = new TextureRegion(new Texture("Run (8).png"));
        Animation runanimation = new Animation(0.1f, run1, run2, run3, run4, run5, run6, run7, run8);
        runanimation.setPlayMode(Animation.PlayMode.LOOP);

        //Chicken jump
        TextureRegion jump1 = new TextureRegion(new Texture("Jump (1).png"));
        TextureRegion jump2 = new TextureRegion(new Texture("Jump (2).png"));
        TextureRegion jump3 = new TextureRegion(new Texture("Jump (3).png"));
        TextureRegion jump4 = new TextureRegion(new Texture("Jump (4).png"));
        TextureRegion jump5 = new TextureRegion(new Texture("Jump (5).png"));
        TextureRegion jump6 = new TextureRegion(new Texture("Jump (6).png"));
        TextureRegion jump7 = new TextureRegion(new Texture("Jump (7).png"));
        TextureRegion jump8 = new TextureRegion(new Texture("Jump (8).png"));
        TextureRegion jump9 = new TextureRegion(new Texture("Jump (9).png"));
        TextureRegion jump10 = new TextureRegion(new Texture("Jump (10).png"));
        TextureRegion jump11 = new TextureRegion(new Texture("Jump (11).png"));
        TextureRegion jump12 = new TextureRegion(new Texture("Jump (12).png"));
        Animation jumpanimation = new Animation(0.1f, jump1, jump2, jump3, jump4, jump5, jump6, jump7, jump8, jump9, jump10, jump11, jump12);
        jumpanimation.setPlayMode(Animation.PlayMode.LOOP);

        //Chicken idle
        TextureRegion idle1 = new TextureRegion(new Texture("Idle (1).png"));
        TextureRegion idle2 = new TextureRegion(new Texture("Idle (2).png"));
        TextureRegion idle3 = new TextureRegion(new Texture("Idle (3).png"));
        TextureRegion idle4 = new TextureRegion(new Texture("Idle (4).png"));
        TextureRegion idle5 = new TextureRegion(new Texture("Idle (5).png"));
        TextureRegion idle6 = new TextureRegion(new Texture("Idle (6).png"));
        TextureRegion idle7 = new TextureRegion(new Texture("Idle (7).png"));
        TextureRegion idle8 = new TextureRegion(new Texture("Idle (8).png"));
        TextureRegion idle9 = new TextureRegion(new Texture("Idle (9).png"));
        TextureRegion idle10 = new TextureRegion(new Texture("Idle (10).png"));
        Animation idleanimation = new Animation(0.1f, idle1, idle2, idle3, idle4, idle5, idle6, idle7, idle8, idle9, idle10);
        idleanimation.setPlayMode(Animation.PlayMode.LOOP);

        //Chicken slide
        TextureRegion slide1 = new TextureRegion(new Texture("Sliding (1).png"));
        TextureRegion slide2 = new TextureRegion(new Texture("Sliding (2).png"));
        TextureRegion slide3 = new TextureRegion(new Texture("Sliding (3).png"));
        TextureRegion slide4 = new TextureRegion(new Texture("Sliding (4).png"));
        TextureRegion slide5 = new TextureRegion(new Texture("Sliding (5).png"));
        TextureRegion slide6 = new TextureRegion(new Texture("Sliding (6).png"));
        TextureRegion slide7 = new TextureRegion(new Texture("Sliding (7).png"));
        TextureRegion slide8 = new TextureRegion(new Texture("Sliding (8).png"));
        TextureRegion slide9 = new TextureRegion(new Texture("Sliding (9).png"));
        TextureRegion slide10 = new TextureRegion(new Texture("Sliding (10).png"));
        TextureRegion slide11 = new TextureRegion(new Texture("Sliding (11).png"));
        TextureRegion slide12 = new TextureRegion(new Texture("Sliding (12).png"));
        TextureRegion slide13 = new TextureRegion(new Texture("Sliding (13).png"));
        Animation slideanimation = new Animation(0.1f, slide1, slide2, slide3, slide4, slide5, slide6, slide7, slide8, slide9, slide10, slide11, slide12, slide13);
        slideanimation.setPlayMode(Animation.PlayMode.LOOP);
    }
}


