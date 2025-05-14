package com.schurke.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private TileMap map;
    private ShapeRenderer shape;
    private Player player;
    private OrthographicCamera camera;
    private List<Enemies> enemies;
    private Random random;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        map = new TileMap();
        shape = new ShapeRenderer();
        player = new Player(map.getCenter());
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1280,960);
        random = new Random();
        enemies = new ArrayList<>();
        for (int i=0; i<5;i++){
            enemies.add(spawnEnemy(map));
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        map.render(batch);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shape);

        for (Enemies enemy:enemies){
            enemy.render(shape);
        }
        player.update(map);
        shape.end();

        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();



    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shape.dispose();
        map.dispose();
    }
    private Enemies spawnEnemy(TileMap map){
        float margin = 30f;
        float x = margin + random.nextFloat()*(map.getMapWidth()* map.getTileSize() -2 * margin);
        float y = margin + random.nextFloat()*(map.getMapHeight()* map.getTileSize() -2 * margin);
        return new Enemies(new Vector2(x,y));
    }
}

