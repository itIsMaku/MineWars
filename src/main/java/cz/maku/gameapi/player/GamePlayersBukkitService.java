package cz.maku.gameapi.player;

import cz.maku.gameapi.player.event.GamePlayerLoadEvent;
import cz.maku.gameapi.player.event.GamePlayerPreloadEvent;
import cz.maku.mommons.player.CloudPlayer;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Service(listener = true)
public class GamePlayersBukkitService {

    @Load
    private GamePlayersService gamePlayersService;

    @BukkitEvent(GamePlayerPreloadEvent.class)
    public void onPreload(GamePlayerPreloadEvent event) {
        Player player = event.getPlayer();
        CloudPlayer cloudPlayer = CloudPlayer.getInstance(player);
        if (cloudPlayer == null) {
            player.kickPlayer("§cNastala chyba při načítání tvého účtu, prosím připoj se znovu.");
            return;
        }

        GamePlayer gamePlayer = new GamePlayer(cloudPlayer);
        gamePlayersService.registerGamePlayer(gamePlayer);
        GamePlayerLoadEvent gamePlayerLoadEvent = new GamePlayerLoadEvent(player, gamePlayer);
        Bukkit.getPluginManager().callEvent(gamePlayerLoadEvent);
        if (gamePlayerLoadEvent.isCancelled()) {
            gamePlayersService.unregisterGamePlayer(gamePlayer);
            player.kickPlayer("§cPřipojení bylo zrušeno serverem.");
        }
    }

}
