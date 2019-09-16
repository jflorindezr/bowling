package com.test.app;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BowlingAppIntegrationTest {

    private static final String NORMAL_HITS = "hits.txt";
    private static final String NORMAL_HITS_2 = "hits_2.txt";
    private static final String ALL_FAULTS = "hits_all_F.txt";
    private static final String ALL_ZEROS = "hits_all_zeros.txt";
    private static final String MULTIPLE_PLAYERS = "hits_multiple_players.txt";
    private static final String HITS_PERFECT = "hits_perfect.txt";
    private static final String HITS_WRONG_CHANCES = "hits_wrong_chances.txt";

    private PrintStream out;

    @Before
    public void prepareMocks() {
        this.out = mock(PrintStream.class);
        System.setOut(out);
    }

    @Test
    public void GIVEN_normal_hits_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(NORMAL_HITS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test
    public void GIVEN_normal_hits_2_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(NORMAL_HITS_2).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test
    public void GIVEN_all_faults_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(ALL_FAULTS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test
    public void GIVEN_all_zeros_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(ALL_ZEROS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test
    public void GIVEN_multiple_players_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(MULTIPLE_PLAYERS).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test
    public void GIVEN_perfect_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(HITS_PERFECT).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

    @Test(expected = Exception.class)
    public void GIVEN_wrong_chances_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        String fileName = this.getClass().getClassLoader().getResource(HITS_WRONG_CHANCES).getPath();

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(this.out).println(anyString());
    }

}
