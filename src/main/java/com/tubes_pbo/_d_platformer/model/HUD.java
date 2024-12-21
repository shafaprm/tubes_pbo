package com.tubes_pbo._d_platformer.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;

public class HUD {
    private final Player player;
    private final BufferedImage heart;
    private final BufferedImage life;

    public HUD(Player player) {
        this.player = player;
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif"));
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, "Failed to load HUD images: " + e.getMessage(), e);
        }

        heart = (image != null) ? image.getSubimage(0, 0, 13, 12) : null;
        life = (image != null) ? image.getSubimage(0, 12, 12, 11) : null;
    }

    public void draw(Graphics2D g) {
        drawHearts(g);
        drawLives(g);
        drawTimer(g);
    }

    private void drawHearts(Graphics2D g) {
        if (heart == null) return;

        for (int i = 0; i < player.getHealth(); i++) {
            g.drawImage(heart, 10 + i * 15, 10, null);
        }
    }

    private void drawLives(Graphics2D g) {
        if (life == null) return;

        for (int i = 0; i < player.getLives(); i++) {
            g.drawImage(life, 10 + i * 15, 25, null);
        }
    }

    private void drawTimer(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString(player.getTimeToString(), 290, 15);
    }
}
