package com.mygdx.game.components;


import com.badlogic.ashley.core.Entity;

import java.util.function.Consumer;


public class PowerUp {

    private Consumer<Entity> action;
    private Consumer<Entity> reset;

    public PowerUp(Consumer<Entity> action, Consumer<Entity> reset){
        this.action = action;
        this.reset = reset;
    }

    public void act(Entity entity){
        action.accept(entity);
    }

    public void reset(Entity entity){
        reset.accept(entity);
    }

}
