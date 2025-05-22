package com.schurke.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private float shootCooldown = 0f;
    private Weapon currentWeapon;
    private BitmapFont font;
    private SpriteBatch hudBatch;

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
        currentWeapon = new Shotgun();

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

        // Zielrichtung vom Spieler zur Maus visualisieren
        renderAimLine();

        enemyManager.render(shape);
        enemyManager.update(player);
        player.update(map);

        // Schießen
        float delta = Gdx.graphics.getDeltaTime();
        handleShooting(delta);

        //Reloading
        handleReloading();

        // Bullets updaten
        updateAndRenderBullets(delta);

        shape.end();

        if (player.isDead()) {
            System.out.println("Game Over! Player is dead!!");
            this.dispose();
            Gdx.app.exit();
            return;
        }

        shape.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        shape.begin(ShapeRenderer.ShapeType.Filled);
        playerHealthBar.render(shape);

        hudBatch.begin();

        if (GameConfig.isUnlimitedAmmo()) {
            font.draw(hudBatch, "Ammo: ∞", 20, 40);
        } else {
            int current = currentWeapon.getCurrentAmmo();
            int reserve = currentWeapon.getReserveAmmo();
            font.draw(hudBatch, "Ammo: " + current + "/" + reserve, 20, 40);
        }

        hudBatch.end();

        shape.end();
    }

    private void renderAimLine() {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        Vector2 playerPos = player.getPosition();
        Vector2 toMouse = new Vector2(mousePos.x, mousePos.y).sub(playerPos).nor();

        float length = 40f;
        float thickness = 4f;

        Vector2 perpendicular = new Vector2(-toMouse.y, toMouse.x).nor().scl(thickness / 2f);

        Vector2 p1 = new Vector2(playerPos).add(perpendicular);
        Vector2 p2 = new Vector2(playerPos).sub(perpendicular);
        Vector2 p3 = new Vector2(playerPos).add(toMouse.scl(length)).sub(perpendicular);
        Vector2 p4 = new Vector2(playerPos).add(toMouse).add(perpendicular);

        shape.triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        shape.triangle(p1.x, p1.y, p3.x, p3.y, p4.x, p4.y);
    }

    private void handleShooting(float delta) {
        shootCooldown -= delta;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && shootCooldown <= 0f && currentWeapon.hasAmmo()) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos);

            Vector2 shootDir = new Vector2(mousePos.x, mousePos.y).sub(player.getPosition()).nor();

            List<Bullet> newBullets = currentWeapon.shoot(player.getPosition(), shootDir);
            bullets.addAll(newBullets);

            shootCooldown = currentWeapon.getCooldown();
        }
    }

    private void handleReloading() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) || !currentWeapon.hasAmmo()) {
            currentWeapon.reload();
        }

    }

    private void updateAndRenderBullets(float delta) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(delta);
            bullet.render(shape);

            Iterator<Enemy> enemyIterator = enemyManager.getEnemies().iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (bullet.collidesWith(enemy)) {
                    enemy.takeDamage(50f);
                    bulletIterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        shape.dispose();
        map.dispose();
        font.dispose();
        hudBatch.dispose();
    }
}
