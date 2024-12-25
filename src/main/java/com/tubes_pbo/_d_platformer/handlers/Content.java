package com.tubes_pbo._d_platformer.handlers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

public final class Content {

    private static final BufferedImage[][] ENERGY_PARTICLE = loadSprites("/Sprites/Player/EnergyParticle.gif", 5, 5);
    private static final BufferedImage[][] EXPLOSIONS = loadSprites("/Sprites/Enemies/ExplosionRed.gif", 30, 30);
    private static final BufferedImage[][] ZOGU = loadSprites("/Sprites/Enemies/Zogu.gif", 39, 20);
    private static final BufferedImage[][] UFO = loadSprites("/Sprites/Enemies/Ufo.gif", 30, 30);
    private static final BufferedImage[][] XHELBAT = loadSprites("/Sprites/Enemies/XhelBat.gif", 25, 25);
    private static final BufferedImage[][] RED_ENERGY = loadSprites("/Sprites/Enemies/RedEnergy.gif", 20, 20);

    private Content() {
        throw new UnsupportedOperationException("Content is a utility class and cannot be instantiated.");
    }

    private static BufferedImage[][] loadSprites(String path, int frameWidth, int frameHeight) {
        try {
            BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(path));
            int rows = spritesheet.getHeight() / frameHeight;
            int cols = spritesheet.getWidth() / frameWidth;

            BufferedImage[][] sprites = new BufferedImage[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    sprites[row][col] = spritesheet.getSubimage(
                            col * frameWidth,
                            row * frameHeight,
                            frameWidth,
                            frameHeight
                    );
                }
            }
            return sprites;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, "Failed to load spritesheet: " + path, e);
            System.exit(1);
        }
        return new BufferedImage[0][0];
    }

    public static BufferedImage[][] getEnergyParticle() {
        return ENERGY_PARTICLE;
    }

    public static BufferedImage[][] getExplosions() {
        return EXPLOSIONS;
    }

    public static BufferedImage[][] getZogu() {
        return ZOGU;
    }

    public static BufferedImage[][] getUfo() {
        return UFO;
    }

    public static BufferedImage[][] getXhelbat() {
        return XHELBAT;
    }

    public static BufferedImage[][] getRedEnergy() {
        return RED_ENERGY;
    }
}
