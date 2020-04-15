package com.mygdx.game.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.components.AnimationComponent;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.ButtonComponent;
import com.mygdx.game.components.CameraComponent;
import com.mygdx.game.components.CoinComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.ObstacleComponent;
import com.mygdx.game.components.PowerUpComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class Mappers {

    public static final ComponentMapper<BodyComponent> BODY = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<CollisionComponent> COLLISION = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<ChickenComponent> CHICKEN = ComponentMapper.getFor(ChickenComponent.class);
    public static final ComponentMapper<StateComponent> STATE = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<TransformComponent> TRANSFORM = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<ButtonComponent> BUTTON = ComponentMapper.getFor(ButtonComponent.class);
    public static final ComponentMapper<ObstacleComponent> OBSTACLE = ComponentMapper.getFor(ObstacleComponent.class);
    public static final ComponentMapper<CoinComponent> COIN = ComponentMapper.getFor(CoinComponent.class);
    public static final ComponentMapper<PowerUpComponent> POWERUP = ComponentMapper.getFor(PowerUpComponent.class);
    public static final ComponentMapper<CameraComponent> CAMERA = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<AnimationComponent> ANIMATION = ComponentMapper.getFor(AnimationComponent.class);
}
