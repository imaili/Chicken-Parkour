package com.mygdx.game.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.Menu;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainMenu extends MenuScreen {

    protected List<MenuButton> createButtons() {
        int buttonX = Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() / 8;
        int nButtons = 4;
        int buttonHeight = Gdx.graphics.getHeight() / 10;
        int buttonOffset = (Gdx.graphics.getHeight() - buttonHeight * nButtons) / (nButtons + 1);
        List<MenuButton> list = new LinkedList<>();
      //  MenuButton goToSinglePlayerMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Single Player", new Vector2(buttonX, buttonOffset + 4*(buttonHeight+buttonOffset)), SinglePlayerMenu.class);
     //   MenuButton goToMultiPlayerMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("MultiPlayer", new Vector2(buttonX, buttonOffset + 3*(buttonHeight+buttonOffset)), MultiPlayerMenu.class);
        MenuButton goToHighscores = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Highscores", new Vector2(buttonX, buttonOffset + 2* (buttonHeight+buttonOffset)), HighscoresMenu.class);
        MenuButton goToPlayerMenu = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Play", new Vector2(buttonX, buttonOffset + 3*(buttonHeight+buttonOffset)), PlayerMenu.class);
        MenuButton goToSettings = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Settings", new Vector2(buttonX, buttonOffset + (buttonHeight+buttonOffset)), Settings.class);
        MenuButton exitGame = DEFAULT_TEXT_BUTTON_FACTORY.createGoToButton("Exit", new Vector2(buttonX, buttonOffset), ExitMenu.class);
      //  Collections.addAll(list, goToSinglePlayerMenu, goToMultiPlayerMenu, goToSettings, goToHighscores, exitGame);
       Collections.addAll(list,goToPlayerMenu , goToSettings, goToHighscores, exitGame);
        return list;
    }

    public MainMenu() {
        super();
        backGroundTexture = DEFAULT_BACK_GROUND_TEXTURE;
    }

    @Override
    public void goTo(Class<? extends Menu> menu) {
        if (menu.equals(Settings.class))
            goToSettings();
        //else if (menu.equals(SinglePlayerMenu.class))
        //    goToSinglePlayerMenu();
        //else if (menu.equals(MultiPlayerMenu.class))
       //     goToMultiPlayerMenu();
        else if (menu.equals(PlayerMenu.class))
            goToPlayerMenu();
        else if (menu.equals(ExitMenu.class))
            goToExitMenu();
        else if (menu.equals(HighscoresMenu.class))
            goToHighscores();
    }

    public void goToPlayerMenu() {
        goTo(new PlayerMenu(this));
    }

    public void goToSettings() {
        goTo(new Settings(this));
    }

    public void goToHighscores() {
        goTo(new HighscoresMenu(this));
    }

   // public void goToSinglePlayerMenu() {
  //      goTo(new SinglePlayerMenu(this));
   // }

 //   public void goToMultiPlayerMenu() {
     //   goTo(new MultiPlayerMenu(this));
  //  }

    public void goToExitMenu() {
        goTo(new ExitMenu(this));
    }
}
