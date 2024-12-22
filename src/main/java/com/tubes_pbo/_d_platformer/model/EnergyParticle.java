package com.tubes_pbo._d_platformer.model;

import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

import java.awt.image.BufferedImage;

public class EnergyParticle extends MapObject {

    public static final int ENERGY_UP = 0;
    public static final int ENERGY_LEFT = 1;
    public static final int ENERGY_DOWN = 2;
    public static final int ENERGY_RIGHT = 3;

    private static final int LIFETIME = 60;
    private static final double VELOCITY_RANGE = 2.5;
    private static final double VELOCITY_OFFSET = 1.25;
    private static final double VELOCITY_Y_BASE = 0.8;

    private int count;
    private boolean remove;
    private BufferedImage[] sprites;

    public EnergyParticle(TileMap tm, double x, double y, int dir) {
        super(tm);
        this.x = x;
        this.y = y;

        initializeVelocity(dir);

        count = 0;
        sprites = Content.getEnergyParticle()[0];
        animation.setFrames(sprites);
        animation.setDelay(-1);
    }

    private void initializeVelocity(int dir) {
        double randomX = Math.random() * VELOCITY_RANGE - VELOCITY_OFFSET;
        double randomY = -Math.random() - VELOCITY_Y_BASE;

        switch (dir) {
            case ENERGY_UP:
                dx = randomX;
                dy = randomY;
                break;
            case ENERGY_LEFT:
                dx = randomY;
                dy = randomX;
                break;
            case ENERGY_DOWN:
                dx = randomX;
                dy = -randomY;
                break;
            case ENERGY_RIGHT:
                dx = -randomY;
                dy = randomX;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    public void update() {
        updatePosition();
        checkLifetime();
    }

    private void updatePosition() {
        x += dx;
        y += dy;
    }

    private void checkLifetime() {
        count++;
        if (count >= LIFETIME) {
            remove = true;
        }
    }

    public boolean shouldRemove() {
        return remove;
    }
}