package cz.maku.gameapi.player;

import com.google.common.collect.Maps;
import cz.maku.mommons.worker.annotation.Service;

import java.util.Map;

@Service
public class GamePlayersService {

    protected static final Map<String, GamePlayer> PLAYERS = Maps.newConcurrentMap();

    public GamePlayer getGamePlayer(String nick) {
        return PLAYERS.get(nick);
    }

    public void registerGamePlayer(GamePlayer gamePlayer) {
        PLAYERS.put(gamePlayer.getNickname(), gamePlayer);
    }

    public void unregisterGamePlayer(GamePlayer gamePlayer) {
        PLAYERS.remove(gamePlayer.getNickname());
    }

}
