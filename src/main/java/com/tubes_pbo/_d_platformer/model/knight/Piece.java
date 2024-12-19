package com.tubes_pbo._d_platformer.model.knight;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.tubes_pbo._d_platformer.model.MapObject;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class Piece extends MapObject{
    private BufferedImage[] sprites;

    public Piece(TileMap tm, int[] mapCoords) {
        super(tm);
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/ballBatBoss.gif"));
            sprites = new BufferedImage[1];
            width = height = 4;
            sprites[0] = spritesheet.getSubimage(mapCoords[0], mapCoords[1], mapCoords[2], mapCoords[3]);
            animation.setFrames(sprites);
            animation.setDelay(-1);
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void update() {
        x += dx;
        y += dy;
        animation.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}