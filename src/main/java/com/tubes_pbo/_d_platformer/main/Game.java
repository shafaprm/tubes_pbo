package com.tubes_pbo._d_platformer.main;

import java.awt.EventQueue;
import java.util.logging.Level;

import javax.swing.JFrame;

import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Game extends JFrame implements CommandLineRunner {

    private static final long serialVersionUID = -437004379167511593L;

    @Override
    public void run(String... arg0) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame window = new JFrame("SunnyLand \u2122");
                    window.add(new GamePanel());
                    window.setContentPane(new GamePanel());
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setResizable(true);
                    window.pack();
                    window.setLocationRelativeTo(null);
                    window.setVisible(true);
                } catch (Exception e) {
                    LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
                }
            }
        });
    }

}