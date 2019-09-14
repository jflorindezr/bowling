package com.test.app;

import com.test.app.model.Player;
import com.test.app.processor.ChancesTextFileProcessor;

import java.util.Map;

/**
 * Hello world!
 *
 */
public class BowlingApp {

    private ChancesTextFileProcessor chancesTextFileProcessor;

    public static void main( String [] args ) throws Exception {
        ChancesTextFileProcessor chancesTextFileProcessor = new ChancesTextFileProcessor();

        String fileName = args[0];
        chancesTextFileProcessor.processPlayersChances(fileName);

        Map<String, Player> players = chancesTextFileProcessor.getPlayers();

        players.forEach((k, v) -> {
            System.out.println(v);
        });
    }

}
