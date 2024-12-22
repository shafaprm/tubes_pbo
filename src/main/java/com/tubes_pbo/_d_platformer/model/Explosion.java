package com.tubes_pbo._d_platformer.model;

import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends MapObject {

    private BufferedImage[] sprites;
    private boolean remove;
    private Point[] points;
    private int speed;
    private double diagSpeed;

    public Explosion(TileMap tm, int x, int y) {
        super(tm);
        initializePosition(x, y);
        initializeDimensions();
        initializeSpeeds();
        initializeSprites();
        initializePoints(x, y);
    }

    private void initializePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void initializeDimensions() {
        width = 30;
        height = 30;
    }

    private void initializeSpeeds() {
        speed = 2;
        diagSpeed = 1.41;
    }

    private void initializeSprites() {
        sprites = Content.getExplosions()[0];
        animation.setFrames(sprites);
        animation.setDelay(6);
    }

    private void initializePoints(int x, int y) {
        points = new Point[8];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(x, y);
        }
    }

    public void update() {
        updateAnimation();
        updatePoints();
    }

    private void updateAnimation() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    private void updatePoints() {
        points[0].x += speed;
        points[1].x += diagSpeed;
        points[1].y += diagSpeed;
        points[2].y += speed;
        points[3].x -= diagSpeed;
        points[3].y += diagSpeed;
        points[4].x -= speed;
        points[5].x -= diagSpeed;
        points[5].y -= diagSpeed;
        points[6].y -= speed;
        points[7].x += diagSpeed;
        points[7].y -= diagSpeed;
    }

    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void draw(Graphics2D g) {
        setMapPosition();
        drawPoints(g);
    }

    private void drawPoints(Graphics2D g) {
        for (Point point : points) {
            g.drawImage(animation.getImage(),
                    (int) (point.x + xmap - width / 2.0),
                    (int) (point.y + ymap - height / 2.0),
                    null);
        }
    }
}