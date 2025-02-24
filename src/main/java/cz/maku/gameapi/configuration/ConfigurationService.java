package cz.maku.gameapi.configuration;

import com.google.gson.stream.JsonReader;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.game.GameService;
import cz.maku.mommons.Mommons;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import cz.maku.mommons.worker.plugin.WorkerPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class ConfigurationService {

    @Load
    private GameService gameService;

    public void load(Game game) {
        WorkerPlugin plugin = game.getPlugin();
        Logger logger = Game.getLogger();
        File file = new File(plugin.getDataFolder(), "config.json");
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                file.createNewFile();
                logger.info("Configuration file created. Loading aborted to prevent errors. Please configure the game and restart the server.");
                return;
            } catch (IOException e) {
                logger.severe("Failed to create configuration file: " + e.getMessage());
                return;
            }
        }

        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            logger.severe("Failed to read configuration file: " + e.getMessage());
            return;
        }

        GameConfiguration gameConfiguration = Game.GSON.fromJson(reader, GameConfiguration.class);
        if (gameConfiguration == null) {
            logger.severe("Failed to parse configuration file. Please check if the file is valid JSON.");
            return;
        }

        if (!gameConfiguration.isEnabled()) {
            logger.severe("Game is disabled in configuration. Please enable it and restart the server.");
            return;
        }
        gameService.setGameConfiguration(gameConfiguration);
        logger.info("Configuration loaded!");
    }

}
