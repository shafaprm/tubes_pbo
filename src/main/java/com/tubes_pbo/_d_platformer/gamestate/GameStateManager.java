package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.main.GamePanel;

public class GameStateManager {

    public static final int NUMGAMESTATES = 16;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 2;
    public static final int LEVEL2STATE = 3;
    public static final int LEVEL3STATE = 4;
    public static final int FINISHSTATE = 15;

    public BasicState[] gameStates;
    private int currentState;
    private PauseState pauseState;
    private boolean paused;

    public GameStateManager() {
        JukeBox.init();

        gameStates = new BasicState[NUMGAMESTATES];
        pauseState = new PauseState(this);
        paused = false;

        currentState = MENUSTATE;
        loadState(currentState);
    }

    public void loadState(int state) {
        switch (state) {
            case MENUSTATE -> gameStates[state] = new MenuState(this);
            case LEVEL1STATE -> gameStates[state] = new Level1State(this);
            case LEVEL2STATE -> gameStates[state] = new Level2State(this);
            case LEVEL3STATE -> gameStates[state] = new Level3State(this);
            case FINISHSTATE -> gameStates[state] = new FinishState(this);
            default -> throw new IllegalArgumentException("Invalid game state: " + state);
        }
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void update() {
        if (paused) {
            pauseState.update();
        } else if (gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        if (paused) {
            pauseState.draw(g);
        } else if (gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        } else {
            drawEmptyState(g);
        }
    }

    private void drawEmptyState(java.awt.Graphics2D g) {
        g.setColor(java.awt.Color.YELLOW);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}
