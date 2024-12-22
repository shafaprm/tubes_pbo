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

    // Constructor to initialize the Title object with a given BufferedImage
    public Title(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.x = -width;
        this.done = false;
    }

    // Reset the Title to its initial state
    public void reset() {
        this.done = false;
        this.remove = false;
        this.x = -width;
    }

    // Set the vertical position of the Title
    public void sety(double y) {
        this.y = y;
    }

    // Begin the Title animation by setting the horizontal speed
    public void begin() {
        this.dx = 10;
    }

    // Update the Title's position based on its state and speed
    public void update() {
        if (!done) {
            if (x >= (GamePanel.WIDTH - width) / 2.0) {
                x = (GamePanel.WIDTH - width) / 2.0;  // Stop at the center
                count++;

                if (count >= 120) {  // Stop after 120 updates
                    done = true;
                }
            } else {
                x += dx;  // Move the Title towards the center
            }
        } else {
            x += dx;  // After being centered, move the Title out of the screen

            if (x > GamePanel.WIDTH) {  // If the Title has moved beyond the screen width
                remove = true;  // Mark it for removal
            }
        }
    }

    // Draw the Title on the screen if it hasn't been removed
    public void draw(Graphics2D g) {
        if (!remove) {
            g.drawImage(image, (int) x, (int) y, null);  // Draw the Title at its position
        }
    }
}
