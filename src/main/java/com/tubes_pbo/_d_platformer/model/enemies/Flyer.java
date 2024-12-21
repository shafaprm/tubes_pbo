package com.tubes_pbo._d_platformer.model.enemies;

import com.tubes_pbo._d_platformer.model.Enemy;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class Flyer extends Enemy {
    private final double[][] initValues = {
            {4, 30, 30, 20, 26, 1, 1.5, 0.15, 4.0, -5},
            {1, 25, 25, 20, 18, 1, 0.8, 0.15, 4.0, -5}
    };

    public Flyer(TileMap tm, FlyerType type) {
        super(tm);
        initializeAttributes(type.value);
    }

    private void initializeAttributes(int typeIndex) {
        health = maxHealth = (int) initValues[typeIndex][0];

        width = (int) initValues[typeIndex][1];
        height = (int) initValues[typeIndex][2];
        cwidth = (int) initValues[typeIndex][3];
        cheight = (int) initValues[typeIndex][4];

        damage = (int) initValues[typeIndex][5];
        moveSpeed = initValues[typeIndex][6];
        fallSpeed = initValues[typeIndex][7];
        maxFallSpeed = initValues[typeIndex][8];
        jumpStart = initValues[typeIndex][9];
    }

    protected void getNextPosition() {
        dx = left ? -moveSpeed : right ? moveSpeed : 0;

        if (falling) {
            dy += fallSpeed;
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }

        if (jumping && !falling) {
            dy = jumpStart;
        }
    }

    public enum FlyerType {
        UFO(0), XHEL_BAT(1);

        public final int value;

        FlyerType(int value) {
            this.value = value;
        }
    }
}