package com.schurke.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private static float size = 20f;


    public Player(Vector2 startPosition){
        this.position = new Vector2(startPosition);
    }
    public void render(ShapeRenderer shape){
        shape.circle(position.x, position.y, size/2f);
    }

    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(float x, float y){
        this.position.set(x,y);
    }
    public float getSize(){
        return size;
    }

}
