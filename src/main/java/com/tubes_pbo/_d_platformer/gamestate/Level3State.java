package com.tubes_pbo._d_platformer.gamestate;

import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.tilemap.Background;

public class Level3State extends GameState {
    private boolean eventQuake;

    public Level3State(GameStateManager gsm) {
        super(gsm);
        init(GameStateManager.FINISHSTATE);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);
        setupLevelEnvironment();
        setupEnemyConfiguration();
        setupTitle(new int[]{0, 0, 193, 36});
    }

    private void setupLevelEnvironment() {
        temple = createBackground("/Backgrounds/temple1.gif", 0.5);
        generateTileMap("/Maps/level3.map", 140, 0, false);
        setupGameObjects(300, 131, 2850, 371, false);
        setupMusic("level2", "/Music/intro_theme.mp3", true);
    }

    private Background createBackground(String path, double scrollSpeed) {
        return new Background(path, scrollSpeed, 0);
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
            EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.ZOGU,
            EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.UFO,
            EnemyType.UFO, EnemyType.UFO, EnemyType.UFO, EnemyType.ZOGU,
            EnemyType.ZOGU, EnemyType.ZOGU, EnemyType.ZOGU
        };
    }

    private int[][] createEnemyCoordinates() {
        return new int[][]{
            {750, 100}, {900, 150}, {1320, 250}, {1570, 160},
            {1590, 160}, {2600, 370}, {2620, 370}, {2640, 370},
            {904, 130}, {1080, 270}, {1200, 270}, {1704, 300},
            {1900, 580}, {2330, 550}, {2400, 490}, {2457, 430},
            {420, 400}, {340, 400}, {300, 425}, {45, 280}
        };
    }

    @Override
    public void update() {
        super.update();
        if (shouldTriggerQuakeEvent()) {
            eventQuake = blockInput = true;
        }
        if (eventQuake) {
            handleQuakeEvent();
        }
    }

    private boolean shouldTriggerQuakeEvent() {
        return player.getx() > 2175 && !tileMap.isShaking();
    }

    private void handleQuakeEvent() {
        eventCount++;
        if (eventCount == 1) {
            triggerQuakeStart();
        } else if (eventCount == 60) {
            player.setEmote(Player.CONFUSED_EMOTE);
        } else if (eventCount == 120) {
            player.setEmote(Player.NONE_EMOTE);
        } else if (eventCount == 150) {
            tileMap.setShaking(true, 10);
        } else if (eventCount == 180) {
            player.setEmote(Player.SURPRISED_EMOTE);
        } else if (eventCount == 300) {
            endQuakeEvent();
        }
    }

    private void triggerQuakeStart() {
        player.stop();
        player.setPosition(2175, player.gety());
    }

    private void endQuakeEvent() {
        player.setEmote(Player.NONE_EMOTE);
        eventQuake = blockInput = false;
        eventCount = 0;
    }
}
