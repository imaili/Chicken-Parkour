package com.mygdx.game.utils;

import com.badlogic.ashley.core.Entity;

public class QueuedEntity {
    public final long offset;
    public final int entity;

    public QueuedEntity(long offset, int entity) {
        this.offset = offset;
        this.entity = entity;
    }
}
