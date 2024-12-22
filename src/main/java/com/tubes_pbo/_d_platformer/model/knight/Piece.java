package com.tubes_pbo._d_platformer.model.knight;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.tubes_pbo._d_platformer.model.MapObject;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class Piece extends MapObject {
    private BufferedImage[] sprites;

    public Piece(TileMap tm, int[] mapCoords) {
        super(tm);
        initializeSprites(mapCoords);
    }

    private void initializeSprites(int[] mapCoords) {
        try {
            BufferedImage spritesheet = loadSpriteSheet("/Sprites/Other/ballBatBoss.gif");
            sprites = extractSprites(spritesheet, mapCoords);
            configureAnimation();
        } catch (Exception e) {
            logError(e);
        }
    }

    private BufferedImage loadSpriteSheet(String path) throws Exception {
        return ImageIO.read(getClass().getResourceAsStream(path));
    }

    private BufferedImage[] extractSprites(BufferedImage spritesheet, int[] mapCoords) {
        BufferedImage[] extractedSprites = new BufferedImage[1];
        width = height = 4;
        extractedSprites[0] = spritesheet.getSubimage(mapCoords[0], mapCoords[1], mapCoords[2], mapCoords[3]);
        return extractedSprites;
    }

    private void configureAnimation() {
        animation.setFrames(sprites);
        animation.setDelay(-1);
    }

    private void logError(Exception e) {
        LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    public void update() {
        updatePosition();
        animation.update();
    }

    private void updatePosition() {
        x += dx;
        y += dy;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
