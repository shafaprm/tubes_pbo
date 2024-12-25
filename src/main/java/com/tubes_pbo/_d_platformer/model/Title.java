package com.tubes_pbo._d_platformer.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import com.tubes_pbo._d_platformer.main.GamePanel;

public class Title {
    private BufferedImage image;
    private int count;
    private boolean done;
    private boolean remove;

    private double x;
    private double y;
    private double dx;

    private int width;

    public Title(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.x = -width;
        this.done = false;
    }

    public void reset() {
        this.done = false;
        this.remove = false;
        this.x = -width;
    }

    public void sety(double y) {
        this.y = y;
    }

    public void begin() {
        this.dx = 10;
    }

    public void update() {
        if (!done) {
            if (x >= (GamePanel.WIDTH - width) / 2.0) {
                x = (GamePanel.WIDTH - width) / 2.0;
                count++;

                if (count >= 120) {
                    done = true;
                }
            } else {
                x += dx;
            }
        } else {
            x += dx;

            if (x > GamePanel.WIDTH) {
                remove = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        if (!remove) {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }
}
