package com.schurke.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class TileMap {
    private static int tileSize = 64;
    private static int mapWidth = 20;
    private static int mapHeight =15;

    private Texture grassTexture;

    public TileMap(){
        grassTexture = new Texture(Gdx.files.internal("textures/grass.png"));
    }

public void render(SpriteBatch batch){
        for (int y=0; y<mapHeight;y++){
            for (int x=0; x<mapWidth;x++){
                batch.draw(grassTexture,x*tileSize,y*tileSize);
            }
        }
    }
public Vector2 getCenter(){
        return new Vector2((mapWidth/2f)*tileSize,(mapHeight/2f)*tileSize);
    }

public boolean isInsideMap(float x, float y){
        return x>=0 && y>=0 && x < mapWidth * tileSize && y < mapHeight * tileSize;
    }

    public void dispose() {
        grassTexture.dispose();
    }


}
