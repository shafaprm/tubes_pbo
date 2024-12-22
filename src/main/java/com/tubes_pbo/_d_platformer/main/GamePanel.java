package com.tubes_pbo._d_platformer.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import javax.swing.JPanel;
import com.tubes_pbo._d_platformer.gamestate.GameStateManager;
import com.tubes_pbo._d_platformer.handlers.Keys;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int SCALE = 1;

    private static final long serialVersionUID = 1275876853084636658L;

    // Game thread
    private transient Thread thread;
    private boolean running;
    private final int fps = 60;
    private final long targetTime = 1000 / fps;

    // Image and graphics
    private transient BufferedImage image;
    private transient Graphics2D g;

    // Game state manager
    private transient GameStateManager gsm;

    // Recording and screenshot
    private boolean recording = false;
    private int recordingCount = 0;
    private boolean screenshot;

    public GamePanel() {
        super();
        initializePanel();
    }

    private void initializePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        initializeGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    private void initializeGraphics() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
    }

    @Override
    public void run() {
        init();

        long start, elapsed, wait;

        while (running) {
            start = System.nanoTime();
            executeGameLoop();
            elapsed = System.nanoTime() - start;
            wait = Math.max(targetTime - elapsed / 1_000_000, 5);

            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void executeGameLoop() {
        update();
        draw();
        drawToScreen();
    }

    private void update() {
        gsm.update();
        Keys.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        if (g2 != null) {
            g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
            g2.dispose();
        }
        handleScreenshot();
        handleRecording();
    }

    private void handleScreenshot() {
        if (screenshot) {
            screenshot = false;
            try {
                java.io.File out = new java.io.File("screenshot_" + System.nanoTime() + ".gif");
                javax.imageio.ImageIO.write(image, "gif", out);
            } catch (Exception e) {
                LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void handleRecording() {
        if (!recording) return;
        try {
            java.io.File out = new java.io.File("C:\\out\\frame_" + recordingCount + ".gif");
            javax.imageio.ImageIO.write(image, "gif", out);
            recordingCount++;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void keyPressed(KeyEvent key) {
        handleControlKeys(key);
        Keys.keySet(key.getKeyCode(), true);
    }

    private void handleControlKeys(KeyEvent key) {
        if (key.isControlDown()) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_R -> recording = !recording;
                case KeyEvent.VK_S -> screenshot = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        Keys.keySet(key.getKeyCode(), false);
    }
}
