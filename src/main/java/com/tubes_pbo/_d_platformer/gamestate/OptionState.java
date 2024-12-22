package com.tubes_pbo._d_platformer.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.handlers.Keys;

public class OptionState extends BasicState {

    public OptionState(GameStateManager gsm) {
        super(gsm);
        options = new String[] { "HowTo Play", "Language", "Back" };
    }

    @Override
    public void update() {
        handleInput();
    }

    @Override
    public void draw(Graphics2D i) {
        super.draw(i);
        drawMenuOptions(i);
    }

    private void drawMenuOptions(Graphics2D i) {
        i.setFont(fontMenu);
        i.setColor(Color.RED);

        int y = 223;
        for (String option : options) {
            i.drawString(option, 300, y);
            y += 25;
        }
    }

    @Override
    protected void select() {
        JukeBox.play("menuselect");
        switch (currentChoice) {
            case 0:
                gsm.setState(GameStateManager.HOWTOPLAY);
                break;
            case 1:
                gsm.setState(GameStateManager.OPTIONSSTATE);
                break;
            case 2:
                gsm.setState(GameStateManager.MENUSTATE);
                break;
            default:
                gsm.setState(GameStateManager.MENUSTATE);
                break;
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        } else if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            navigateMenu(-1);
        } else if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
            navigateMenu(1);
        }
    }

    private void navigateMenu(int direction) {
        JukeBox.play("menuoption", 0);
        currentChoice += direction;
    }
}
