package cz.maku.gameapi.map;


import com.google.common.collect.Lists;
import cz.maku.gameapi.Game;
import org.bukkit.*;
import org.bukkit.entity.Entity;

import java.io.File;
import java.util.Collection;

public final class WorldsUtils {

    public static void createEmpty(String name) {
        File folder = Bukkit.getWorldContainer();
        for (File file : folder.listFiles()) {
            if (file.getName().equalsIgnoreCase(name)) {
                Game.getLogger().severe("World with name " + name + " already exists!");
                return;
            }
        }

        WorldCreator wc = new WorldCreator(name);
        wc.generator(new VoidChunkGenerator());
        wc.type(WorldType.FLAT);
        World world = wc.createWorld();
        if (world == null) {
            Game.getLogger().severe("Failed to create world " + name + "!");
            return;
        }
        world.getSpawnLocation().add(0, 49, 0).getBlock().setType(Material.BEDROCK);
        world.setSpawnLocation(0, 51, 0);
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(6000);
    }

    public static World load(String name) {
        WorldCreator wc = new WorldCreator(name);
        wc.generator(new VoidChunkGenerator());
        wc.type(WorldType.FLAT);

        return Bukkit.createWorld(wc);
    }

    public static boolean unload(String name) {
        Bukkit.unloadWorld(name, true);
        File file = new File(Bukkit.getWorldContainer() + File.separator + name);
        if (file.exists()) {
            file.delete();
            return true;
        }

        return false;
    }

    public static boolean exists(String name) {
        return Bukkit.getWorld(name) != null;
    }

    public static World getDefault() {
        return Bukkit.getWorlds().getFirst();
    }

    public static <T extends Entity> Collection<T> getWorldEntities(String world, Class<T> clazz) {
        World worldInstance = Bukkit.getWorld(world);
        if (worldInstance == null) return Lists.newArrayList();
        return worldInstance.getEntitiesByClass(clazz);
    }

}
