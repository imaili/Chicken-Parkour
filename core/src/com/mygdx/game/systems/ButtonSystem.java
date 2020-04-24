package com.mygdx.game.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.ButtonComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;


public class ButtonSystem extends IteratingSystem {

    private Array<Entity> renderQueue;
    private Stage stage;

    private ComponentMapper<ButtonComponent> buttonM;

    public ButtonSystem() {
        super(Family.all(ButtonComponent.class).get());

        buttonM = Mappers.BUTTON;

        renderQueue = new Array<>();

        stage = new Stage();
        InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        if (!multiplexer.getProcessors().contains(stage, true))
            multiplexer.addProcessor(stage);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //stage.clear();

        for (Entity entity : renderQueue) {
            ButtonComponent b = buttonM.get(entity);

            stage.addActor(b.button);

        }

        stage.act();

        renderQueue.clear();

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

}