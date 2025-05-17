package com.schurke.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    private Vector2 position;
    private static float size = 20f;
    private float damageCooldown;
    private float health;
    private float attackDamage;

    public Enemy(Vector2 position, float health, float damageCooldown, float attackDamage){
        this.position = new Vector2(position);
        this.health = health;
        this.damageCooldown = damageCooldown;
        this.attackDamage = attackDamage;
    }

    public void render(ShapeRenderer shape){
        if (position != null) {
            shape.setColor(1, 0, 0, 1);
            shape.rect(position.x, position.y, size, size);
        }
    }

    public Vector2 getPosition(){
        return position;
    }

    public void update(ArrayList<Enemy> allEnemies, Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 toPlayer = new Vector2(playerPosition).sub(position).nor();
        Vector2 separation = new Vector2();

        float speed = 100f;
        float delta = Gdx.graphics.getDeltaTime();
        damageCooldown -= delta;
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

        if (this.position.dst(playerPosition)<20f){
            if (damageCooldown <= 0f) {
                player.takeDamage(this.attackDamage);
                damageCooldown = 1.0f;
            }
            return;
        }

        // Combine movement vectors
        Vector2 finalVelocity = new Vector2(toPlayer).scl(speed).add(separation.scl(separationStrength));
        position.add(finalVelocity.scl(Gdx.graphics.getDeltaTime()));
    }

    public boolean isDead(){
        return health <= 0;
    }
}
