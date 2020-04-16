package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainGame;
import com.mygdx.game.screens.menu.button.MenuButton;
import com.mygdx.game.screens.menu.button.factory.ImageButtonFactory;
import com.mygdx.game.screens.menu.button.factory.TextButtonFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.utils.Constants.BACKGROUND_MENU_EMPTY_PATH;
import static com.mygdx.game.utils.Constants.BACKGROUND_MENU_PATH;
import static com.mygdx.game.utils.Constants.BACKGROUND_TUTORIAL_PATH;

public abstract class MenuScreen extends BaseScreen implements Menu {
    protected final Menu previousMenu;
    private final Stage stage;
    private final List<MenuButton> buttons;
    private final List<Actor> actors;
    protected static final Texture DEFAULT_BACK_GROUND_TEXTURE = new Texture(BACKGROUND_MENU_PATH);
    protected static final Texture BACK_GROUND_TEXTURE_EMPTY = new Texture(BACKGROUND_MENU_EMPTY_PATH);
    protected static final Texture BACK_GROUND_TUTORIAL  = new Texture(BACKGROUND_TUTORIAL_PATH);

    protected static final String MUSIC_TYPE = "menu";
    protected static final ImageButtonFactory IMAGE_BUTTON_FACTORY = ImageButtonFactory.getInstance();
    protected static final TextButtonFactory DEFAULT_TEXT_BUTTON_FACTORY = TextButtonFactory.getDefaultInstance();
    protected Texture backGroundTexture;

    protected MenuScreen(Menu previousMenu){
        this.previousMenu = previousMenu;
        this.stage = new Stage();
        this.buttons = createButtons();
        this.actors = getActors();
        
        if (actors != null) {
            for (Actor actor :
                    actors) {
                stage.addActor(actor);
            }
        }

        for (MenuButton button : buttons) {
            button.setMenu(this);
            stage.addActor(button.getButton());
        }
    }

    protected Stage getStage() {
        return stage;
    }

    protected abstract List<MenuButton> createButtons();

    protected List<Actor> getActors() {
        return null;
    }

    protected MenuScreen() {
        this(null);
    }

    protected void goTo(Menu menu) {
        if (menu != null) {
            MainGame.getSingleton().setMenu(menu);
            removeInputProcessor();
            if (menu instanceof MenuScreen)
                ((MenuScreen) menu).setInputProcessor();
        }
    }

    @Override
    public void goBack() {
        goTo(previousMenu);
    }

    public void setInputProcessor() {
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        //for (MenuButton button : buttons)
            //button.setMenu(this);
        if (!multiplexer.getProcessors().contains(stage, true))
            multiplexer.addProcessor(stage);
    }

    public  void removeInputProcessor() {
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        multiplexer.removeProcessor(stage);
    }

    public void startMusic() {

    }

    public void stopMusic() { }

    public static Skin createBasicSkin() {
        // Create a font
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        // Create a texture
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        // Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

    // Override from BaseScreen

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (backGroundTexture != null) {
            stage.getBatch().begin();
            stage.getBatch().draw(backGroundTexture,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage.getBatch().end();
        }

        stage.draw();
    }

    @Override
    public void render(float delta) {
        // draw
        draw();
        // update
        stage.act(delta);
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (backGroundTexture != DEFAULT_BACK_GROUND_TEXTURE)
            backGroundTexture.dispose();
    }
}
