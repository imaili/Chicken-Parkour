package com.mygdx.game.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.components.BodyComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.StateComponent;
import com.mygdx.game.components.TextureComponent;
import com.mygdx.game.components.TransformComponent;

public class Mappers {

    public static final ComponentMapper<BodyComponent> BODY = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<CollisionComponent> COLLISSION = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<ChickenComponent> PLAYER = ComponentMapper.getFor(ChickenComponent.class);
    public static final ComponentMapper<StateComponent> STATE = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<TransformComponent> TRANSFORM = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
}
