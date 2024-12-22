package com.tubes_pbo._d_platformer.tilemap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.main.GamePanel;

public class Background {

    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private int width;
    private int height;
    private double xscale;
    private double yscale;

    public Background(String imagePath, double scale) {
        this(imagePath, scale, scale);
    }

    public Background(String imagePath, double xscale, double yscale) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.xscale = xscale;
            this.yscale = yscale;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * xscale) % width;
        this.y = (y * yscale) % height;
    }

    public void update() {
        x += dx;
        while (x <= -width) {
            x += width;
        }
        while (x >= width) {
            x -= width;
        }

        y += dy;
        while (y <= -height) {
            y += height;
        }
        while (y >= height) {
            y -= height;
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            graphics.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            graphics.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }
        if (y < 0) {
            graphics.drawImage(image, (int) x, (int) y + GamePanel.HEIGHT, null);
        }
        if (y > 0) {
            graphics.drawImage(image, (int) x, (int) y - GamePanel.HEIGHT, null);
        }
    }
}
