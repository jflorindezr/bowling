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
    public void GIVEN_players_with_chances_loaded_WHEN_processing_scores_THEN_all_players_have_scores() throws Exception {
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

    @Test
    public void GIVEN_player_with_perfect_score_WHEN_processing_scores_THEN_player_1_has_total_score_300() throws Exception {
        // GIVEN
        ScoreProcessor processor = new ScoreProcessor();
        Map<String, Player> players = constructPlayerPerfectScore();

        // WHEN
        processor.calculateScores(players);

        // THEN
        Assert.assertThat(players.get("Player 1").getFrames().get(9).getScore(), Matchers.equalTo(300));
    }

    @Test
    public void GIVEN_player_with_all_faults_WHEN_processing_scores_THEN_player_1_has_total_score_0() throws Exception {
        // GIVEN
        ScoreProcessor processor = new ScoreProcessor();
        Map<String, Player> players = constructPlayerAllFails();

        // WHEN
        processor.calculateScores(players);

        // THEN
        Assert.assertThat(players.get("Player 1").getFrames().get(9).getScore(), Matchers.equalTo(0));
    }

    @Test
    public void GIVEN_player_with_all_zeros_WHEN_processing_scores_THEN_player_1_has_total_score_0() throws Exception {
        // GIVEN
        ScoreProcessor processor = new ScoreProcessor();
        Map<String, Player> players = constructPlayerAllZeros();

        // WHEN
        processor.calculateScores(players);

        // THEN
        Assert.assertThat(players.get("Player 1").getFrames().get(9).getScore(), Matchers.equalTo(0));
    }

    @Test
    public void GIVEN_players_with_normal_game_WHEN_processing_scores_THEN_player_1_has_total_score_167_second_player_has_total_score_151() throws Exception {
        // GIVEN
        ScoreProcessor processor = new ScoreProcessor();
        Map<String, Player> players = constructPlayersWithValidGame();

        // WHEN
        processor.calculateScores(players);

        // THEN
        Assert.assertThat(players.get("Player 1").getFrames().get(9).getScore(), Matchers.equalTo(167));
        Assert.assertThat(players.get("Player 2").getFrames().get(9).getScore(), Matchers.equalTo(151));
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

    private Map<String, Player> constructPlayerPerfectScore() throws Exception {
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        for (int i=1; i <= 9; i++) {
            player1.addChance(i, "10");
            player2.addChance(i, "3");
            player2.addChance(i, "0");
        }
        // Adding last frame chances
        player1.addChance(10, "10");
        player1.addChance(10, "10");
        player1.addChance(10, "10");
        player2.addChance(10, "0");

        players.put("Player 1", player1);
        players.put("Player 2", player2);

        return players;
    }

    private Map<String, Player> constructPlayerAllFails() throws Exception {
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        for (int i=1; i <= 10; i++) {
            player1.addChance(i, "F");
            player2.addChance(i, "3");
            player2.addChance(i, "0");
        }

        players.put("Player 1", player1);
        players.put("Player 2", player2);

        return players;
    }

    private Map<String, Player> constructPlayerAllZeros() throws Exception {
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        for (int i=1; i <= 10; i++) {
            player1.addChance(i, "0");
            player2.addChance(i, "3");
            player2.addChance(i, "0");
        }

        players.put("Player 1", player1);
        players.put("Player 2", player2);

        return players;
    }

    private Map<String, Player> constructPlayersWithValidGame() throws Exception {
        Map<String, Player> players = new HashMap<>();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Frame 1
        player1.addChance(1, "10");
        player2.addChance(1, "3");
        player2.addChance(1, "7");

        // Frame 2
        player1.addChance(2, "7");
        player1.addChance(2, "3");
        player2.addChance(2, "6");
        player2.addChance(2, "3");

        // Frame 3
        player1.addChance(3, "9");
        player1.addChance(3, "0");
        player2.addChance(3, "10");

        // Frame 4
        player1.addChance(4, "10");
        player2.addChance(4, "8");
        player2.addChance(4, "1");

        // Frame 5
        player1.addChance(5, "0");
        player1.addChance(5, "8");
        player2.addChance(5, "10");

        // Frame 6
        player1.addChance(6, "8");
        player1.addChance(6, "2");
        player2.addChance(6, "10");

        // Frame 7
        player1.addChance(7, "F");
        player1.addChance(7, "6");
        player2.addChance(7, "9");
        player2.addChance(7, "0");

        // Frame 8
        player1.addChance(8, "10");
        player2.addChance(8, "7");
        player2.addChance(8, "3");

        // Frame 9
        player1.addChance(9, "10");
        player2.addChance(9, "4");
        player2.addChance(9, "4");

        // Frame 10
        player1.addChance(10, "10");
        player1.addChance(10, "8");
        player1.addChance(10, "1");
        player2.addChance(10, "10");
        player2.addChance(10, "9");
        player2.addChance(10, "0");

        players.put("Player 1", player1);
        players.put("Player 2", player2);

        return players;
    }

}
