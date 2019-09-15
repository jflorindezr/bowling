package com.test.app;

import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BowlingAppIntegrationTest {

    @Test
    public void GIVEN_players_WHEN_processing_score_board_THEN_score_board_is_generated() throws Exception {
        // GIVEN
        PrintStream out = mock(PrintStream.class);
        String fileName = this.getClass().getClassLoader().getResource("hits.txt").getPath();
        System.setOut(out);

        // WHEN
        BowlingApp.main(new String [] { fileName });

        // THEN
        verify(out).println(anyString());
    }

}
