package cz.maku.gameapi.map;

import com.google.common.collect.Maps;
import cz.maku.mommons.worker.annotation.Service;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Service
public class GameMapService {

    @Getter
    private final Map<GameMap, Integer> maps = Maps.newHashMap();

    public void registerMap(GameMap map) {
        maps.put(map, 0);
    }

    public void unregisterMap(GameMap map) {
        maps.remove(map);
    }

    public boolean voteMap(GameMap map) {
        Integer votes = maps.get(map);
        if (votes == null) {
            return false;
        }

        maps.put(map, votes + 1);
        return true;
    }

    public boolean removeVoteMap(GameMap map) {
        Integer votes = maps.get(map);
        if (votes == null) {
            return false;
        }

        maps.put(map, votes - 1);
        return true;
    }

    public Integer getVotes(GameMap map) {
        return maps.get(map);
    }

    public GameMap getVotedMap() {
        return maps.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

}
