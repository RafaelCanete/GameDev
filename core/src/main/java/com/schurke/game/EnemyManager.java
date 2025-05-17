package com.schurke.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private ArrayList<Enemy> enemies;
    private Random random;
    private TileMap map;

    public EnemyManager(TileMap map) {
        this.map = map;
        this.enemies = new ArrayList<>();
        this.random = new Random();
    }

    public void spawnEnemy (int count){
        for (int i=0;i< count; i++){
            float margin = 30f;
            float x = margin + random.nextFloat()*(map.getMapWidth()* map.getTileSize() -2 * margin);
            float y = margin + random.nextFloat()*(map.getMapHeight()* map.getTileSize() -2 * margin);
            enemies.add(new Enemy(new Vector2(x,y), 100f, 1f, 20f));
        }
    }

    public void update(Player player){
        for (Enemy enemy: enemies){
            enemy.update(player);
        }
    }

    public void render(ShapeRenderer shape){
        for (Enemy enemy:enemies){
            enemy.render(shape);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void clear(){
        enemies.clear();
    }
}
