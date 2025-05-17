package com.schurke.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private Vector2 position;
    private static float size = 20f;
    private float speed;

    public Enemy(Vector2 position, float speed){
        this.position = new Vector2(position);
        this.speed = speed;
    }

    public void render(ShapeRenderer shape){
        shape.setColor(1,0,0,1);
        shape.rect(position.x,position.y,size,size);
        shape.setColor(1,1,1,1);
    }

    public Vector2 getPosition(){
        return position;
    }

    public void update(ArrayList<Enemy> allEnemies, Vector2 playerPosition) {
        Vector2 toPlayer = new Vector2(playerPosition).sub(position).nor();
        Vector2 separation = new Vector2();

        float separationDistance = 25f;
        float separationStrength = 100f;

        for (Enemy other : allEnemies) {
            if (other == this) continue;

            float distance = position.dst(other.position);
            if (distance < separationDistance && distance > 0.01f) {
                Vector2 push = new Vector2(position).sub(other.position).nor().scl((separationDistance - distance) / separationDistance);
                separation.add(push);
            }
        }

        // Combine movement vectors
        Vector2 finalVelocity = new Vector2(toPlayer).scl(speed).add(separation.scl(separationStrength));
        position.add(finalVelocity.scl(Gdx.graphics.getDeltaTime()));
    }

}
