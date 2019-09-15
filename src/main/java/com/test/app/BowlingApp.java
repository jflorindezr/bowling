package com.test.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.test.app.injection.BasicModule;
import com.test.app.model.Player;
import com.test.app.processor.ChancesTextFileProcessor;
import com.test.app.processor.IChancesFileProcessor;
import com.test.app.processor.IScoreBoardProcessor;
import com.test.app.processor.IScoreProcessor;

import java.util.Map;

/**
 * Simple app that parses a file with all plays in a bowling match and prints the score board.
 *
 */
public class BowlingApp {

    private ChancesTextFileProcessor chancesTextFileProcessor;

    public static void main(String [] args) throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());

        String fileName = args[0];

        // First create the data structure
        IChancesFileProcessor chancesTextFileProcessor = injector.getInstance(IChancesFileProcessor.class);
        chancesTextFileProcessor.processPlayersChances(fileName);

        Map<String, Player> players = chancesTextFileProcessor.getPlayers();

        // Now calculate the scores of each player
        IScoreProcessor scoreProcessor = injector.getInstance(IScoreProcessor.class);
        scoreProcessor.calculateScores(players);

        // Finally print the score board
        IScoreBoardProcessor stdOutScoreBoardProcessor = injector.getInstance(IScoreBoardProcessor.class);
        stdOutScoreBoardProcessor.generateScoreBoard(players);
    }

}
