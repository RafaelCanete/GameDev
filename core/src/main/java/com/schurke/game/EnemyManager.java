package com.schurke.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class EnemyManager {
    private ArrayList<Enemy> enemies;
    private Random random;

    public EnemyManager(TileMap map, int count) {
        this.enemies = new ArrayList<Enemy>();
        this.random = new Random();
        for(int i = 0; i < count; i++) {
            enemies.add(this.createEnemy(map));
        }
    }

    public void render(ShapeRenderer renderer) {
        for (Enemy enemy: enemies) {
            enemy.render(renderer);
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    public void update(Vector2 position) {
        for (Enemy enemy: enemies) {
            enemy.update(enemies, position);
        }
    }

    private Enemy createEnemy(TileMap map){
        float margin = 30f;
        float x = margin + random.nextFloat()*(map.getMapWidth()* map.getTileSize() -2 * margin);
        float y = margin + random.nextFloat()*(map.getMapHeight()* map.getTileSize() -2 * margin);
        return new Enemy(new Vector2(x,y), 100f);
    }
}
