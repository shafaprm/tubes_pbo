package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.tilemap.Background;

public class Level1State extends GameState {

    public Level1State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.LEVEL2STATE);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);

        setupLevelEnvironment();
        setupEnemyConfiguration();
        setupTitle(new int[]{0, 0, 193, 36});
    }

    private void setupLevelEnvironment() {
        generateTileMap("/Maps/level1.map", 0, 140, true);
        setupGameObjects(100, 191, 3700, 131, false);
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
            EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU
        };
    }

    private int[][] createEnemyCoordinates() {
        return new int[][]{
            {1300, 100}, {1320, 100}, {1340, 100},
            {1660, 100}, {1680, 100}, {1700, 100},
            {2180, 100}, {2960, 100}, {2980, 100},
            {3000, 100}, {2400, 350}, {3750, 230}, {3780, 150}
        };
    }
}
