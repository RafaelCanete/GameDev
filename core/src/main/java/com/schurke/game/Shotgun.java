package com.schurke.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Shotgun implements Weapon {
    private final float cooldown = 0.6f;
    private final float damage = 20f;
    private final int pelletCount = 3;

    private int ammo = 10;
    private final float spreadAngle = 20f; // in Grad, gesamt über alle Pellets verteilt

    @Override
    public List<Bullet> shoot(Vector2 position, Vector2 direction) {
        List<Bullet> bullets = new ArrayList<>();

        if (ammo <= 0)
            return bullets;

        // Mittelrichtung in Radiant
        float baseAngle = direction.angleRad();
        float startAngle = baseAngle - (float) Math.toRadians(spreadAngle / 2f);
        float angleStep = (float) Math.toRadians(spreadAngle / (pelletCount - 1));

        for (int i = 0; i < pelletCount; i++) {
            float angle = startAngle + i * angleStep;
            Vector2 dir = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor();
            bullets.add(new Bullet(position, dir, damage));
        }

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
