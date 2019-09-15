package com.test.app.processor;

import com.test.app.model.Frame;
import com.test.app.model.Player;

import java.util.*;

public class ScoreProcessor implements IScoreProcessor {

    private Map<String, Player> players;

    public void calculateScores(Map<String, Player> players) {
        this.players = players;

        // For each player, calculate its score
        this.players.forEach((k, v) -> {
            this.calculatePlayerScore(v);
        });
    }

    private void calculatePlayerScore(Player player) {
        Frame previousFrame = null;
        Queue<Frame> strikesSpares = new LinkedList<>();
        for (Frame currentFrame : player.getFrames()) {
            Integer individualScore = currentFrame.sumAllRollsHits();

            // Check if there is any frame in the stack and process its score
            if (!strikesSpares.isEmpty()) {
                int strikeNumber = 1;
                Frame queueFrame = null;
                while (!strikesSpares.isEmpty()) {
                    queueFrame = strikesSpares.remove();

                    // If it's a spare, only add the first hit of the current frame
                    if (queueFrame.isSpare()) {
                        queueFrame.setScore(queueFrame.getScore() + currentFrame.getFirstChanceHits());
                    } else if (queueFrame.isStrike()) {
                        // Only if current frame is not a strike we remove the frame from the queue
                        if (!currentFrame.isStrike()) {
                            strikesSpares.clear();
                        }
                        queueFrame.setScore(queueFrame.getScore() + individualScore * strikeNumber++);
                    } else {
                        System.out.println("JUST REMOVING FROM QUEUE");
                    }

                    // Update the current frame score
                    currentFrame.setScore(individualScore + queueFrame.getScore());

                    /*try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }

                if (currentFrame.isStrike() && Objects.nonNull(queueFrame)) {
                    strikesSpares.add(queueFrame);
                }

            } else {
                Integer previousScore = (previousFrame!=null) ? previousFrame.getScore() : 0;
                currentFrame.setScore(previousScore + individualScore);
            }

            // If the current frame is a strike or a spare, save it for later processing
            if (currentFrame.isStrike() || currentFrame.isSpare()) {
                strikesSpares.add(currentFrame);
            }

            previousFrame = currentFrame;
        }
    }

}
