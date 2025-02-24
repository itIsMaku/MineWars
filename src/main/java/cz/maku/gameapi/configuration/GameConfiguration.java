package cz.maku.gameapi.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class GameConfiguration {

    private boolean enabled;
    private int maxTeamSize;
    private int maxTeams;
    private int minPlayersStart;
    @Exclude
    private Location lobbyLocation;

}
