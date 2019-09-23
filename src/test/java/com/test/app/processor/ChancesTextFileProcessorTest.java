package com.test.app.processor;

import com.test.app.model.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChancesTextFileProcessorTest {

    private static final String PLAYS = "Jeff 10\tJohn 3\tJohn 7\tJeff 7\tJeff 3\tJohn 6\tJohn 3\tJeff 9\tJeff 0\tJohn 10\t" +
            "Jeff 10\tJohn 8\tJohn 1\tJeff 0\tJeff 8\tJohn 10\tJeff 8\tJeff 2\tJohn 10\tJeff F\tJeff 6\tJohn 9\tJohn 0\t" +
            "Jeff 10\tJohn 7\tJohn 3\tJeff 10\tJohn 4\tJohn 4\tJeff 10\tJeff 8\tJeff 1\tJohn 10\tJohn 9\tJohn 0";

    private static final String PLAYS_WITH_FRAME_2_NOT_COMPLETE = "Jeff 10\tJohn 3\tJohn 7\tJeff 7\tJohn 6\tJohn 3\tJeff 9\tJeff 0\tJohn 10\t" +
            "Jeff 10\tJohn 8\tJohn 1\tJeff 0\tJeff 8\tJohn 10\tJeff 8\tJeff 2\tJohn 10\tJeff F\tJeff 6\tJohn 9\tJohn 0\t" +
            "Jeff 10\tJohn 7\tJohn 3\tJeff 10\tJohn 4\tJohn 4\tJeff 10\tJeff 8\tJeff 1\tJohn 10\tJohn 9\tJohn 0";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void GIVEN_plays_WHEN_processing_chances_THEN_chances_are_added() throws Exception {
        // GIVEN
        ChancesTextFileProcessor processor = new ChancesTextFileProcessor();
        ByteArrayInputStream bais = new ByteArrayInputStream(PLAYS.getBytes());

        Map<String, Player> players = mock(Map.class);
        processor.setPlayers(players);

        Player playerJeff = mock(Player.class);
        Player playerJohn = mock(Player.class);

        // WHEN
        when(players.containsKey(anyString())).thenReturn(true);
        when(players.get("Jeff")).thenReturn(playerJeff);
        when(players.get("John")).thenReturn(playerJohn);
        Set<Map.Entry<String, Player>> playersSet = new HashSet<>();
        playersSet.add(new AbstractMap.SimpleEntry("Jeff", playerJeff));
        playersSet.add(new AbstractMap.SimpleEntry("John", playerJohn));
        when(players.entrySet()).thenReturn(playersSet);
        when(players.size()).thenReturn(2);
        when(playerJeff.getName()).thenReturn("Jeff");
        when(playerJohn.getName()).thenReturn("John");
        when(playerJeff.hasFrameComplete(any())).thenReturn(true);
        when(playerJohn.hasFrameComplete(any())).thenReturn(true);
        doNothing().when(playerJeff).addChance(anyInt(), anyString());
        doNothing().when(playerJohn).addChance(anyInt(), anyString());

        processor.processPlayersChances(bais);

        // THEN
        verify(playerJeff, times(17)).addChance(any(), anyString());
        verify(playerJohn, times(18)).addChance(any(), anyString());
    }

    @Test
    public void GIVEN_plays_with_frame_not_complete_WHEN_processing_chances_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = "Cannot add chance of player John because previous player Jeff doesn't have its frame 2 complete.";
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);
        ChancesTextFileProcessor processor = new ChancesTextFileProcessor();
        ByteArrayInputStream bais = new ByteArrayInputStream(PLAYS_WITH_FRAME_2_NOT_COMPLETE.getBytes());

        Map<String, Player> players = mock(Map.class);
        processor.setPlayers(players);

        Player playerJeff = mock(Player.class);
        Player playerJohn = mock(Player.class);

        // WHEN
        when(players.containsKey(anyString())).thenReturn(true);
        when(players.get("Jeff")).thenReturn(playerJeff);
        when(players.get("John")).thenReturn(playerJohn);
        Set<Map.Entry<String, Player>> playersSet = new HashSet<>();
        playersSet.add(new AbstractMap.SimpleEntry("Jeff", playerJeff));
        playersSet.add(new AbstractMap.SimpleEntry("John", playerJohn));
        when(players.entrySet()).thenReturn(playersSet);
        when(players.size()).thenReturn(2);
        when(playerJeff.getName()).thenReturn("Jeff");
        when(playerJohn.getName()).thenReturn("John");
        when(playerJeff.hasFrameComplete(any())).thenReturn(true);
        when(playerJeff.hasFrameComplete(eq(2))).thenReturn(false); // Frame 2 in Jeff board is not complete
        when(playerJohn.hasFrameComplete(any())).thenReturn(true);
        doNothing().when(playerJeff).addChance(anyInt(), anyString());
        doNothing().when(playerJohn).addChance(anyInt(), anyString());

        processor.processPlayersChances(bais);

        // THEN
        // Exception is thrown
    }

}
