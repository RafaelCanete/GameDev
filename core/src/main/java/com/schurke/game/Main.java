package com.schurke.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private TileMap map;
    private ShapeRenderer shape;
    private Player player;
    private HealthBar playerHealthBar;
    private OrthographicCamera camera;
    private EnemyManager enemyManager;
    private ArrayList<Bullet> bullets;
    private Weapon currentWeapon;
    private BitmapFont font;
    private SpriteBatch hudBatch;
    private CombatController combatController;
    private BulletManager bulletManager;
    private AimRenderer aimRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        map = new TileMap();
        shape = new ShapeRenderer();
        player = new Player(map.getCenter(), 100f, 25f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 960);
        enemyManager = new EnemyManager(map);
        enemyManager.spawnEnemy(10);
        playerHealthBar = new HealthBar(player, 20f);
        font = new BitmapFont();
        hudBatch = new SpriteBatch();
        bullets = new ArrayList<>();
        currentWeapon = new Pistol();
        combatController = new CombatController(player, currentWeapon, camera, bullets);
        bulletManager = new BulletManager(bullets, enemyManager);
        aimRenderer = new AimRenderer(camera, player);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float delta = Gdx.graphics.getDeltaTime();

        // Kamera & Projektionen
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        // Welt zeichnen
        batch.begin();
        batch.draw(image, 140, 210);
        map.render(batch);
        batch.end();

        // Shape Rendering
        shape.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shape);
        aimRenderer.render(shape);
        enemyManager.render(shape);
        enemyManager.update(player);
        player.update(map);
        combatController.update(delta);
        bulletManager.updateAndRender(delta, shape);
        shape.end();

        // HUD
        renderHUD();

        if (player.isDead()) {
            System.out.println("Game Over! Player is dead!!");
            this.dispose();
            Gdx.app.exit();
        }
    }

    private void renderHUD() {
        shape.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        shape.begin(ShapeRenderer.ShapeType.Filled);
        playerHealthBar.render(shape);
        shape.end();

        hudBatch.begin();
        if (GameConfig.isUnlimitedAmmo()) {
            font.draw(hudBatch, "Ammo: ∞", 20, 40);
        } else {
            font.draw(hudBatch, "Ammo: " + currentWeapon.getCurrentAmmo() + "/" + currentWeapon.getReserveAmmo(), 20,
                    40);
        }
        if (currentWeapon.isReloading()) {
            font.draw(hudBatch, "Reloading...", 20, 70);
        }
        hudBatch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shape.dispose();
        map.dispose();
        font.dispose();
        hudBatch.dispose();
        currentWeapon.dispose();
    }
}
