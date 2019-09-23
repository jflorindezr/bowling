package com.test.app.processor;

import com.test.app.model.Player;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

@RunWith(MockitoJUnitRunner.class)
public class StdOutScoreBoardProcessorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void GIVEN_players_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        StdOutScoreBoardProcessor processor = new StdOutScoreBoardProcessor();
        Map<String, Player> players = constructPlayers();

        // WHEN
        processor.generateScoreBoard(players);

        // THEN
        String scoreBoard = outContent.toString();
        String [] lines = scoreBoard.split(System.getProperty("line.separator"));
        Assert.assertThat(lines.length, Matchers.equalTo(7));
        Assert.assertThat(lines[0], Matchers.equalTo("Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10"));
        Assert.assertThat(lines[1], Matchers.equalTo("Player 1"));
        Assert.assertThat(lines[2], Matchers.equalTo("Pinfalls\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t8\t0\t"));
        Assert.assertThat(lines[3], Matchers.equalTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t"));
        Assert.assertThat(lines[4], Matchers.equalTo("Player 2"));
        Assert.assertThat(lines[5], Matchers.equalTo("Pinfalls\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t3\t0\t"));
        Assert.assertThat(lines[6], Matchers.equalTo("Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t"));
    }

    private Map<String, Player> constructPlayers() throws Exception {
        Map<String, Player> players = new TreeMap<>();
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
