package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.model.PlayerSave;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuState extends BasicState{
    public MenuState(GameStateManager gsm) {
        super(gsm);
        options = new String[] { "Play", "Quit" };
    }

    @Override
    public void draw(Graphics2D i) {
        super.draw(i);
        i.setFont(fontMenu);

        i.setColor(Color.ORANGE);
        i.drawString("Play", 300, 223);
        i.drawString("Quit", 300, 268);
    }

    @Override
    protected void select() {
        if(currentChoice == 0) {
            JukeBox.play("menuselect");
            PlayerSave.init();
            gsm.setState(GameStateManager.LEVEL1STATE);
        }else{
            System.exit(0);
        }
    }
}
