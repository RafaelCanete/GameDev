package com.schurke.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
        enemyManager = new EnemyManager(map);
        enemyManager.spawnEnemy(5);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(image, 140, 210);
        map.render(batch);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shape);
        enemyManager.render(shape);
        enemyManager.update(player);
        player.update(map);
        shape.end();

        if (player.isDead()){
            System.out.println("Game Over! Player is dead!!");
            return;
        }

        shape.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        shape.begin(ShapeRenderer.ShapeType.Filled);
        healthBar();
        shape.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shape.dispose();
        map.dispose();
    }

    private void healthBar(){
        float maxHealth = 200;
        float currentHealth = player.getHealth();
        float width = 200f;
        float height = 20f;
        float x = 20f;
        float y = Gdx.graphics.getHeight()-height-20f;
        shape.setColor(0.3f,0.3f,0.3f,1f);
        shape.rect(x,y,width,height);

        shape.setColor(0f,1f,0f,1f);
        shape.rect(x,y,(currentHealth/maxHealth)*width,height);

    }

}

