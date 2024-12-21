package com.tubes_pbo._d_platformer.model;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class Enemy extends MapObject {
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean remove;
    protected boolean flinching;
    protected long flinchCount;

    public Enemy(TileMap tm) {
        super(tm);
        remove = false;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) {
            return;
        }

        JukeBox.play("enemyhit");

        health -= damage;
        health = Math.max(health, 0); // Ensure health does not go below zero

        if (health == 0) {
            dead = true;
            remove = true;
        }

        flinching = true;
        flinchCount = 0;
    }

    public void update() {
        throw new IllegalStateException("Method 'update' must be overridden in subclasses.");
    }

    public enum EnemyType {
        RED_ENERGY, UFO, XHELBAT, ZOGU, SPIRIT
    }
}
