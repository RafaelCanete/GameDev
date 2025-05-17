package com.schurke.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class HealthBar {
    private float width;
    private float height;
    private Vector2 position = new Vector2(20f, Gdx.graphics.getHeight()-height-40f);
    private Player player;

    public HealthBar(Player player, float height) {
        this.player = player;
        this.height = height;
        this.width = player.getMaxHealth();
    }

    public void render(ShapeRenderer shape){
        float maxHealth = player.getMaxHealth();
        float currentHealth = player.getHealth();
        // Background of Healthbar
        shape.setColor(0.3f,0.3f,0.3f,1f);
        shape.rect(position.x, position.y, width, height);

        // Green Healthbar
        shape.setColor(0f,1f,0f,1f);
        shape.rect(position.x, position.y, (currentHealth/maxHealth)*width, height);
    }
}
