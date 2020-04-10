package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.GoBackButton;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.menu.button.MusicButton;

import java.util.LinkedList;
import java.util.List;

public class Settings extends MenuScreen {

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton musicOption = DEFAULT_TEXT_BUTTON_FACTORY.createMusicButton(new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2));
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        list.add(musicOption);
        list.add(goBack);
        return list;
    }

    public Settings(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(Settings.class))
            goToSettings();
    }

    public void goToSettings() {
        goTo(new Settings(previousMenu));
    }
}
