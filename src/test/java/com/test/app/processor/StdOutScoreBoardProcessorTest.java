package com.test.app.processor;

import com.test.app.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StdOutScoreBoardProcessorTest {

    @Test
    public void GIVEN_players_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        StdOutScoreBoardProcessor processor = new StdOutScoreBoardProcessor();
        Map<String, Player> players = constructPlayers();
        PrintStream out = mock(PrintStream.class);
        System.setOut(out);

        // WHEN
        processor.generateScoreBoard(players);

        // THEN
        verify(out).println(anyString());
    }

    private Map<String, Player> constructPlayers() throws Exception {
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        for (int i=1; i <= 10; i++) {
            player1.addChance(i, "8");
            player1.addChance(i, "0");
            player2.addChance(i, "3");
            player2.addChance(i, "0");
        }
        players.put("Player 1", player1);
        players.put("Player 2", player2);

        return players;
    }

}
