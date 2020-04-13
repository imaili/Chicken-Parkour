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

public class AnimationSystem extends IteratingSystem {


    public AnimationSystem() {
        super(Family.all(AnimationComponent.class).get());

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent tex = Mappers.TEXTURE.get(entity);
        AnimationComponent anim = Mappers.ANIMATION.get(entity);
        StateComponent state = Mappers.STATE.get(entity);

        Animation<TextureRegion> animation = anim.animationsMap.get(state.get());

        if (animation != null) {
            tex.region = animation.getKeyFrame(state.time);
        }
        state.time += deltaTime;
    }
}
