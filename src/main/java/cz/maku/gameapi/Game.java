package cz.maku.gameapi;

import cz.maku.gameapi.configuration.ConfigurationService;
import cz.maku.gameapi.kit.GameKit;
import cz.maku.gameapi.kit.GameKitService;
import cz.maku.gameapi.map.GameMap;
import cz.maku.gameapi.map.GameMapService;
import cz.maku.mommons.worker.WorkerReceiver;
import cz.maku.mommons.worker.plugin.WorkerPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.containr.Containr;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Getter
public class Game {

    @Getter
    private static Game instance;

    @Getter
    private static Logger logger;
    private final String name;
    private final String displayName;
    private final String shortcut;
    private final Material icon;
    private WorkerPlugin plugin;

    public void init(WorkerPlugin plugin) {
        instance = this;

        this.plugin = plugin;
        logger = Logger.getLogger("GameAPI");
    }

    public void load() {
        if (plugin == null) {
            throw new IllegalStateException("Plugin is not initialized!");
        }

        Containr.init(plugin);

        logger.addHandler(new GameAPILoggerHandler());
        logger.info(String.format("Loading game %s...", name));

        ConfigurationService coreService = WorkerReceiver.getService(plugin.getClass(), ConfigurationService.class);
        if (coreService == null) {
            throw new IllegalStateException("ConfigurationService is not registered!");
        }

        coreService.load(this);

    }

    public List<Class<?>> getServices() {
        return GameAPIConfiguration.SERVICES;
    }

    public String getPrefix() {
        return String.format(GameAPIConfiguration.PREFIX, displayName);
    }

    public void sendPlayerMessage(Player player, String message) {
        player.sendMessage(String.format("%s %s", getPrefix(), message));
    }

    public void broadcast(String message) {
        Bukkit.broadcastMessage(String.format("%s %s", getPrefix(), message));
    }

    @Nullable
    private GameKitService getKitService() {
        return WorkerReceiver.getService(plugin.getClass(), GameKitService.class);
    }

    public boolean registerKit(GameKit kit) {
        GameKitService kitService = getKitService();
        if (kitService == null) {
            throw new IllegalStateException("GameKitService is not registered!");
        }

        return kitService.registerKit(kit);
    }

    public boolean unregisterKit(GameKit kit) {
        GameKitService kitService = getKitService();
        if (kitService == null) {
            throw new IllegalStateException("GameKitService is not registered!");
        }

        return kitService.unregisterKit(kit);
    }

    @Nullable
    private GameMapService getMapService() {
        return WorkerReceiver.getService(plugin.getClass(), GameMapService.class);
    }

    public void registerMap(GameMap map) {
        GameMapService mapService = getMapService();
        if (mapService == null) {
            throw new IllegalStateException("GameMapService is not registered!");
        }

        mapService.registerMap(map);
    }

    public void unregisterMap(GameMap map) {
        GameMapService mapService = getMapService();
        if (mapService == null) {
            throw new IllegalStateException("GameMapService is not registered!");
        }

        mapService.unregisterMap(map);
    }
}
