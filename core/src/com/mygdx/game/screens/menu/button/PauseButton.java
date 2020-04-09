package com.mygdx.game.screens.menu.button;

import com.mygdx.game.screens.Menu;

public class PauseButton extends GoBackButton {

    public PauseButton(Menu menu) {
        super();
        setMenu(menu);
    }

    @Override
    public void action() {
        getMenu().pause();
    }

}
