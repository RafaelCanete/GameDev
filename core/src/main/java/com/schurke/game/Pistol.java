package com.schurke.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Pistol implements Weapon {
    private final float cooldown = 0.3f;
    private final float damage = 50f;
    private final int magazineSize = 10;

    private int currentAmmo = magazineSize;
    private int reserveAmmo = 90;

    @Override
    public List<Bullet> shoot(Vector2 position, Vector2 direction) {
        List<Bullet> bullets = new ArrayList<>();
        if (!hasAmmo())
            return bullets;

        bullets.add(new Bullet(position, direction, damage));
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
    public float getCooldown() {
        return cooldown;
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
    public int getCurrentAmmo() {
        return GameConfig.isUnlimitedAmmo() ? -1 : currentAmmo;
    }

    @Override
    public int getReserveAmmo() {
        return GameConfig.isUnlimitedAmmo() ? -1 : reserveAmmo;
    }
}
