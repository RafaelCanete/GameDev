package com.schurke.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private Vector2 velocity;
    private float lifetime = 2f;
    private float damage;

    private static final float SPEED = 400f;
    private static final float SIZE = 5f;

    public Bullet(Vector2 position, Vector2 direction, float damage) {
        this.position = new Vector2(position);
        this.velocity = new Vector2(direction).nor().scl(SPEED);
        this.damage = damage;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        lifetime -= delta;
    }

    public void render(ShapeRenderer shape) {
        shape.setColor(1f, 1f, 0f, 1f);
        shape.circle(position.x, position.y, SIZE);
    }

    public boolean collidesWith(Enemy enemy) {
        return enemy.getPosition().dst(position) < (Enemy.getSize() / 2f);
    }

    public float getDamage() {
        return damage;
    }

    public boolean isExpired() {
        return lifetime <= 0f;
    }

    public Vector2 getPosition() {
        return position;
    }
}
