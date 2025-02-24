package cz.maku.gameapi.game;

import cz.maku.gameapi.GameAPIConfiguration;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.gameapi.team.GameTeam;
import cz.maku.gameapi.team.GameTeamBukkitService;
import cz.maku.gameapi.team.GameTeamService;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

@Service(listener = true)
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
        if (player.hasPermission("gameapi.admin")) return;

        event.getCommands().clear();
    }

    @BukkitEvent(PlayerCommandPreprocessEvent.class)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("gameapi.admin")) return;

        if (GameAPIConfiguration.ALLOWED_COMMANDS.stream().anyMatch(event.getMessage()::startsWith)) return;

        event.setCancelled(true);
        player.sendMessage("§cK tomuto příkazu nemáš oprávnění.");
    }

}
