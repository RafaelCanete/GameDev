package com.schurke.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture image;
    private TileMap map;
    private ShapeRenderer shape;
    private Player player;
    private HealthBar playerHealthBar;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Viewport viewport;
    private Viewport uiViewport;
    private EnemyManager enemyManager;
    private boolean gameOver;
    private float deathTimer;
    private static final float DEATH_DELAY = 0.5f; // Half second delay before transition

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.getBatch();
        this.shape = game.getShapeRenderer();
        this.gameOver = false;
        this.deathTimer = 0;
        
        image = new Texture("libgdx.png");
        map = new TileMap();
        player = new Player(map.getCenter(), 100f, 25f);
        
        // Game camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(map.getTileSize() * map.getMapWidth(), map.getTileSize() * map.getMapHeight(), camera);
        viewport.apply();
        camera.position.set(map.getCenter(), 0);
        
        // UI camera and viewport
        uiCamera = new OrthographicCamera();
        uiViewport = new ScreenViewport(uiCamera);
        uiViewport.apply();
        
        enemyManager = new EnemyManager(map);
        enemyManager.spawnEnemy(10);
        playerHealthBar = new HealthBar(player, 20f);
    }

    @Override
    public void render(float delta) {
        if (gameOver) {
            deathTimer += delta;
            if (deathTimer >= DEATH_DELAY) {
                try {
                    Screen gameOverScreen = new GameOverScreen(game);
                    game.setScreen(gameOverScreen);
                } catch (Exception e) {
                    Gdx.app.error("GameScreen", "Error transitioning to GameOverScreen", e);
                }
                return;
            }
        }

        // Check for ESC key
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(image, 140, 210);
        map.render(batch);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shape);
        enemyManager.render(shape);
        if (!gameOver) {
            enemyManager.update(player);
            player.update(map);
        }
        shape.end();

        // UI rendering with separate camera
        uiViewport.apply();
        shape.setProjectionMatrix(uiCamera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        playerHealthBar.render(shape);
        shape.end();

        if (player.isDead() && !gameOver) {
            gameOver = true;
            deathTimer = 0;
        }
    }

    public void renderWithoutUpdate() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(image, 140, 210);
        map.render(batch);
        batch.end();

        shape.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shape);
        enemyManager.render(shape);
        shape.end();

        // UI rendering with separate camera
        uiViewport.apply();
        shape.setProjectionMatrix(uiCamera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        playerHealthBar.render(shape);
        shape.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);  // Game screen doesn't need input processor
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        try {
            if (image != null) image.dispose();
            if (map != null) map.dispose();
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Error disposing resources", e);
        }
    }
}