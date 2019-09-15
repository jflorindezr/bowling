package com.test.app.processor;

import com.test.app.model.Player;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ScoreProcessorTest {

    @Test
    public void GIVEN_players_WHEN_processing_scores_THEN_all_players_have_scores() throws Exception {
        // GIVEN
        ScoreProcessor processor = new ScoreProcessor();
        Map<String, Player> players = constructPlayers();

        // WHEN
        processor.calculateScores(players);

        // THEN
        for (int i=0; i < 10; i++) {
            Assert.assertThat(players.get("Player 1").getFrames().get(i).getScore() > 0, Matchers.equalTo(true));
            Assert.assertThat(players.get("Player 2").getFrames().get(i).getScore() > 0, Matchers.equalTo(true));
        }
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
