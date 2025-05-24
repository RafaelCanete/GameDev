package com.schurke.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        setScreen(new StartScreen(this));
    }

    public void startGame() {
        setScreen(new GameScreen(this));
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}

