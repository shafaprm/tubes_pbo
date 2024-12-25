package com.tubes_pbo._d_platformer.model.enemies;

import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;
import com.tubes_pbo._d_platformer.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class XhelBat extends Flyer {
    private final BufferedImage[] sprites;
    private final Player player;
    private boolean active;

    public XhelBat(TileMap tm, Player player) {
        super(tm, FlyerType.XHEL_BAT);

        this.player = player;

        sprites = Content.getXhelbat()[0];
        animation.setFrames(sprites);
        animation.setDelay(4);

        left = true;
        facingRight = false;
    }

    @Override
    public void update() {
        activateIfInRange();

        if (!active) {
            return;
        }

        handleFlinching();

        getNextPosition();
        checkTileMapCollision();
        updateDirectionBasedOnCorners();
        setPosition(xtemp, ytemp);

        reverseDirectionIfBlocked();

        animation.update();
    }

    private void activateIfInRange() {
        if (!active && Math.abs(player.getx() - x) < GamePanel.WIDTH) {
            active = true;
        }
    }

    private void handleFlinching() {
        if (flinching) {
            flinchCount++;
            if (flinchCount >= 6) {
                flinching = false;
            }
        }
    }

    private void updateDirectionBasedOnCorners() {
        calculateCorners(x, ydest + 1);

        if (!bottomLeft) {
            left = false;
            right = true;
            facingRight = true;
        } else if (!bottomRight) {
            left = true;
            right = false;
            facingRight = false;
        }
    }

    private void reverseDirectionIfBlocked() {
        if (dx == 0) {
            left = !left;
            right = !right;
            facingRight = !facingRight;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }
        super.draw(g);
    }
}
