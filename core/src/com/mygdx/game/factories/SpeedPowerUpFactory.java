package com.mygdx.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGame;
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PowerUp;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;
import com.mygdx.game.utils.Mappers;

import java.util.function.Consumer;

public class SpeedPowerUpFactory {
    public static Entity create(float x, float _y, PooledEngine engine) {
        Entity entity = engine.createEntity();
        final PowerUpComponent powerUp = engine.createComponent(PowerUpComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        CollisionComponent collision = engine.createComponent(CollisionComponent.class);
        BodyComponent body = engine.createComponent(BodyComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);

        body.body = MainGame.getSingleton().getGame().createTriangle(x, _y);
        body.body.setUserData(entity);
        Pixmap pmap = new Pixmap(32,32, Pixmap.Format.RGBA8888);
        pmap.setColor(Color.GRAY);
        pmap.fillTriangle(32,0,32,32 ,0,16 );
        final int width = pmap.getWidth();
        final int height = pmap.getHeight();
        Pixmap rotatedPmap = new Pixmap(height, width, pmap.getFormat());

        for (int x2 = 0; x2 < height; x2++) {
            for (int y = 0; y < width; y++) {
                rotatedPmap.drawPixel(x2, y, pmap.getPixel(y, x2));
            }
        }
        texture.region = new TextureRegion(new Texture(rotatedPmap));
        rotatedPmap.dispose();
        pmap.dispose();
        Consumer<Entity> action = new Consumer<Entity>() {
            @Override
            public void accept(Entity entity) {
                BodyComponent body = Mappers.BODY.get(entity);
                body.body.setLinearVelocity(8, body.body.getLinearVelocity().y);
                AnimationComponent animation = Mappers.ANIMATION.get(entity);
                animation.animationsMap.get(StateComponent.STATE_WALKING).setFrameDuration(0.07f);

            }
        };
        Consumer<Entity> reset = new Consumer<Entity>() {
            @Override
            public void accept(Entity entity) {
                BodyComponent body = Mappers.BODY.get(entity);
                body.body.setLinearVelocity(5, body.body.getLinearVelocity().y);
                AnimationComponent animation = Mappers.ANIMATION.get(entity);
                animation.animationsMap.get(StateComponent.STATE_WALKING).setFrameDuration(0.1f);

            }
        };
        powerUp.powerUp = new PowerUp(action, reset);
        powerUp.duration = 5;

        entity.add(texture);
        entity.add(transform);
        entity.add(body);
        entity.add(powerUp);
        entity.add(collision);
        return entity;
    }
}
