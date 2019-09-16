package com.test.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the name of the player and all its plays distributed in frames.
 */
public class Player {

    private String name;
    private List<Frame> frames;

    public Player(String name) {
        this.name = name;
        this.frames = new ArrayList<>();
    }

    public void addChance(Integer currentFrame, String chance) throws Exception {
        Frame frame = calculateCurrentFrame(currentFrame);
        frame.addChance(chance);
    }

    private Frame calculateCurrentFrame(Integer currentFrame) {
        // If current frame is not in frames array yet, create it and add it to the list
        if (currentFrame > this.frames.size()) {
            this.frames.add(new Frame(currentFrame));
        }

        // Otherwise, return the frame
        return this.frames.get(currentFrame - 1);
    }

    public boolean hasFrameComplete(Integer currentFrame) {
        // If current frame is not yet in player's array of frames
        if (currentFrame > this.frames.size()) {
            return false;
        }

        return this.frames.get(currentFrame - 1).isFrameComplete();
    }

    public String getName() {
        return name;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public String toString() {
        StringBuffer resp = new StringBuffer();
        resp.append(String.format("%s:%n", this.name));

        resp.append(this.frames.stream()
                .map(f -> f.toString())
                .collect(Collectors.joining(" | ")));

        return resp.toString();
    }

}
