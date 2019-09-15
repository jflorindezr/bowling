package com.test.app.processor;

import com.test.app.model.Player;
import org.apache.commons.lang.Validate;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChancesTextFileProcessor implements IChancesFileProcessor {

    private static final Integer TOTAL_NUMBER_PLAYERS = 2;
    private static final String ROWS_DELIMITER = "\t";
    private static final String COLUMNS_DELIMITER = " ";

    private Map<String, Player> players;

    public ChancesTextFileProcessor() {
        this.players = new HashMap<>();
    }

    public void processPlayersChances(InputStream is) throws IllegalArgumentException, FileNotFoundException, Exception {
        Scanner sc = new Scanner(is);
        sc.useDelimiter(ROWS_DELIMITER);

        Integer currentFrame = 1;
        boolean frameChanged = false;
        Player previousPlayer = null;
        while (sc.hasNext()) {
            String [] play = sc.next().split(COLUMNS_DELIMITER);
            Validate.notEmpty(play);
            Validate.isTrue(play.length == 2);

            String playerName = play[0];
            String chance = play[1];

            // If the current player is different than the previous one and that one doesn't have its frame complete
            if (Objects.nonNull(previousPlayer)
                    && !previousPlayer.getName().equals(playerName)
                    && !previousPlayer.hasFrameComplete(frameChanged ? currentFrame - 1 : currentFrame)) {
                throw new Exception(String.format(
                        "Cannot add chance of player %s because previous player %s doesn't have its frame %d complete.",
                        playerName, previousPlayer.getName(), currentFrame));
            }

            Player player = obtainPlayer(playerName);
            player.addChance(currentFrame, chance);

            // Calculate if frame is complete and should move to the next frame
            if (this.isFrameCompleteForAllPlayers(currentFrame)) {
                currentFrame++;
                frameChanged = true;
            } else {
                frameChanged = false;
            }

            previousPlayer = player;
        }
    }

    private Player obtainPlayer(String playerName) {
        if (this.players.containsKey(playerName)) {
            return this.players.get(playerName);
        }
        Player player = new Player(playerName);
        this.players.put(playerName, player);

        return player;
    }

    private boolean isFrameCompleteForAllPlayers(final Integer currentFrame) {
        boolean isFrameComplete = this.players.entrySet().stream()
                .map(e -> e.getValue().hasFrameComplete(currentFrame))
                .collect(Collectors.toList())
                .stream()
                .reduce(Boolean.TRUE, Boolean::logicalAnd);

        return TOTAL_NUMBER_PLAYERS.equals(this.players.size()) && isFrameComplete;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    protected void setPlayers(Map<String, Player> players) {
        this.players = players;
    }
}
