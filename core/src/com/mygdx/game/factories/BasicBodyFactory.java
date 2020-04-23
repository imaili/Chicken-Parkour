package com.mygdx.game.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BasicBodyFactory extends BodyFactory{


    public BasicBodyFactory(World world){
        super(world);
    }

    public Body createRectangle(float x, float y, float width, float height, boolean dynamic) {
        BodyDef bodyDef = new BodyDef();
        if(dynamic){
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }else{
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.x = x;
        bodyDef.position.y = y;
        bodyDef.fixedRotation = true;

        //create the body to attach said definition
        Body boxBody = world.createBody(bodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
        poly.dispose();

        return boxBody;

    }


    public Body createTriangle(float x, float y, boolean floating) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = x;
        bodyDef.position.y = y;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.StaticBody;



        Body body = world.createBody(bodyDef);
        //create the body to attach said definition
        Vector2[] vertices = {new Vector2(-0.5f, -0.5f), new Vector2(0.5f, -0.5f), new Vector2(0,0.5f)};
        PolygonShape poly = new PolygonShape();
        poly.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.friction = 0;
        if(floating) {
            bodyDef.gravityScale = 0.0f;
            fixtureDef.isSensor = true;
        }

        body.createFixture(fixtureDef);
        poly.dispose();
        return body;
    }


}

