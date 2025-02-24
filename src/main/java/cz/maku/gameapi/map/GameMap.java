package cz.maku.gameapi.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.gameapi.team.GameTeam;
import cz.maku.gameapi.team.TeamsConfiguration;
import cz.maku.mommons.utils.Pair;
import lombok.Data;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cz.maku.gameapi.team.GameTeamService.TEAMS;

@Data
public abstract class GameMap {

    private final String name;
    private final String displayName;
    private final Map<String, ArmorStand> locations = Maps.newHashMap();
    private final Map<GameTeam, List<Pair<String, ArmorStand>>> teamLocations = Maps.newHashMap();

    public Collection<ArmorStand> getRawLocations() {
        if (!WorldsUtils.exists(name)) return Collections.emptyList();

        return WorldsUtils.getWorldEntities(name, ArmorStand.class);
    }

    public void addLocations(Map<String, ArmorStand> locations) {
        this.locations.putAll(locations);
    }

    public void addTeamLocations(Map<GameTeam, List<Pair<String, ArmorStand>>> teamLocations) {
        this.teamLocations.putAll(teamLocations);
    }

    public Map<GameTeam, List<Pair<String, ArmorStand>>> getSpecificTeamLocations(String locationType) {
        Collection<ArmorStand> rawLocations = getRawLocations();
        Map<GameTeam, List<Pair<String, ArmorStand>>> teamLocations = Maps.newHashMap();
        for (ArmorStand armorStand : rawLocations) {
            String name = armorStand.getCustomName();
            if (name == null) continue;

            if (!name.startsWith(locationType)) continue;

            String[] split = name.split("_");
            if (split.length != 2) continue;

            String rawTeamIndex = split[1];
            int teamIndex = Integer.parseInt(rawTeamIndex);
            Pair<ChatColor, Material> pair = TeamsConfiguration.TEAM_COLORS.get(teamIndex);
            if (pair == null) continue;

            GameTeam gameTeam = TEAMS.keySet().stream().filter(team -> team.getColor().equals(pair.getFirst())).findFirst().orElse(null);
            if (gameTeam == null) continue;

            if (!teamLocations.containsKey(gameTeam)) {
                teamLocations.put(gameTeam, Lists.newArrayList());
            }

            teamLocations.get(gameTeam).add(new Pair<>(locationType, armorStand));
        }

        return teamLocations;
    }

    public Map<GameTeam, List<Pair<String, ArmorStand>>> getTeamSpawnLocations() {
        return getSpecificTeamLocations(LocationType.SPAWN);
    }

    public Map<String, ArmorStand> getSpecificLocations(String locationType) {
        List<ArmorStand> armorStands = getRawLocations().stream().filter(armorStand -> armorStand.getCustomName() != null && armorStand.getCustomName().equals(locationType)).toList();
        Map<String, ArmorStand> locations = Maps.newHashMap();
        for (ArmorStand armorStand : armorStands) {
            locations.put(locationType, armorStand);
        }

        return locations;
    }

    public Map<String, ArmorStand> getShops() {
        return getSpecificLocations(LocationType.SHOP);
    }

    public Map<String, ArmorStand> getUpgrades() {
        return getSpecificLocations(LocationType.UPGRADES);
    }

    public abstract void prepare(World world);

    public abstract void load(World world);

    public Map<String, Location> getPreparedLocations() {
        Map<String, Location> preparedLocations = Maps.newHashMap();
        for (Map.Entry<String, ArmorStand> entry : locations.entrySet()) {
            preparedLocations.put(entry.getKey(), entry.getValue().getLocation());
        }
        return preparedLocations;
    }

    public Map<GameTeam, List<Location>> getPreparedTeamLocations() {
        Map<GameTeam, List<Location>> preparedTeamLocations = Maps.newHashMap();
        for (Map.Entry<GameTeam, List<Pair<String, ArmorStand>>> entry : teamLocations.entrySet()) {
            List<Location> locations = Lists.newArrayList();
            for (Pair<String, ArmorStand> pair : entry.getValue()) {
                locations.add(pair.getSecond().getLocation());
            }
            preparedTeamLocations.put(entry.getKey(), locations);
        }
        return preparedTeamLocations;
    }

}
