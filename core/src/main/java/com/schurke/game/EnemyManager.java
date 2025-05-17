package com.schurke.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private List<Enemy> enemy;
    private Random random;
    private TileMap map;

    public EnemyManager(TileMap map) {
        this.map = map;
        this.enemy = new ArrayList<>();
        this.random = new Random();
    }

    public void spawnEnemy (int count){
        for (int i=0;i< count; i++){
            float margin = 30f;
            float x = margin + random.nextFloat()*(map.getMapWidth()* map.getTileSize() -2 * margin);
            float y = margin + random.nextFloat()*(map.getMapHeight()* map.getTileSize() -2 * margin);
            enemy.add(new Enemy(new Vector2(x,y)));
        }
    }
    public void update(Player player){
        for (Enemy enemy1: enemy){
            enemy1.update(player);
        }
    }
    public void render(ShapeRenderer shape){
        for (Enemy enemy1:enemy){
            enemy1.render(shape);
        }
    }

    public List<Enemy> getEnemy() {
        return enemy;
    }
    public void clear(){
        enemy.clear();
    }
}
