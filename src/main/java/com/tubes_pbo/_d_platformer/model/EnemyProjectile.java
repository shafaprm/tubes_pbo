package com.tubes_pbo._d_platformer.model;

import com.tubes_pbo._d_platformer.tilemap.TileMap;

public abstract class EnemyProjectile extends MapObject {
    protected boolean hit;
    protected boolean remove;
    protected int damage;

    public EnemyProjectile(TileMap tm) {
        super(tm);
    }

    public int getDamage() {
        return damage;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public abstract void setHit();

    public abstract void update();
}
