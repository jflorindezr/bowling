package com.test.app.processor;

import com.test.app.model.Player;

import java.util.Map;

public interface IScoreBoardProcessor {

    void generateScoreBoard(Map<String, Player> players);

}
