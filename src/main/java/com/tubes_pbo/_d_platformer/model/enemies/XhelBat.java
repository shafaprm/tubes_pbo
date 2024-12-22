package com.tubes_pbo._d_platformer.model.enemies;

import com.tubes_pbo._d_platformer.model.Enemy;
import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zogu extends Enemy {
    private final BufferedImage[] idleSprites;

    private int tick;
    private final double oscillationRateX;
    private final double oscillationRateY;

    public Zogu(TileMap tm) {
        super(tm);

        health = maxHealth = 2;

        width = 39;
        height = 20;
        cwidth = 25;
        cheight = 15;

        damage = 1;
        moveSpeed = 5;

        idleSprites = Content.getZogu()[0];
        animation.setFrames(idleSprites);
        animation.setDelay(4);

        tick = 0;
        oscillationRateX = Math.random() * 0.06 + 0.07;
        oscillationRateY = Math.random() * 0.06 + 0.07;
    }

    @Override
    public void update() {
        handleFlinching();

        updatePosition();

        animation.update();
    }

    private void handleFlinching() {
        if (flinching) {
            flinchCount++;
            if (flinchCount >= 6) {
                flinching = false;
            }
        }
    }

    private void updatePosition() {
        tick++;
        x += Math.sin(oscillationRateX * tick);
        y += Math.sin(oscillationRateY * tick);
    }

    @Override
    public void draw(Graphics2D g) {
        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }
        super.draw(g);
    }
}