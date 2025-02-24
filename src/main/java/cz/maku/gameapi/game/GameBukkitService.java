package cz.maku.gameapi.game;

import cz.maku.gameapi.Game;
import cz.maku.gameapi.GameAPIConfiguration;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.gameapi.team.GameTeam;
import cz.maku.gameapi.team.GameTeamBukkitService;
import cz.maku.gameapi.team.GameTeamService;
import cz.maku.mommons.worker.annotation.BukkitCommand;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

@Service(listener = true, commands = true)
public class GameBukkitService {

    @Load
    private GameService gameService;
    @Load
    private GameTeamService gameTeamService;

    @BukkitEvent(AsyncPlayerChatEvent.class)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer == null) return;

        if (gamePlayer.isAdminMode()) {
            event.setFormat("§c§lADMIN §c%s §8» §c%s");
            return;
        }

        GameTeam team = gamePlayer.getTeam();
        if (gameService.isState(GameState.WAITING)) {
            if (team == null) {
                event.setFormat("§7%s §8» §f%s");
                return;
            }

            event.setFormat(team.getColor() + "%s §8» §f%s");
            return;
        }
    }

    @BukkitEvent(PlayerCommandSendEvent.class)
    public void onCommandSend(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer != null && gamePlayer.isAdminMode()) return;

        event.getCommands().clear();
    }

    @BukkitEvent(PlayerCommandPreprocessEvent.class)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer != null && gamePlayer.isAdminMode()) return;

        if (GameAPIConfiguration.ALLOWED_COMMANDS.stream().anyMatch(event.getMessage()::startsWith)) return;

        event.setCancelled(true);
        player.sendMessage("§cK tomuto příkazu nemáš oprávnění.");
    }

    @BukkitCommand("admin")
    public void onAdminCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer == null || !gamePlayer.isAdmin()) {
            player.sendMessage("§cK tomuto příkazu nemáš oprávnění.");
            return;
        }

        gamePlayer.setAdminMode(!gamePlayer.isAdminMode());
        player.sendMessage("§7Admin mód je nyní " + (gamePlayer.isAdminMode() ? "§aaktivní" : "§cneaktivní") + "§7.");
    }

    @BukkitCommand("shop")
    public void onShopCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        Game.getInstance().getGameShop().open(gamePlayer);
    }
}
