package com.tubes_pbo._d_platformer.model;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int frameCount;
    private int frameDelay;

    private int playCount;

    public Animation() {
playCount = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        resetAnimationState();
        frameDelay = 2;
        numFrames = frames.length;
    }

    private void resetAnimationState() {
        currentFrame = 0;
        frameCount = 0;
        playCount = 0;
    }

    public void setDelay(int delay) {
        frameDelay = delay;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public void update() {
        if (frameDelay == -1) {
            return;
        }

        frameCount++;

        if (frameCount >= frameDelay) {
            advanceFrame();
        }
    }

    private void advanceFrame() {
        currentFrame++;
        frameCount = 0;

        if (currentFrame >= numFrames) {
            currentFrame = 0;
            playCount++;
        }
    }

    public int getFrame() {
        return currentFrame;
    }

    public int getCount() {
        return frameCount;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return playCount > 0;
    }

    public boolean hasPlayed(int times) {
        return playCount == times;
    }
}
