package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.tilemap.Background;

public class Level2State extends GameState{
    public Level2State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.LEVEL3STATE);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);

        generateTileMap("/Maps/cloud.map", 0, 140, true);
        setupGameObjects(100, 161, 3700, 370, false);

        setupMusic("level1", "/Music/intro_theme.mp3", true);

        sky = new Background("/Backgrounds/cloud.gif", 0);
        clouds = new Background("/Backgrounds/mountain.gif", 0.1);
        mountains = new Background("/Backgrounds/hill.gif", 0.2);

        enemyTypesInLevel = new EnemyType[] { EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU };

        coords = new int[][] { new int[] { 1300, 100 }, new int[] { 1320, 100 }, new int[] { 1340, 100 },
                new int[] { 1660, 100 }, new int[] { 1680, 100 }, new int[] { 1700, 100 }, new int[] { 2177, 100 },
                new int[] { 2960, 100 }, new int[] { 2980, 100 }, new int[] { 3000, 100 }, new int[] { 2700, 320 },
                new int[] { 3500, 100 } };

        populateEnemies(enemyTypesInLevel, coords);
        setupTitle(new int[] { 0, 0, 193, 36 });
    }
}
