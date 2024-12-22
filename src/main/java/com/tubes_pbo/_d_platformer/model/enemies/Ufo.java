package com.tubes_pbo._d_platformer.model.enemies;

import com.tubes_pbo._d_platformer.model.Enemy;
import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.handlers.Content;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Ufo extends Flyer {
    private static final int IDLE = 0;
    private static final int JUMPING = 1;
    private static final int ATTACKING = 2;

    private final Player player;
    private final List<Enemy> enemies;

    private final BufferedImage[] idleSprites;
    private final BufferedImage[] jumpSprites;
    private final BufferedImage[] attackSprites;

    private int attackTick;
    private static final int ATTACK_DELAY = 30;
    private int step;

    public Ufo(TileMap tm, Player p, List<Enemy> en) {
        super(tm, FlyerType.UFO);

        player = p;
        enemies = en;

        idleSprites = Content.getUfo()[0];
        jumpSprites = Content.getUfo()[1];
        attackSprites = Content.getUfo()[2];

        animation.setFrames(idleSprites);
        animation.setDelay(-1);

        attackTick = 0;
    }

    @Override
    public void update() {
        handleFlinching();

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        animation.update();

        facingRight = player.getx() >= x;

        switch (step) {
            case 0 -> updateIdle();
            case 1 -> updateJump();
            case 2 -> updateAttack();
            case 3 -> {
                if (dy == 0) step++;
            }
            default -> resetToIdleState();
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

    private void updateIdle() {
        if (currentAction != IDLE) {
            setAction(IDLE, idleSprites, -1);
        }

        attackTick++;
        if (attackTick >= ATTACK_DELAY && Math.abs(player.getx() - x) < 60) {
            step++;
            attackTick = 0;
        }
    }

    private void updateJump() {
        if (currentAction != JUMPING) {
            setAction(JUMPING, jumpSprites, -1);
        }

        jumping = true;
        if (facingRight) {
            left = true;
        } else {
            right = true;
        }

        if (falling) {
            step++;
        }
    }

    private void updateAttack() {
        if (dy > 0 && currentAction != ATTACKING) {
            setAction(ATTACKING, attackSprites, 3);
            spawnRedEnergyProjectile();
        }

        if (currentAction == ATTACKING && animation.hasPlayedOnce()) {
            step++;
            setAction(JUMPING, jumpSprites, -1);
        }
    }

    private void spawnRedEnergyProjectile() {
        RedEnergy projectile = new RedEnergy(tileMap);
        projectile.setPosition(x, y);

        int xVector = facingRight ? 3 : -3;
        projectile.setVector(xVector, 3);

        enemies.add(projectile);
    }

    private void resetToIdleState() {
        step = 0;
        left = false;
        right = false;
        jumping = false;
    }

    private void setAction(int action, BufferedImage[] frames, int delay) {
        currentAction = action;
        animation.setFrames(frames);
        animation.setDelay(delay);
    }

    @Override
    public void draw(Graphics2D g) {
        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }
        super.draw(g);
    }
}