package com.test.app.processor;

import com.test.app.model.Player;

import java.util.Map;

public interface IScoreProcessor {

    void calculateScores(Map<String, Player> players);

}
