package cz.maku.gameapi.configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.mommons.bukkit.Bukkits;
import lombok.Data;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;

@Data
public class GameConfiguration {

    private boolean enabled;
    private int maxTeamSize;
    private int maxTeams;
    private int minPlayersStart;
    private Location lobbyLocation;
    private List<Map<Location, String>> teamLocations;

    public static GameConfiguration from(JsonGameConfiguration jsonGameConfiguration) {
        GameConfiguration gameConfiguration = new GameConfiguration();
        gameConfiguration.setEnabled(jsonGameConfiguration.isEnabled());
        gameConfiguration.setMaxTeamSize(jsonGameConfiguration.getMaxTeamSize());
        gameConfiguration.setMaxTeams(jsonGameConfiguration.getMaxTeams());
        gameConfiguration.setMinPlayersStart(jsonGameConfiguration.getMinPlayersStart());

        String location = jsonGameConfiguration.getRawLobbyLocation();
        if (location != null) {
            gameConfiguration.setLobbyLocation(Bukkits.stringToLocation(location));
        }

        List<Map<String, String>> rawLocations = jsonGameConfiguration.getRawTeamLocations();
        if (rawLocations != null) {
            List<Map<Location, String>> locations = Lists.newArrayList();
            for (Map<String, String> team : rawLocations) {
                Map<Location, String> map = Maps.newHashMap();
                for (Map.Entry<String, String> entry : team.entrySet()) {
                    map.put(Bukkits.stringToLocation(entry.getKey()), entry.getValue());
                }

                locations.add(map);
            }

            gameConfiguration.setTeamLocations(locations);
        }

        return gameConfiguration;
    }
}
