package com.mygdx.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AssetsManager {
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
}

