package com.mygdx.game.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.components.ChickenComponent;
import com.mygdx.game.components.CollisionComponent;

public class ChickenContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        //if fa is the chicken fixture
        if(fa.getBody().getUserData() instanceof Entity &&
            ((Entity) fa.getBody().getUserData()).getComponent(ChickenComponent.class) != null){

            Entity ent = (Entity) fa.getBody().getUserData();
            entityCollision(ent,fb);
            return;

        //if fb is the chicken fixtre
        }else if(fb.getBody().getUserData() instanceof Entity &&
                ((Entity) fb.getBody().getUserData()).getComponent(ChickenComponent.class) != null){

            Entity ent = (Entity) fb.getBody().getUserData();
            entityCollision(ent,fa);
            return;
        }
    }

    private void entityCollision(Entity entity, Fixture fb) {
        if(fb.getBody().getUserData() instanceof Entity){

            Entity colEnt = (Entity) fb.getBody().getUserData();
            CollisionComponent col = Mappers.COLLISION.get(entity);

            // set the CollisionEntity of the component
            if(col != null){
                col.collisionEntity = colEnt;

            }
        }
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
