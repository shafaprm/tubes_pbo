package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.tilemap.Background;

public class Level2State extends GameState {

    public Level2State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.LEVEL3STATE);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);

        setupLevelEnvironment();
        setupEnemyConfiguration();
        setupTitle(new int[]{0, 0, 193, 36});
    }

    private void setupLevelEnvironment() {
        generateTileMap("/Maps/cloud.map", 0, 140, true);
        setupGameObjects(100, 161, 3700, 370, false);
        setupMusic("level1", "/Music/intro_theme.mp3", true);

        sky = createBackground("/Backgrounds/cloud.gif", 0);
        clouds = createBackground("/Backgrounds/mountain.gif", 0.1);
        mountains = createBackground("/Backgrounds/hill.gif", 0.2);
    }

    private Background createBackground(String path, double scrollSpeed) {
        return new Background(path, scrollSpeed);
    }

    private void setupEnemyConfiguration() {
        enemyTypesInLevel = createEnemyTypes();
        coords = createEnemyCoordinates();
        populateEnemies(enemyTypesInLevel, coords);
    }

    private EnemyType[] createEnemyTypes() {
        return new EnemyType[]{
            EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
            EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
            EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
            EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU
        };
    }

    private int[][] createEnemyCoordinates() {
        return new int[][]{
            {1300, 100}, {1320, 100}, {1340, 100},
            {1660, 100}, {1680, 100}, {1700, 100},
            {2177, 100}, {2960, 100}, {2980, 100},
            {3000, 100}, {2700, 320}, {3500, 100}
        };
    }
}
