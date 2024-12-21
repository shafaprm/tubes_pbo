*MAP OBJECT*

package com.tubes_pbo._d_platformer.model;

import java.awt.Rectangle;
import com.tubes_pbo._d_platformer.main.GamePanel;
import com.tubes_pbo._d_platformer.tilemap.Tile;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class MapObject {
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    // Position and vector
    protected double x, y, dx, dy;

    // Dimensions
    protected int width, height;

    // Collision box
    protected int cwidth, cheight;

    // Collision details
    protected int currRow, currCol;
    protected double xdest, ydest, xtemp, ytemp;
    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    // Animation
    protected Animation animation;
    protected int currentAction, previousAction;
    protected boolean facingRight;

    // Movement
    protected boolean left, right, up, down, jumping, falling;

    // Movement attributes
    protected double moveSpeed, maxSpeed, stopSpeed, fallSpeed, maxFallSpeed, jumpStart, stopJumpSpeed;

    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize();
        animation = new Animation();
        facingRight = true;
        currentAction = -1;
    }

    public boolean intersects(MapObject o) {
        return getRectangle().intersects(o.getRectangle());
    }

    public boolean intersects(Rectangle r) {
        return getRectangle().intersects(r);
    }

    public boolean contains(MapObject o) {
        return getRectangle().contains(o.getRectangle());
    }

    public boolean contains(Rectangle r) {
        return getRectangle().contains(r);
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) x - cwidth / 2, (int) y - cheight / 2, cwidth, cheight);
    }

    public void calculateCorners(double x, double y) {
        int leftTile = (int) (x - cwidth / 2.0) / tileSize;
        int rightTile = (int) (x + cwidth / 2.0 - 1) / tileSize;
        int topTile = (int) (y - cheight / 2.0) / tileSize;
        int bottomTile = (int) (y + cheight / 2.0 - 1) / tileSize;

        if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }

        topLeft = tileMap.getType(topTile, leftTile) == Tile.BLOCKED;
        topRight = tileMap.getType(topTile, rightTile) == Tile.BLOCKED;
        bottomLeft = tileMap.getType(bottomTile, leftTile) == Tile.BLOCKED;
        bottomRight = tileMap.getType(bottomTile, rightTile) == Tile.BLOCKED;
    }

    public void checkTileMapCollision() {
        currCol = (int) x / tileSize;
        currRow = (int) y / tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        checkYCollision();
        checkXCollision();

        if (!falling) {
            calculateCorners(x, ydest + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    private void checkXCollision() {
        calculateCorners(xdest, y);

        if (dx < 0 && (topLeft || bottomLeft)) {
            dx = 0;
            xtemp = currCol * tileSize + cwidth / 2.0;
        } else if (dx > 0 && (topRight || bottomRight)) {
            dx = 0;
            xtemp = (currCol + 1) * tileSize - cwidth / 2.0;
        } else {
            xtemp += dx;
        }
    }

    private void checkYCollision() {
        calculateCorners(x, ydest);

        if (dy < 0 && (topLeft || topRight)) {
            dy = 0;
            ytemp = currRow * tileSize + cheight / 2.0;
        } else if (dy > 0 && (bottomLeft || bottomRight)) {
            dy = 0;
            falling = false;
            ytemp = (currRow + 1) * tileSize - cheight / 2.0;
        } else {
            ytemp += dy;
        }
    }

    public int getx() {
        return (int) x;
    }

    public int gety() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        xmap = tileMap.getx();
        ymap = tileMap.gety();
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setJumping(boolean b) {
        jumping = b;
    }

    public boolean notOnScreen() {
        return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH ||
                y + ymap + height < 0 || y + ymap - height > GamePanel.HEIGHT;
    }

    public void draw(java.awt.Graphics2D g) {
        setMapPosition();

        int drawX = (int) (x + xmap - width / 2.0);
        int drawY = (int) (y + ymap - height / 2.0);

        if (facingRight) {
            g.drawImage(animation.getImage(), drawX, drawY, null);
        } else {
            g.drawImage(animation.getImage(), drawX + width, drawY, -width, height, null);
        }
    }
}