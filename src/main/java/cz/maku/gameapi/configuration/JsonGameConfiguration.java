package cz.maku.gameapi.configuration;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JsonGameConfiguration {

    private boolean enabled;
    private int maxTeamSize;
    private int maxTeams;
    private int minPlayersStart;
    private String rawLobbyLocation;
    private List<Map<String, String>> rawTeamLocations;

}
