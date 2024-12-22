package com.tubes_pbo._d_platformer.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.handlers.Keys;
import com.tubes_pbo._d_platformer.main.GamePanel;

public class AcidState extends BasicState {

    public AcidState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        drawBackground(g);
        drawMessage(g);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.ORANGE);
        g.fillOval(180, 160, 260, 260);
        g.setColor(Color.WHITE);
        g.fillOval(185, 165, 250, 250);
        g.setColor(Color.YELLOW);
        g.fillOval(190, 170, 240, 240);
        g.setColor(Color.RED);
        g.fillOval(195, 175, 230, 230);
    }

    private void drawMessage(Graphics2D g) {
        g.setFont(fontMenu);
        g.setColor(Color.RED);
        g.drawString("Congratulation!", 240, 280);

        g.setFont(font);
        g.drawString("Press any key to Play Again", 215, 305);
    }

    @Override
    protected void select() {
        JukeBox.play("menuselect");
        gsm.setState(GameStateManager.MENUSTATE);
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        } else if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            handleMenuNavigation();
        }
    }

    private void handleMenuNavigation() {
        JukeBox.play("menuoption", 0);
        currentChoice--;
    }
}
