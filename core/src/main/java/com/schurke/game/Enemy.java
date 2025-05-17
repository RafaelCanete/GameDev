package com.schurke.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private Vector2 position;
    private static float size = 20f;
    public int health = 50;
    private float damegeCooldown = 1.0f;

    public Enemy(Vector2 position){
        this.position = new Vector2(position);

    }
    public void render(ShapeRenderer shape){
        if (position != null) {
            shape.setColor(1, 0, 0, 1);
            shape.circle(position.x, position.y, size / 2f);
        }
    }
    public Vector2 getPosition(){
        return position;
    }

    public void update(Player player){
        float speed = 100f;
        float delta = Gdx.graphics.getDeltaTime();
        damegeCooldown -= delta;

        if (position.dst(player.getPosition())<20f){
            if (damegeCooldown <=0f) {
                player.takeDamege(20);
                damegeCooldown = 1.0f;
            }
            return;
        }

        Vector2 target = new Vector2(player.getPosition());
        Vector2 direction = target.sub(position);

        if (direction.len()>1f){
            direction.nor().scl(speed* delta);
            position.add(direction);

        }
    }
    public boolean isDead(){
        return health<=0;
    }



}
