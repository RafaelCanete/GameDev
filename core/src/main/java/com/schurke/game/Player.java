package com.schurke.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private static float size = 20f;


    public Player(Vector2 startPosition){
        this.position = new Vector2(startPosition);
    }
    public void render(ShapeRenderer shape){
        shape.circle(position.x, position.y, size/2f);
    }

    public Vector2 getPosition() {
        return position;
    }
    public void update(TileMap map) {
        // Bewegungsgeschwindigkeit in Einheiten pro Sekunde
        float speed = 200f;
        // Zeit seit letztem Frame
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        float xNew = position.x; //position speichern
        float yNew = position.y;

        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            yNew += speed * delta;
        }
        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            yNew -= speed * delta;
        }
        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
            xNew -= speed * delta;
        }
        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            xNew += speed * delta;
        }
        float margin = size/2f;
        if (map.isInsideMap(xNew, position.y, margin)){
            position.x = xNew;
        }
        if (map.isInsideMap(position.x, yNew, margin)){
            position.y = yNew;
        }
    }

    public void setPosition(float x, float y){
        this.position.set(x,y);
    }
    public float getSize(){
        return size;
    }

}
