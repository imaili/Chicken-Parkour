package com.mygdx.game.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.AssetsManager;
import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.Mappers;


public class RenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;

    public RenderingSystem(SpriteBatch batch, float position) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        textureM = Mappers.TEXTURE;
        transformM = Mappers.TRANSFORM;

        renderQueue = new Array<>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformM.get(entityB).position.z -
                        transformM.get(entityA).position.z);
            }
        };

        this.batch = batch;
        cam = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        cam.position.set(9, Constants.WORLD_HEIGHT / 2, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = tex.region.getRegionWidth()*t.scale.x*Constants.PIXELS_TO_METRES;
            float height = tex.region.getRegionHeight()*t.scale.y*Constants.PIXELS_TO_METRES;
            float originX = width * 0.5f;
            float originY = height * 0.5f;


            batch.draw(tex.region, t.position.x-originX, t.position.y-originY, width, height);


        }

        batch.end();
        renderQueue.clear();

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}