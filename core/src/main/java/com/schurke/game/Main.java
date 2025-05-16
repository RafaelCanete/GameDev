package com.schurke.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private TileMap map;
    private ShapeRenderer shape;
    private Player player;
    private OrthographicCamera camera;
    private EnemyManager enemyManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        map = new TileMap();
        shape = new ShapeRenderer();
        player = new Player(map.getCenter());
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1280,960);
        enemyManager = new EnemyManager(map, 10);
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
        enemyManager.render(shape);
        player.update(map);
        enemyManager.update(player.getPosition());
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
}

