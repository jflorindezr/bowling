package com.test.app.processor;

import com.test.app.model.Player;

import java.util.Map;

public interface IChancesFileProcessor {

    Map<String, Player> getPlayers();
    void processPlayersChances(String fileName) throws Exception;

}
