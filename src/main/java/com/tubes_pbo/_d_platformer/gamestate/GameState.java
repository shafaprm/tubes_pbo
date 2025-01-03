package com.tubes_pbo._d_platformer.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.model.Enemy;
import com.tubes_pbo._d_platformer.model.Enemy.EnemyType;
import com.tubes_pbo._d_platformer.model.EnergyParticle;
import com.tubes_pbo._d_platformer.model.Explosion;
import com.tubes_pbo._d_platformer.model.HUD;
import com.tubes_pbo._d_platformer.model.Player;
import com.tubes_pbo._d_platformer.model.PlayerSave;
import com.tubes_pbo._d_platformer.model.Teleport;
import com.tubes_pbo._d_platformer.model.Title;
import com.tubes_pbo._d_platformer.model.enemies.RedEnergy;
import com.tubes_pbo._d_platformer.model.enemies.Ufo;
import com.tubes_pbo._d_platformer.model.enemies.XhelBat;
import com.tubes_pbo._d_platformer.model.enemies.Zogu;

import com.tubes_pbo._d_platformer.handlers.Keys;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.tilemap.Background;
import com.tubes_pbo._d_platformer.tilemap.TileMap;
import com.tubes_pbo._d_platformer.main.GamePanel;

public class GameState extends BasicState{
    private static final String TELEPORT_MUSIC_NAME = "teleport";
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Explosion> explosions;
    protected HUD hud;
    protected BufferedImage SunnyLandStart;
    protected Title title;
    protected Teleport teleport;
    protected int eventCount = 0;
    protected boolean eventStart;
    protected ArrayList<Rectangle> tb;
    protected boolean eventFinish;
    protected boolean eventDead;
    protected EnemyType[] enemyTypesInLevel;
    protected int[][] coords;
    protected Background cloud;
    protected Background mountain;
    protected Background hill;
    protected Background temple;
    protected int playerXStart;
    protected int playerYStart;
    protected int nextLevelState;
    protected String levelMusicName;

    public GameState(GameStateManager gsm) {
        super(gsm);
    }

