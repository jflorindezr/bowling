package com.test.app.processor;

import com.test.app.model.Frame;
import com.test.app.model.Player;

import java.util.List;
import java.util.Map;

public class ScoreProcessor implements IScoreProcessor {

    public void calculateScores(Map<String, Player> players) {
        // For each player, calculate its score
        players.forEach((k, v) -> {
            this.calculatePlayerScore(v);
        });
    }

    private void calculatePlayerScore(Player player) {
        Frame previousFrame = null;
        for (Frame currentFrame : player.getFrames()) {
            Integer previousScore = (previousFrame!=null) ? previousFrame.getScore() : 0;
            currentFrame.setScore(previousScore);

            Integer individualScore = currentFrame.sumAllRollsHits();
            if (!currentFrame.isLastFrame()) {
                // If current frame is a spare or a strike then add up corresponding next frames
                if (currentFrame.isSpare()) {
                    Integer nextFrameHits = calculateIfShouldAddFromNextFrames(currentFrame, currentFrame, player.getFrames(), false);
                    currentFrame.setScore(currentFrame.getScore() + individualScore + nextFrameHits);
                } else if (currentFrame.isStrike()) {
                    Integer nextFrameHits = calculateIfShouldAddFromNextFrames(currentFrame, currentFrame, player.getFrames(), false);
                    currentFrame.setScore(currentFrame.getScore() + nextFrameHits);
                } else {
                    currentFrame.setScore(currentFrame.getScore() + individualScore);
                }
            } else {
                currentFrame.setScore(currentFrame.getScore() + currentFrame.sumAllRollsHits());
            }

            previousFrame = currentFrame;
        }
    }

    private Integer calculateIfShouldAddFromNextFrames(Frame targetFrame, Frame currentFrame, List<Frame> frames, boolean stopWhenSpare) {
        if (currentFrame.isLastFrame()) {
            return calculateWhatScoreReturnFromLastFrame(targetFrame, currentFrame, frames.get(currentFrame.getNumber()-2));
        }

        Frame nextFrame = frames.get(currentFrame.getNumber());

        if (currentFrame.isSpare()) {
            if (stopWhenSpare) {
                return currentFrame.sumAllRollsHits();
            }
            return nextFrame.isStrike() ? nextFrame.sumAllRollsHits() : nextFrame.getFirstChanceHits();
        } else if (currentFrame.isStrike()) {
            Integer calculatedHits = calculateIfShouldAddFromNextFrames(targetFrame, nextFrame, frames, true);
            return currentFrame.sumAllRollsHits() + calculatedHits;
        }

        return currentFrame.sumAllRollsHits();
    }

    private Integer calculateWhatScoreReturnFromLastFrame(Frame targetFrame, Frame currentFrame, Frame previousFrame) {
        // Only if the current frame is the consecutive of the target frame
        if (targetFrame.getNumber().equals(previousFrame.getNumber())) {
            if (currentFrame.isSpare()) {
                return currentFrame.sumHitsOfSpare();
            }
            return currentFrame.sumAllStrikesHits() + currentFrame.getFirstChanceNotStrikeHits();
        }
        return currentFrame.sumAllStrikesHits();
    }

}
