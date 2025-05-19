package com.schurke.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Pistol implements Weapon {
    private final float cooldown = 0.3f;
    private final float damage = 50f;
    private int shots = 10;

    @Override
    public List<Bullet> shoot(Vector2 position, Vector2 direction) {
        List<Bullet> bullets = new ArrayList<>();
        bullets.add(new Bullet(position, direction, damage));
        shots--;
        if (shots <= 0) {
            System.out.println("Out of ammo!");
        }
        return bullets;
    }

    @Override
    public float getCooldown() {
        return cooldown;
    }
}
