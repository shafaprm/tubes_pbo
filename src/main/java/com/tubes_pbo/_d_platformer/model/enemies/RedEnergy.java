package com.tubes_pbo._d_platformer.model.enemies;

import com.tubes_pbo._d_platformer.model.Enemy;
import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

import java.awt.image.BufferedImage;

public class RedEnergy extends Enemy {

    public static final int VECTOR = 0;
    public static final int GRAVITY = 1;
    public static final int BOUNCE = 2;

    private final BufferedImage[] startSprites;
    private final BufferedImage[] sprites;
    private boolean start;
    private boolean permanent;
    private int type;
    private int bounceCount;

    public RedEnergy(TileMap tm) {
        super(tm);

        health = maxHealth = 1;

        width = 20;
        height = 20;
        cwidth = 12;
        cheight = 12;

        damage = 1;
        moveSpeed = 5;

        startSprites = Content.getRedEnergy()[0];
        sprites = Content.getRedEnergy()[1];

        animation.setFrames(startSprites);
        animation.setDelay(2);

        start = true;
        flinching = true;
        permanent = false;
        type = VECTOR;
        bounceCount = 0;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }

    @Override
    public void update() {
        handleStartAnimation();
        updatePositionBasedOnType();
        updateAnimation();
        handleRemovalConditions();
    }

    private void handleStartAnimation() {
        if (start && animation.hasPlayedOnce()) {
            animation.setFrames(sprites);
            animation.setNumFrames(3);
            animation.setDelay(2);
            start = false;
        }
    }

    private void updatePositionBasedOnType() {
        switch (type) {
            case VECTOR -> {
                x += dx;
                y += dy;
            }
            case GRAVITY -> {
                dy += 0.2;
                x += dx;
                y += dy;
            }
            case BOUNCE -> handleBounceMovement();
        }
    }

    private void handleBounceMovement() {
        double previousDx = dx;
        double previousDy = dy;

        checkTileMapCollision();

        if (dx == 0) {
            dx = -previousDx;
            bounceCount++;
        }
        if (dy == 0) {
            dy = -previousDy;
            bounceCount++;
        }

        x += dx;
        y += dy;
    }

    private void updateAnimation() {
        animation.update();
    }

    private void handleRemovalConditions() {
        if (!permanent) {
            boolean outOfBounds = x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight();
            if (outOfBounds || bounceCount == 3) {
                remove = true;
            }
        }
    }
}