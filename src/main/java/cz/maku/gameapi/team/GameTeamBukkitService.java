package cz.maku.gameapi.team;

import cz.maku.gameapi.player.GamePlayer;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@Service(listener = true)
public class GameTeamBukkitService {

    @Load
    private GameTeamService gameTeamService;

    @BukkitEvent(EntityDamageEvent.class)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            Entity damager = damageByEntityEvent.getDamager();
            Entity entity = damageByEntityEvent.getEntity();
            if (damager instanceof Player player && entity instanceof Player target) {
                GamePlayer gamePlayer = GamePlayer.getInstance(player);
                if (gamePlayer == null) return;

                GamePlayer gameTarget = GamePlayer.getInstance(target);
                if (gameTarget == null) return;

                GameTeam team = gamePlayer.getTeam();
                GameTeam targetTeam = gameTarget.getTeam();
                if (team == targetTeam) {
                    damageByEntityEvent.setCancelled(true);
                }
            }
        }
    }

}
