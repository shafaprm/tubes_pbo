package com.tubes_pbo._d_platformer.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.handlers.Keys;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.main.GamePanel;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public abstract class BasicState {
    private static final String MENU_OPTION = "menuoption";
    protected GameStateManager gsm;
    protected Player player;
    protected TileMap tileMap;
    protected boolean blockInput = false;
    protected BufferedImage bg;
    protected BufferedImage head;
    protected int currentChoice = 0;
    protected String[] options;
    protected Font font;
    protected Font font2;
    protected Font fontMenu;

    public BasicState(GameStateManager gsm) {
        this.gsm = gsm;
        try {

            bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/sfondi1.gif")).getSubimage(0, 0,
                    GamePanel.WIDTH, GamePanel.HEIGHT);

            // load floating head
            head = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif")).getSubimage(0, 12, 12, 11);

            // titles and fonts
            fontMenu = new Font("Arial", Font.BOLD, 18);
            font = new Font("Arial", Font.BOLD, 15);
            font2 = new Font("Arial", Font.PLAIN, 9);

            // load sound sfx
            JukeBox.load("/SFX/menuoption.mp3", MENU_OPTION);
            JukeBox.load("/SFX/menuselect.mp3", "menuselect");

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void update() {
        // check keys
        handleInput();
    }

    public void draw(Graphics2D g) {
        // draw bg
        g.drawImage(bg, 0, 0, null);
        // draw menu options
        g.setFont(font);
        g.setColor(Color.YELLOW);
        g.drawRoundRect(220, 170, 245, 140, 50, 50);
        g.fillRect(230, 180, 225, 120); // Fills a square
        g.setColor(Color.WHITE);
        // draw floating head
        if (currentChoice == 0)
            g.drawImage(head, 270, 213, null); // 25
        else if (currentChoice == 1)
            g.drawImage(head, 270, 238, null);
        else if (currentChoice == 2)
            g.drawImage(head, 270, 263, null);
        else if (currentChoice == 3)
            g.drawImage(head, 270, 281, null);
        else if (currentChoice == 4)
            g.drawImage(head, 270, 299, null);
        else if (currentChoice == 5)
            g.drawImage(head, 270, 317, null);
        // other
        g.setFont(font2);
        g.drawString("tonikolaba \u00A9 \u00AE", 20, 468);
    }

    protected void select() {
        throw new IllegalStateException("Needs to be overwritten");
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER))
            select();
        if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            JukeBox.play(MENU_OPTION, 0);
            currentChoice--;
        }
        if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
            JukeBox.play(MENU_OPTION, 0);
            currentChoice++;
        }
    }
}
