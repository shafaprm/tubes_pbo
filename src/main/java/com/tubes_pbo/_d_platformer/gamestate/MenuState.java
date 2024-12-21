package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.model.PlayerSave;
import com.tubes_pbo._d_platformer.handlers.Keys;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuState extends BasicState{
    public MenuState(GameStateManager gsm) {
        super(gsm);
        options = new String[] { "Play", "Options", "Quit" };
    }

    @Override
    public void draw(Graphics2D i) {

        super.draw(i);
        // titles and fonts
        i.setFont(fontMenu);
        i.setColor(Color.RED);
        i.drawString("Play", 300, 223); // 25
        i.drawString("Options", 300, 248);
        i.drawString("Quit", 300, 273);
    }

    @Override
    protected void select() {
        switch (currentChoice) {
            case 0:
                JukeBox.play("menuselect");
                PlayerSave.init();
                gsm.setState(GameStateManager.LEVEL1STATE);
                break;
            case 1:
                gsm.setState(GameStateManager.OPTIONSSTATE);
                break;
            default:
                System.exit(0);
                break;
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER))
            select();
        if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            JukeBox.play("menuoption", 0);
            currentChoice--;
        }
        if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
            JukeBox.play("menuoption", 0);
            currentChoice++;
        }
    }
}