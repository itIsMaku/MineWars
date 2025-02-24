package cz.maku.gameapi.game;

import cz.maku.gameapi.Game;
import cz.maku.gameapi.player.event.GamePlayerPreloadEvent;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

@Service(listener = true)
public class WaitingBukkitService {

    @Load
    private GameService gameService;

    private boolean isWaiting() {
        return gameService.getGameState().equals(GameState.WAITING);
    }

    @BukkitEvent(PlayerJoinEvent.class)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!isWaiting()) return;

        Player player = event.getPlayer();
        player.teleport(gameService.getGameConfiguration().getLobbyLocation());

        event.setJoinMessage(null);
        int maxPlayers = gameService.getGameConfiguration().getMaxTeams() * gameService.getGameConfiguration().getMaxTeamSize();
        Game.getInstance().broadcast(String.format("Hráč §6%s§7 se připojil do hry §8(§f%s§7/§f%s§8)§7.", player.getName(), Bukkit.getOnlinePlayers().size(), maxPlayers));

        GamePlayerPreloadEvent gamePlayerPreloadEvent = new GamePlayerPreloadEvent(player);
        Bukkit.getPluginManager().callEvent(gamePlayerPreloadEvent);

        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerListHeaderFooter(String.format("§6§l%s", Game.getInstance().getDisplayName()), "§emc.wernex.eu");
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        if (gameService.isGameTeamless()) {
            player.setDisplayName("§e" + player.getName() + "§7");
            player.setPlayerListName("§e" + player.getName());
        }
    }

    @BukkitEvent(PlayerQuitEvent.class)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!isWaiting()) return;

        Player player = event.getPlayer();
        event.setQuitMessage(null);
        int maxPlayers = gameService.getGameConfiguration().getMaxTeams() * gameService.getGameConfiguration().getMaxTeamSize();
        Game.getInstance().broadcast(String.format("Hráč §6%s§7 se odpojil ze hry §8(§f%s§7/§f%s§8)§7.", player.getName(), Bukkit.getOnlinePlayers().size(), maxPlayers));
    }

    @BukkitEvent(EntityDamageEvent.class)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (!isWaiting()) return;

        event.setCancelled(true);
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause.equals(EntityDamageEvent.DamageCause.VOID)) {
            player.teleport(gameService.getGameConfiguration().getLobbyLocation());
        }
    }


    @BukkitEvent(BlockBreakEvent.class)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(BlockPlaceEvent.class)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(PlayerInteractEvent.class)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(FoodLevelChangeEvent.class)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(PlayerPickupItemEvent.class)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(PlayerDropItemEvent.class)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }

    @BukkitEvent(InventoryClickEvent.class)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!isWaiting()) return;

        event.setCancelled(true);
    }
}
