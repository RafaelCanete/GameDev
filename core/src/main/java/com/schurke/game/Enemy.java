package com.schurke.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private Vector2 position;
    private static float size = 20f;

    public Enemy(Vector2 position){
        this.position = new Vector2(position);
    }

    public void render(ShapeRenderer shape){
        shape.setColor(1,0,0,1);
        shape.circle(position.x,position.y,size/2f);
        shape.setColor(1,1,1,1);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void update(Vector2 playerPosition) {
        float speed = 100f;
        Vector2 direction = new Vector2(playerPosition).sub(position);
        if (direction.len() > 1f) {
            direction.nor();
            direction.scl(speed * Gdx.graphics.getDeltaTime());
            position.add(direction);
        }
    }
}
