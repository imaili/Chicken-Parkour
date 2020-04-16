package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.utils.Mappers;

import java.util.Map;

public class AnimationSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> textureMapper = Mappers.TEXTURE;
    ComponentMapper<AnimationComponent> animationMapper = Mappers.ANIMATION;
    ComponentMapper<StateComponent> stateMapper = Mappers.STATE;
    StateComponent state;
    public AnimationSystem() {
        super(Family.all(AnimationComponent.class,
                         TextureComponent.class,
                         StateComponent.class)
                    .get());

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {

        AnimationComponent animation = animationMapper.get(entity);
        StateComponent state = stateMapper.get(entity);

        if (animation.animationsMap.containsKey(state.get())) {
           TextureComponent texture = textureMapper.get(entity);
           texture.region = (TextureRegion) animation.animationsMap.get(state.get()).getKeyFrame(state.time, true);
        }
        state.time += deltaTime;
    }
}
