package com.mygdx.game.screens.menu.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainGame;

public class MusicButton extends MenuButton {
    private static final String START_MUSIC_BUTTON_NAME = "START MUSIC";
    private static final String STOP_MUSIC_BUTTON_NAME = "STOP MUSIC";

    private Action action;

    public MusicButton(Skin skin, Vector2 position, Action action) {
        super(new TextButton(getName(action), skin), position);
        this.action = action;
    }

    public MusicButton(Skin skin, Vector2 position) {
        this(skin, position, getAction());
    }

    public MusicButton(Texture texture, Vector2 position, int width, int height) {
        super(texture, position, width, height);
        this.action = getAction();
    }

    public void setAction(Action action) {
        this.action = action;
    }

    private static String getName(Action action) {
        if (action.equals(Action.START_MUSIC))
            return START_MUSIC_BUTTON_NAME;
        else
            return STOP_MUSIC_BUTTON_NAME;
    }

    private static Action getAction() {
        if (MainGame.getSingleton().getMusic())
            return Action.STOP_MUSIC;
        else
            return Action.START_MUSIC;
    }

    @Override
    public void action() {
        if (action.equals(Action.START_MUSIC)) {
            MainGame.getSingleton().setMusic(true);
            getMenu().startMusic();
        } else if (action.equals(Action.STOP_MUSIC)) {
            MainGame.getSingleton().setMusic(false);
            getMenu().stopMusic();
        }
        getMenu().goTo(getMenu().getClass());
    }

    public enum Action{START_MUSIC, STOP_MUSIC}
}
