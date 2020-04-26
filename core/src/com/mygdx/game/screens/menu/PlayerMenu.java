package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayerMenu extends MenuScreen {

    @Override
    protected List<MenuButton> createButtons() {

      //  int buttonX = Gdx.graphics.getWidth() /2 + Gdx.graphics.getWidth() / 10;
        //  int nButtons = 2;
       // int buttonHeight = Gdx.graphics.getHeight() / 10;
       // int buttonOffset = (Gdx.graphics.getHeight() - buttonHeight * nButtons) / (nButtons + 1);
        int buttonX = Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        List<MenuButton> list = new LinkedList<>();
        MenuButton goToTutorial = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("How to play", new Vector2(buttonX, Gdx.graphics.getHeight() - 3*buttonHeight), Tutorial.class);
        MenuButton goToSinglePlayerMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Single Player", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - buttonHeight/2), SinglePlayerMenu.class);
        MenuButton goToMultiPlayerMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("MultiPlayer", new Vector2(buttonX, Gdx.graphics.getHeight()/2 - 2*buttonHeight), MultiPlayerTypeMenu.class);
        MenuButton goBack = IMAGE_BUTTON_FACTORY.createGoBackButton();
        Collections.addAll(list,goToTutorial,goToMultiPlayerMenu, goToSinglePlayerMenu, goBack);
        return list;
    }

    public PlayerMenu(Menu previousMenu) {
        super(previousMenu);
        backGroundTexture = BACK_GROUND_TEXTURE_EMPTY ;
    }


    public void goToPlayerMenu() {
        goTo(new PlayerMenu(previousMenu));
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(PlayerMenu.class))
            goToPlayerMenu();
        else if (menu.equals(SinglePlayerMenu.class))
            goToSinglePlayerMenu();
        else if (menu.equals(MultiPlayerTypeMenu.class))
            goToMultiPlayerMenu();
        else if (menu.equals(Tutorial.class))
            goToTutorial();

    }

 public void goToSinglePlayerMenu() { goTo(new SinglePlayerMenu(this)); }
 public void goToMultiPlayerMenu() { goTo(new MultiPlayerTypeMenu(this)); }
 public void goToTutorial() { goTo(new Tutorial(this)); }

}