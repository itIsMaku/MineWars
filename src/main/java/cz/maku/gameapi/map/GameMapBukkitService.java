package cz.maku.gameapi.map;

import cz.maku.gameapi.Game;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.world.WorldLoadEvent;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

@Service(listener = true)
public class GameMapBukkitService {

    @Load
    private GameMapService gameMapService;

    public @Nullable World loadMap(GameMap map) {
        String name = map.getName();
        Game game = Game.getInstance();
        Logger logger = Game.getLogger();
        File mapsWorldFile = new File(String.format("%s%smaps%s%s", game.getPlugin().getDataFolder(), File.separator, File.separator, name));
        if (!mapsWorldFile.exists()) {
            logger.severe("World with name " + name + " does not exist!");
            return null;
        }

        File worldFile = new File(String.format("%s%s%s", Bukkit.getWorldContainer(), File.separator, name));
        if (worldFile.exists()) {
            logger.severe("World with name " + name + " already exists in worlds folder!");
            return null;
        }

        if (!mapsWorldFile.renameTo(worldFile)) {
            logger.severe("Failed to move world with name " + name + "!");
            return null;
        }

        World world = WorldsUtils.load(name);
        if (world == null) {
            logger.severe("Failed to load world with name " + name + "!");
            return null;
        }

        return world;
    }

    public boolean unloadMap(GameMap map) {
        String name = map.getName();
        return WorldsUtils.unload(name);
    }

    @BukkitEvent(WorldLoadEvent.class)
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();

    }

}
