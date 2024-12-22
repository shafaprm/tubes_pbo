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
        resetPlayCount();
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
        resetPlayCount();
    }

    private void resetPlayCount() {
        playCount = 0;
    }

    public void setDelay(int delay) {
        frameDelay = delay;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public void update() {
        if (isDelayDisabled()) {
            return;
        }

        frameCount++;

        if (shouldAdvanceFrame()) {
            advanceFrame();
        }
    }

    private boolean isDelayDisabled() {
        return frameDelay == -1;
    }

    private boolean shouldAdvanceFrame() {
        return frameCount >= frameDelay;
    }

    private void advanceFrame() {
        currentFrame++;
        frameCount = 0;

        if (isLastFrame()) {
            resetToFirstFrame();
            incrementPlayCount();
        }
    }

    private boolean isLastFrame() {
        return currentFrame >= numFrames;
    }

    private void resetToFirstFrame() {
        currentFrame = 0;
    }

    private void incrementPlayCount() {
        playCount++;
    }

    public int getFrame() {
        return currentFrame;
    }

    public void setFrame(int frame) {
        currentFrame = frame;
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
