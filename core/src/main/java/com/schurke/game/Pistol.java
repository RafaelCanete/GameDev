package com.schurke.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Pistol implements Weapon {
    private final float cooldown = 0.3f;
    private final float damage = 50f;
    private int ammo = 10;

    @Override
    public List<Bullet> shoot(Vector2 position, Vector2 direction) {
        List<Bullet> bullets = new ArrayList<>();
        if (ammo <= 0)
            return bullets;

        bullets.add(new Bullet(position, direction, damage));
        ammo--;
        return bullets;
    }

    @Override
    public float getCooldown() {
        return cooldown;
    }

    @Override
    public boolean hasAmmo() {
        return ammo > 0;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }
}
