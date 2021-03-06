package com.test.app.model;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the plays in the frame, and an indicator if its a strike or a spare.
 */
public class Frame {

    private static final String FAULT = "F";
    private static final Integer MAX_NUMBER_OF_FRAMES = 10;
    private static final Integer MAX_HITS_IN_ROLL = 10;
    private static final Integer MAX_CHANCES_FRAME = 2;
    private static final Integer MAX_CHANCES_LAST_FRAME = 3;

    private Integer number;
    private List<String> rolls;
    private Integer score;
    private boolean isStrike;
    private boolean isSpare;

    public Frame(Integer number) {
        this.number = number;
        this.isStrike = false;
        this.isSpare = false;
        this.score = 0;
        this.rolls = new ArrayList<>();
    }

    public void addChance(String chance) throws Exception {
        if (!NumberUtils.isNumber(chance) && !FAULT.equalsIgnoreCase(chance)) {
            throw new Exception(String.format("Chance value %s not valid in frame %d.", chance, this.number));
        }

        if (this.number > MAX_NUMBER_OF_FRAMES) {
            throw new Exception(String.format("The frame is more than the max number of frames allowed."));
        }

        Integer pinsHit = getPinsHit(chance);

        // Validate if can add a new chance to the frame:
        //    if hit is negative then ERROR
        if (pinsHit < 0) {
            throw new Exception(String.format("Cannot add chance %d in frame %d because it's negative.", pinsHit, this.number));
        }

        //   if pins hit is higher than MAX_HITS_IN_ROLL
        if (pinsHit > MAX_HITS_IN_ROLL) {
            throw new Exception(String.format("Cannot add chance %d in frame %d because it's higher than the max number of possible pin hits in a roll.", pinsHit, this.number));
        }

        //   if sum of hits is higher than MAX_HITS_IN_ROLL
        if (!this.isLastFrame() && (sumAllRollsHits() + pinsHit) > MAX_HITS_IN_ROLL) {
            throw new Exception(String.format("Cannot add chance %d in frame %d because total of frame is higher than the max number of possible pin hits in a frame.", pinsHit, this.number));
        }

        //    if currentFrame == 10 and first roll was a strike then we should allow extra chances
        if (this.isFrameComplete()) {
            throw new Exception(String.format("Cannot add a new chance in frame %d because it's complete.", this.number));
        }

        this.rolls.add(chance);

        this.isStrike = MAX_HITS_IN_ROLL.equals(pinsHit);
        this.isSpare = !this.isStrike && MAX_HITS_IN_ROLL.equals(this.sumTwoHits());
    }

    private Integer getPinsHit(String chance) {
        Integer pinsHit = 0;
        if (NumberUtils.isNumber(chance)) {
            pinsHit = Integer.valueOf(chance);
        } else if (FAULT.equalsIgnoreCase(chance)) {
            pinsHit = 0;
        }

        return pinsHit;
    }

    public boolean isFrameComplete() {
        if (CollectionUtils.isEmpty(this.rolls)) {
            return false;
        }

        Integer firstChanceHits = this.getPinsHit(this.rolls.get(0));

        // If it's the last frame
        if (this.isLastFrame()) {
            // If the first chance of the last frame has the maximum hits in the roll or frame is a spare
            boolean isSpare = this.rolls.size() > 1 && MAX_HITS_IN_ROLL.equals(this.getPinsHit(this.rolls.get(0)) + this.getPinsHit(this.rolls.get(1)));
            if (MAX_HITS_IN_ROLL.equals(firstChanceHits) || isSpare) {
                return MAX_CHANCES_LAST_FRAME == this.rolls.size(); // Return true if the max number of chances is equal to MAX_CHANCES_LAST_FRAME
            }
        }
        // Otherwise, frame is complete if the first chance is MAX_HITS_IN_ROLL or the number of chances in the frame is MAX_CHANCES_FRAME
        return MAX_HITS_IN_ROLL.equals(firstChanceHits) || this.rolls.size()==MAX_CHANCES_FRAME;
    }

    public Integer sumTwoHits() {
        if (this.rolls.size() > 1) {
            return this.getPinsHit(this.rolls.get(0)) + this.getPinsHit(this.rolls.get(1));
        }
        return 0;
    }

    public Integer sumAllRollsHits() {
        return this.rolls.stream()
                .map(chance -> this.getPinsHit(chance))
                .reduce(0, Integer::sum);
    }

    public Integer getFirstChanceHits() {
        return this.getPinsHit(this.rolls.get(0));
    }

    public boolean isStrike() {
        return this.isStrike;
    }

    public boolean isSpare() {
        return this.isSpare;
    }

    public boolean isLastFrame() {
        return MAX_NUMBER_OF_FRAMES.equals(this.number);
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getRolls() {
        return rolls;
    }

    public Integer getNumber() {
        return number;
    }

    public String toString() {
        StringBuffer resp = new StringBuffer();
        resp.append(String.format("Frame %d = ", this.number));

        resp.append(this.rolls.stream()
                .map(r -> r.toString())
                .collect(Collectors.joining(", ")));

        resp.append(String.format(" (Score = %d)", this.score));

        return resp.toString();
    }

}
