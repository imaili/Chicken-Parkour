package com.mygdx.game.factories;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.MainGame;
import com.mygdx.game.utils.Constants;

public class TextFieldFactory {
    private static Skin skin = MainGame.getSingleton().getAssetManager().get(Constants.SKIN);

    public static TextField create(String text) {
        return new TextField(text, skin);
    }
}
