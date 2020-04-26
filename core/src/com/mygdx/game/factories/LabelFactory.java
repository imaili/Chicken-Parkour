package com.mygdx.game.factories;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MainGame;
import com.mygdx.game.utils.Constants;

public class LabelFactory {
    private static Skin skin = MainGame.getSingleton().getAssetManager().get(Constants.SKIN_PATH);

    public static Label create(String text) {
        Label title = new Label(text, skin);
        return title;
    }
}