    public void init(int nextLevel) {
        nextLevelState = nextLevel;
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE))
            gsm.setPaused(true);
        if (blockInput || player.getHealth() == 0)
            return;
        player.setUp(Keys.getKeyState()[Keys.UP]);
        player.setLeft(Keys.getKeyState()[Keys.LEFT]);
        player.setDown(Keys.getKeyState()[Keys.DOWN]);
        player.setRight(Keys.getKeyState()[Keys.RIGHT]);
        player.setJumping(Keys.getKeyState()[Keys.BUTTON1]);
        player.setDashing(Keys.getKeyState()[Keys.BUTTON2]);
        if (Keys.isPressed(Keys.BUTTON3))
            player.setAttacking();
        if (Keys.isPressed(Keys.BUTTON4))
            player.setCharging();
    }

    protected void handleObjects(TileMap tileMap, List<Enemy> enemies, List<Explosion> explosions) {

        // update enemies
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemiesToRemove.add(e);
                explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
            }
        }

        for (Enemy enemy : enemiesToRemove) {
            enemies.remove(enemy);
        }

        // update explosions
        ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosionsToRemove.add(explosions.get(i));
            }
        }

        for (Explosion explosion : explosionsToRemove) {
            explosions.remove(explosion);
        }
    }

    protected void generateTileMap(String map, int x, int y, boolean bounds) {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/ruinstileset.gif");
        tileMap.loadMap(map);
        tileMap.setPosition(x, y);
        if (bounds)
            tileMap.setBounds(tileMap.getWidth() - 1 * tileMap.getTileSize(),
                    tileMap.getHeight() - 2 * tileMap.getTileSize(), 0, 0);
        tileMap.setTween(1);
    }

    protected void setupGameObjects(int playerX, int playerY, int goalX, int goalY, boolean portal) {
        // player
        playerXStart = playerX;
        playerYStart = playerY;
        player = new Player(tileMap);
        player.setPosition(playerX, playerY);
        player.setHealth(PlayerSave.getHealth());
        player.setLives(PlayerSave.getLives());
        player.setTime(PlayerSave.getTime());

        // enemies
        enemies = new ArrayList<>();

        // energy particle
        ArrayList<EnergyParticle> energyParticles;
        energyParticles = new ArrayList<>();

        // init player
        player.init(enemies, energyParticles);

        // explosions
        explosions = new ArrayList<>();

        // hud
        hud = new HUD(player);

        // teleport
        if (!portal) {
            teleport = new Teleport(tileMap);
            teleport.setPosition(goalX, goalY);
        }

        // start event
        eventStart = true;
        tb = new ArrayList<>();
        eventStartFunc();
    }

    protected void setupMusic(String level, String bgMusic, boolean loop) {
        // sfx
        levelMusicName = level;
        JukeBox.load("/SFX/teleport.mp3", TELEPORT_MUSIC_NAME);
        JukeBox.load("/SFX/explode.mp3", "explode");
        JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

        // music
        JukeBox.load(bgMusic, level);
        if (loop)
            JukeBox.loop(level, 600, JukeBox.getFrames(level) - 2200);
    }

    protected void setupTitle(int[] titleCoords) {
        try {
            SunnyLandStart = ImageIO.read(getClass().getResourceAsStream("/HUD/title-screen.png"));
            this.title = new Title(
                    SunnyLandStart.getSubimage(titleCoords[0], titleCoords[1], titleCoords[2], titleCoords[3]));
            this.title.sety(60);
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    protected void populateEnemies(EnemyType[] enemies, int[][] coords) {
        this.enemies.clear();
        for (int i = 0; i < enemies.length; i++) {
            Enemy e = null;
            switch (enemies[i]) {
                case RED_ENERGY:
                    e = new RedEnergy(this.tileMap);
                    break;
                case UFO:
                    e = new Ufo(this.tileMap, this.player, this.enemies);
                    break;
                case XHELBAT:
                    e = new XhelBat(this.tileMap, this.player);
                    break;
                default:
                    e = new Zogu(this.tileMap);
                    break;
            }

            e.setPosition(coords[i][0], coords[i][1]);
            this.enemies.add(e);
        }
    }

    @Override
    public void update() {

        // check keys
        handleInput();

        // check if end of level event should start
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }

        // check if player dead event should start
        if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = blockInput = true;
        }

        // play events
        if (eventStart)
            eventStartFunc();
        if (eventDead)
            eventDeadFunc();
        if (eventFinish)
            eventFinishFunc();

        // move title and subtitle
        if (title != null) {
            title.update();
        }

        // move backgrounds
        if (cloud != null)
            cloud.setPosition(tileMap.getx(), tileMap.gety());
        if (mountain != null)
            mountain.setPosition(tileMap.getx(), tileMap.gety());
        if (hill != null)
            hill.setPosition(tileMap.getx(), tileMap.gety());
        if (temple != null)
            temple.setPosition(tileMap.getx(), tileMap.gety());

        // update player
        player.update();

        // update tilemap
        tileMap.setPosition(GamePanel.WIDTH / 2.0 - player.getx(), GamePanel.HEIGHT / 2.0 - player.gety());
        tileMap.update();
        tileMap.fixBounds();

        handleObjects(tileMap, enemies, explosions);

        // update teleport
        if (teleport != null)
            teleport.update();

    }

    @Override
    public void draw(Graphics2D g) {
        // draw background
        if (cloud != null)
            cloud.draw(g);
        if (mountain != null)
            mountain.draw(g);
        if (hill != null)
            hill.draw(g);
        if (temple != null)
            temple.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        // draw player
        player.draw(g);

        // draw teleport
        if (teleport != null)
            teleport.draw(g);

        // draw hud
        hud.draw(g);

        // draw title
        if (title != null)
            title.draw(g);

        // draw transition boxes
        g.setColor(java.awt.Color.YELLOW);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }
    }

    // reset level
    protected void reset() {
        player.reset();
        player.setPosition(playerXStart, playerYStart);
        populateEnemies(enemyTypesInLevel, coords);
        blockInput = true;
        eventCount = 0;
        tileMap.setShaking(false, 0);
        eventStart = true;
        eventStartFunc();
        if (title != null)
            title.reset();
    }

    protected void eventStartFunc() {
        LoggingHelper.LOGGER.log(Level.INFO, "Event Start: eventCount = " + eventCount);
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
        if (eventCount == 30 && title != null)
            title.begin();
        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;

            tb.clear();
        }
    }

    protected void eventDeadFunc() {
        LoggingHelper.LOGGER.log(Level.INFO, "Event Dead: eventCount = " + eventCount);
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getLives() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    protected void eventFinishFunc() {
        LoggingHelper.LOGGER.log(Level.INFO, "Event Finish: eventCount = " + eventCount);
        JukeBox.stop(levelMusicName);
        eventCount++;
        if (eventCount == 1) {
            JukeBox.play(TELEPORT_MUSIC_NAME);
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
            JukeBox.stop(TELEPORT_MUSIC_NAME);
        }
        if (eventCount == 180) {
            PlayerSave.setHealth(player.getHealth());
            PlayerSave.setLives(player.getLives());
            PlayerSave.setTime(player.getTime());
            gsm.setState(nextLevelState);
        }

    }
}
