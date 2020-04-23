package com.mygdx.game.utils;

import com.badlogic.ashley.core.Entity;

public class QueuedEntity {
    public final long offset;
    public final String entity;

    public QueuedEntity(long offset, String entity) {
        this.offset = offset;
        this.entity = entity;
    }
}
