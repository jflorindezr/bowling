package com.test.app.processor;

import com.test.app.model.Player;

import java.io.InputStream;
import java.util.Map;

public interface IChancesFileProcessor {

    Map<String, Player> getPlayers();
    void processPlayersChances(InputStream is) throws Exception;

}
