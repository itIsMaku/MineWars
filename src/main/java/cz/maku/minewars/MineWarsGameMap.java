package cz.maku.minewars;

import cz.maku.gameapi.map.GameMap;
import org.bukkit.Location;
import org.bukkit.World;

public class MineWarsGameMap extends GameMap {

    public MineWarsGameMap(String name, String displayName) {
        super(name, displayName);
    }

    @Override
    public void prepare(World world) {
        addTeamLocations(getTeamSpawnLocations());
        addTeamLocations(getSpecificTeamLocations(MineWarsLocationType.BLOCK));

        addLocations(getShops());
        addLocations(getUpgrades());
        addLocations(getSpecificLocations(MineWarsLocationType.GOLD));
    }

    @Override
    public void load(World world) {

    }


}
