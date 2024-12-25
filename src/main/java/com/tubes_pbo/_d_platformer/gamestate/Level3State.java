package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.tilemap.Background;

public class Level3State extends GameState{
    private boolean eventQuake;

    public Level3State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.FINISHSTATE);
    }

    @Override
    public void init(int nextLevel) {

        super.init(nextLevel);
        temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);

        generateTileMap("/Maps/level3.map", 140, 0, false);

        setupGameObjects(300, 131, 2850, 371, false);
        setupTitle(new int[] { 0, 0, 193, 36 });
        setupMusic("level2", "/Music/intro_theme.mp3", true);

        enemyTypesInLevel = new EnemyType[] { EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
                EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.UFO, EnemyType.UFO,
                EnemyType.UFO, EnemyType.UFO, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU };
        coords = new int[][] { new int[] { 750, 100 }, new int[] { 900, 150 }, new int[] { 1320, 250 },
                new int[] { 1570, 160 }, new int[] { 1590, 160 }, new int[] { 2600, 370 }, new int[] { 2620, 370 },
                new int[] { 2640, 370 }, new int[] { 904, 130 }, new int[] { 1080, 270 }, new int[] { 1200, 270 },
                new int[] { 1704, 300 }, new int[] { 1900, 580 }, new int[] { 2330, 550 }, new int[] { 2400, 490 },
                new int[] { 2457, 430 }, new int[] {420, 400}, new int[] {340, 400}, new int[] {300, 425}, new int[] {45, 280} };

        populateEnemies(enemyTypesInLevel, coords);
    }

    @Override
    public void update() {
        super.update();

        if (player.getx() > 2175 && !tileMap.isShaking()) {
            eventQuake = blockInput = true;
        }

        if (eventQuake)
            eventQuake();

    }

    private void eventQuake() {
        eventCount++;
        if (eventCount == 1) {
            player.stop();
            player.setPosition(2175, player.gety());
        }
        if (eventCount == 60) {
            player.setEmote(Player.CONFUSED_EMOTE);
        }
        if (eventCount == 120)
            player.setEmote(Player.NONE_EMOTE);
        if (eventCount == 150)
            tileMap.setShaking(true, 10);
        if (eventCount == 180)
            player.setEmote(Player.SURPRISED_EMOTE);
        if (eventCount == 300) {
            player.setEmote(Player.NONE_EMOTE);
            eventQuake = blockInput = false;
            eventCount = 0;
        }
    }
}
