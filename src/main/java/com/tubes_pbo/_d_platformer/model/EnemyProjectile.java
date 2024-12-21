package com.tubes_pbo._d_platformer.model;

import com.tubes_pbo._d_platformer.tilemap.TileMap;

public abstract class EnemyProjectile extends MapObject{
    protected boolean remove;

    public EnemyProjectile(TileMap tm) {
        super(tm);
        remove = false;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public abstract void update();
}
