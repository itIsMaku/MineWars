package cz.maku.gameapi;

import com.google.common.collect.Lists;
import cz.maku.gameapi.configuration.ConfigurationService;
import cz.maku.gameapi.game.GameBukkitService;
import cz.maku.gameapi.game.GameService;
import cz.maku.gameapi.game.SelectorsService;
import cz.maku.gameapi.game.WaitingBukkitService;
import cz.maku.gameapi.kit.GameKitService;
import cz.maku.gameapi.player.GamePlayersBukkitService;
import cz.maku.gameapi.player.GamePlayersService;
import cz.maku.gameapi.team.GameTeamBukkitService;
import cz.maku.gameapi.team.GameTeamService;
import cz.maku.mommons.bukkit.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class GameAPIConfiguration {

    public static final List<Class<?>> SERVICES = List.of(
            ConfigurationService.class,
            GameService.class,
            WaitingBukkitService.class,
            GameTeamBukkitService.class,
            GameTeamService.class,
            GameBukkitService.class,
            SelectorsService.class,
            GameKitService.class,
            GamePlayersService.class,
            GamePlayersBukkitService.class
    );
    public static final String PREFIX = "§8[§6%s§8]§7";
    public static final List<String> ALLOWED_COMMANDS = List.of(
            "/lobby"
    );
}
