package com.test.app.processor;

import com.test.app.model.Frame;
import com.test.app.model.Player;
import org.apache.commons.lang.math.NumberUtils;

import java.util.List;
import java.util.Map;

/**
 * Prints the score board of all players in the standard output.
 */
public class StdOutScoreBoardProcessor implements IScoreBoardProcessor {

    private static final Integer MAX_NUMBER_OF_FRAMES = 10;
    private static final Integer MAX_HITS_IN_ROLL = 10;

    public void generateScoreBoard(Map<String, Player> players) {
        StringBuffer board = new StringBuffer();

        generateHeader(board);

        players.forEach((k, v) -> {
            // Print player name
            board.append(k).append("\n");
            generatePinfalls(board, v);
            generateScore(board, v);
        });

        System.out.println(board.toString());
    }

    private void generateHeader(StringBuffer board) {
        board.append("Frame\t\t");
        for (int i=1; i <= MAX_NUMBER_OF_FRAMES; i++) {
            board.append(i);
            if (i < MAX_NUMBER_OF_FRAMES) {
                board.append("\t\t");
            }
        }
        board.append("\n");
    }

    private void generatePinfalls(StringBuffer board, Player player) {
        board.append("Pinfalls\t");
        for (int i=0; i < MAX_NUMBER_OF_FRAMES; i++) {
            Frame frame = player.getFrames().get(i);

            if (i < MAX_NUMBER_OF_FRAMES - 1) {
                if (frame.isStrike()) {
                    board.append("\tX\t");
                } else {
                    List<String> rolls = frame.getRolls();
                    board.append(rolls.get(0)).append("\t");
                    if (frame.isSpare()) {
                        board.append("/\t");
                    } else {
                        board.append(rolls.get(1)).append("\t");
                    }
                }
            } else {
                List<String> rolls = frame.getRolls();
                Integer sum = 0;
                for (String chance : rolls) {
                    Integer hits = NumberUtils.toInt(chance);
                    if (MAX_HITS_IN_ROLL.equals(hits)) {
                        board.append("X\t");
                    } else {
                        sum += hits;

                        if (sum.equals(MAX_HITS_IN_ROLL)) {
                            board.append("/\t");
                        } else {
                            board.append(chance).append("\t");
                        }
                    }
                }
            }
        }
        board.append("\n");
    }

    private void generateScore(StringBuffer board, Player player) {
        board.append("Score\t\t");
        for (int i=0; i < MAX_NUMBER_OF_FRAMES; i++) {
            Frame frame = player.getFrames().get(i);
            board.append(frame.getScore()).append("\t\t");
        }
        board.append("\n");
    }

}
