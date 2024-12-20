package com.tubes_pbo._d_platformer.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.tubes_pbo._d_platformer.audio.JukeBox;
import com.tubes_pbo._d_platformer.handlers.LoggingHelper;
import com.tubes_pbo._d_platformer.tilemap.TileMap;

public class Player extends MapObject{
    public static final int NONE_EMOTE = 0;
    public static final int CONFUSED_EMOTE = 1;
    public static final int SURPRISED_EMOTE = 2;
    private static final int[] NUMFRAMES = { 1, 8, 5, 3, 3, 5, 3, 8, 2, 1, 3 };
    private static final int[] FRAMEWIDTHS = { 40, 40, 80, 40, 40, 40, 80, 40, 40, 40, 40 };
    private static final int[] FRAMEHEIGHTS = { 40, 40, 40, 40, 40, 80, 40, 40, 40, 40, 40 };
    private static final int[] SPRITEDELAYS = { -1, 3, 2, 6, 5, 2, 2, 2, 1, -1, 1 };
    // animation actions
    private static final int IDLE_ANIM = 0;
    private static final int WALKING_ANIM = 1;
    public static final int ATTACKING_ANIM = 2;
    private static final int JUMPING_ANIM = 3;
    private static final int FALLING_ANIM = 4;
    private static final int UPATTACKING_ANIM = 5;
    private static final int CHARGING_ANIM = 6;
    private static final int DASHING_ANIM = 7;
    private static final int KNOCKBACK_ANIM = 8;
    private static final int DEAD_ANIM = 9;
    private static final int TELEPORTING_ANIM = 10;
    public final String PLAYERJUMP_MUSIC_NAME = "playerjump";
    public final String PLAYERATTACK_MUSIC_NAME = "playerattack";
    // references
    private ArrayList<Enemy> enemies;
    // player stuff
    private int lives;
    private int health;
    private int maxHealth;
    private int damage;
    private int chargeDamage;
    private boolean knockback;
    private boolean flinching;
    private long flinchCount;
    private int score;
    private boolean doubleJump;
    private boolean alreadyDoubleJump;
    private double doubleJumpStart;
    private ArrayList<EnergyParticle> energyParticles;
    private long time;
    // actions
    private boolean dashing;
    public boolean attacking;
    public boolean upattacking;
    private boolean charging;
    private int chargingTick;
    private boolean teleporting;
    // animations
    private ArrayList<BufferedImage[]> sprites;
    private Rectangle ar;
    private Rectangle aur;
    private Rectangle cr;
    // emotes
    private BufferedImage confused;
    private BufferedImage surprised;
    private int emote = NONE_EMOTE;

    public Player(TileMap tm) {

        super(tm);

        ar = new Rectangle(0, 0, 0, 0);
        ar.width = 30;
        ar.height = 20;
        aur = new Rectangle((int) x - 15, (int) y - 45, 30, 30);
        cr = new Rectangle(0, 0, 0, 0);
        cr.width = 50;
        cr.height = 40;

        width = 30;
        height = 30;
        cwidth = 15;
        cheight = 38;

        moveSpeed = 1.6;
        maxSpeed = 1.6;
        stopSpeed = 1.6;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        doubleJumpStart = -3;

        damage = 2;
        chargeDamage = 1;

        facingRight = true;

        lives = 3;
        health = maxHealth = 5;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO
                    .read(getClass().getResourceAsStream("/Sprites/Player/BatterySpirtes.gif"));

            int count = 0;
            sprites = new ArrayList<>();
            for (int i = 0; i < NUMFRAMES.length; i++) {
                BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
                for (int j = 0; j < NUMFRAMES[i]; j++) {
                    bi[j] = spritesheet.getSubimage(j * FRAMEWIDTHS[i], count, FRAMEWIDTHS[i], FRAMEHEIGHTS[i]);
                }
                sprites.add(bi);
                count += FRAMEHEIGHTS[i];
            }

            // emotes
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/Emotes.gif"));
            confused = spritesheet.getSubimage(0, 0, 14, 17);
            surprised = spritesheet.getSubimage(14, 0, 14, 17);

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }

        energyParticles = new ArrayList<>();

        setAnimation(IDLE_ANIM);

        JukeBox.load("/SFX/playerlands.mp3", "playerlands");
        JukeBox.load("/SFX/playerattack.mp3", PLAYERATTACK_MUSIC_NAME);
        JukeBox.load("/SFX/playerhit.mp3", "playerhit");
        JukeBox.load("/SFX/playercharge.mp3", "playercharge");

    }

    public void init(List<Enemy> enemies, List<EnergyParticle> energyParticles) {
        this.enemies = (ArrayList<Enemy>) enemies;
        this.energyParticles = (ArrayList<EnergyParticle>) energyParticles;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int i) {
        health = i;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setEmote(int i) {
        emote = i;
    }

    public void setTeleporting(boolean b) {
        teleporting = b;
    }

    @Override
    public void setJumping(boolean b) {
        if (knockback)
            return;
        if (b && !jumping && falling && !alreadyDoubleJump) {
            doubleJump = true;
        }
        jumping = b;
    }

    public void setAttacking() {
        if (knockback)
            return;
        if (charging)
            return;
        if (up && !attacking)
            upattacking = true;
        else
            attacking = true;
    }

    public void setCharging() {
        if (knockback)
            return;
        if (!attacking && !upattacking && !charging) {
            charging = true;
            JukeBox.play("playercharge");
            chargingTick = 0;
        }
    }

    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean b) {
        if (!b)
            dashing = false;
        else if (!falling) {
            dashing = true;
        }
    }

    public void setDead() {
        health = 0;
        stop();
    }

    public String getTimeToString() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long t) {
        time = t;
    }

    public void gainLife() {
        lives++;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int i) {
        lives = i;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    public void hit(int damage) {

    }

    public void reset() {
    }

    public void stop() {
    }


}
