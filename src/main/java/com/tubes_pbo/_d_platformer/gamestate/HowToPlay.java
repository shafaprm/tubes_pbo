package com.tubes_pbo._d_platformer.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.handlers.Keys;

public class HowToPlay extends BasicState {

    public HowToPlay(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void draw(Graphics2D h) {
        super.draw(h);
        drawBackground(h);
        drawInstructionText(h);
    }

    private void drawBackground(Graphics2D h) {
        h.setFont(font);
        h.setColor(Color.YELLOW);
        h.fillRect(200, 160, 280, 200); // Fills a square
        h.setColor(Color.RED);
        h.drawRoundRect(190, 150, 300, 220, 50, 50);
    }

    private void drawInstructionText(Graphics2D h) {
        h.setColor(Color.RED);
        String[] instructions = {
                "< >      -   MOVE LEFT OR RIGHT",
                "W+R   -   JUMP AND HIT ",
                "R         -   SINGLE HIT ",
                "F         -   BIG HIT ",
                "W        -   JUMP UP ",
                "ESC   -   PAUSE "
        };

        int y = 200;
        for (String instruction : instructions) {
            h.drawString(instruction, 230, y);
            y += 20;
        }

        h.setFont(font);
        h.drawString(" * Press any key to go Back ", 240, 330);
    }

    @Override
    protected void select() {
        JukeBox.play("menuselect");
        gsm.setState(GameStateManager.OPTIONSSTATE);
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
        gsm.setState(GameStateManager.MENUSTATE);
    }
}
