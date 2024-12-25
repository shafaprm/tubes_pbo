package com.tubes_pbo._d_platformer.main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.logging.Level;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;

public class Game extends JFrame {
    private static final long serialVersionUID = -437004379167511593L;

    public Game() {
        super("SunnyLand ™");
        add(new GamePanel());
        setContentPane(new GamePanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Game game = new Game();
                game.setVisible(true);
            } catch (Exception e) {
                LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });
    }
}