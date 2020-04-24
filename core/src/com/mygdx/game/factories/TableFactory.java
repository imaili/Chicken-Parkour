package com.mygdx.game.factories;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MainGame;
import com.mygdx.game.utils.Constants;

public class TableFactory {
    private static Skin skin = MainGame.getSingleton().getAssetManager().get(Constants.TABLE_SKIN);

    public static Table create() {
        return new Table(skin);
    }
}
