package cz.maku.gameapi.team;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.game.GameService;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.mommons.bukkit.scheduler.Schedulers;
import cz.maku.mommons.utils.Pair;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.PostInitialize;
import cz.maku.mommons.worker.annotation.Service;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameTeamService {

    @Load
    private GameService gameService;

    public static final Map<GameTeam, List<String>> TEAMS = Maps.newHashMap();

    @PostInitialize
    private void postInit() {
        Schedulers.later(task -> {
            int maxTeams = gameService.getGameConfiguration().getMaxTeams();
            for (int i = 0; i < maxTeams; i++) {
                Pair<ChatColor, Material> pair = TeamsConfiguration.TEAM_COLORS.get(i);
                if (pair == null) break;

                GameTeam gameTeam = new GameTeam(pair.getFirst(), pair.getSecond());
                TEAMS.put(gameTeam, Lists.newArrayList());
                Game.getLogger().info("Registered team " + gameTeam.getDisplayName() + "§r.");
            }
        }, 20 * 2);

    }

    public boolean canJoinTeam(Player player, GameTeam gameTeam) {
        return TEAMS.get(gameTeam).size() < gameService.getGameConfiguration().getMaxTeamSize();
    }

    public boolean joinTeam(Player player, GameTeam gameTeam) {
        if (!canJoinTeam(player, gameTeam)) {
            Game.getInstance().sendPlayerMessage(player, "Tým " + gameTeam.getDisplayName() + "§7 je již plný.");
            return false;
        }

        String nick = player.getName();
        if (!leaveTeam(player, gameTeam)) return false;

        TEAMS.get(gameTeam).add(nick);
        Game.getInstance().sendPlayerMessage(player, "Připojil ses do týmu " + gameTeam.getDisplayName() + "§7.");

        player.setDisplayName(gameTeam.getColor() + player.getName() + "§7");
        player.setPlayerListName(gameTeam.getColor() + player.getName());

        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer != null) gamePlayer.setTeam(gameTeam);

        return true;
    }

    public boolean leaveTeam(Player player, GameTeam gameTeam) {
        String nick = player.getName();
        Optional<Map.Entry<GameTeam, List<String>>> previousEntry = TEAMS.entrySet().stream().filter(entry -> entry.getValue().contains(nick)).findFirst();
        if (previousEntry.isEmpty()) return true;

        GameTeam previousTeam = previousEntry.get().getKey();
        if (gameTeam != null && previousTeam == gameTeam) {
            Game.getInstance().sendPlayerMessage(player, "Již jsi v týmu " + previousTeam.getDisplayName() + "§7.");
            return false;
        }

        TEAMS.get(previousTeam).remove(nick);
        Game.getInstance().sendPlayerMessage(player, "Odpojil ses z týmu " + previousTeam.getDisplayName() + "§7.");

        GamePlayer gamePlayer = GamePlayer.getInstance(player);
        if (gamePlayer != null) gamePlayer.setTeam(null);

        return true;
    }

    public boolean leaveTeam(Player player) {
        return leaveTeam(player, null);
    }

    public GameTeam getTeam(Player player) {
        String nick = player.getName();
        Optional<Map.Entry<GameTeam, List<String>>> entry = TEAMS.entrySet().stream().filter(e -> e.getValue().contains(nick)).findFirst();
        return entry.map(Map.Entry::getKey).orElse(null);
    }

}
