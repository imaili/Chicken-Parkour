package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Font {

    private static final int DEFAULT_SIZE = 64;

    public static BitmapFont createFont(int size) {
        BitmapFont font = new BitmapFont(Gdx.files.internal(Constants.MY_FONT_PATH));
        font.getData().setScale(size / (float) DEFAULT_SIZE);
        return font;
    }

    public static Skin createLabelSkin(int size) {
        BitmapFont font = createFont(size);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        Skin skin = new Skin();
        skin.add("default", style);
        return skin;
    }


}
