package com.schurke.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Shotgun implements Weapon {
    private final float cooldown = 0.6f;
    private final float damage = 20f;
    private final int pelletCount = 3;
    private final float spreadAngle = 20f;

    private final int magazineSize = 2;
    private int currentAmmo = magazineSize;
    private int reserveAmmo = 30;

    @Override
    public List<Bullet> shoot(Vector2 position, Vector2 direction) {
        List<Bullet> bullets = new ArrayList<>();

        if (!hasAmmo()) return bullets;

        float baseAngle = direction.angleRad();
        float startAngle = baseAngle - (float) Math.toRadians(spreadAngle / 2f);
        float angleStep = (float) Math.toRadians(spreadAngle / (pelletCount - 1));

        for (int i = 0; i < pelletCount; i++) {
            float angle = startAngle + i * angleStep;
            Vector2 dir = new Vector2((float) Math.cos(angle), (float) Math.sin(angle)).nor();
            bullets.add(new Bullet(position, dir, damage));
        }

        currentAmmo--;
        return bullets;
    }

    @Override
    public boolean hasAmmo() {
        return GameConfig.isUnlimitedAmmo() || currentAmmo > 0;
    }

    @Override
    public int getAmmo() {
        return GameConfig.isUnlimitedAmmo() ? -1 : currentAmmo;
    }

    @Override
    public int getCurrentAmmo() {
        return GameConfig.isUnlimitedAmmo() ? -1 : currentAmmo;
    }

    @Override
    public int getReserveAmmo() {
        return GameConfig.isUnlimitedAmmo() ? -1 : reserveAmmo;
    }

    @Override
    public void reload() {
        if (GameConfig.isUnlimitedAmmo()) {
            currentAmmo = magazineSize;
            return;
        }

        int missing = magazineSize - currentAmmo;
        int toReload = Math.min(missing, reserveAmmo);
        currentAmmo += toReload;
        reserveAmmo -= toReload;
    }

    @Override
    public float getCooldown() {
        return cooldown;
    }
}
