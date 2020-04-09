package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.menu.GameOverMenu;
import com.mygdx.game.screens.menu.button.ExitButton;
import com.mygdx.game.screens.menu.button.GoToButton;
import com.mygdx.game.screens.menu.button.MenuButton;

import java.util.List;

import static com.mygdx.game.screens.MenuScreen.createBasicSkin;


public class GameOverScreen extends BaseScreen  implements Menu {

    private final Stage stage;
    protected Texture backGroundTexture;
    private MainGame game;
    private GameOverMenu gameOver;
    SpriteBatch batch;

    protected static final Texture GAME_OVER_BACK_GROUND_TEXTURE = new Texture("background_gameover.png");
    protected static final String MUSIC_TYPE = "menu";

    public GameOverScreen(MainGame game) {
        super(game);
        this.game = game;
        this.gameOver = new GameOverMenu();
        this.stage = new Stage();
    }


    @Override
    public void goTo(Class<? extends Menu> menu) {

    }



    @Override
    public void goBack() {

    }

    public void startMusic() {
        if (game.getMusic())
            game.getAssetsManager().play_music(MUSIC_TYPE);
    }

    public void stopMusic() {
        game.getAssetsManager().stop_music();
    }




    protected void draw() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         /*   stage.getBatch().begin();
            stage.getBatch().draw(backGroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
      //  }
       stage.draw();*/

         batch.begin();
         batch.draw(backGroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
         batch.end();
    }

    @Override
    public void render(float delta) {
        // draw
        draw();
        // update
    //    stage.act(delta);
    }

    @Override
    public void dispose() {
        //stage.dispose();
        if (backGroundTexture != GAME_OVER_BACK_GROUND_TEXTURE )
            backGroundTexture.dispose();
    }





}
