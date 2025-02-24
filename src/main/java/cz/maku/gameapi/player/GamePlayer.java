package cz.maku.gameapi.player;

import cz.maku.gameapi.kit.GameKit;
import cz.maku.gameapi.map.GameMap;
import cz.maku.gameapi.team.GameTeam;
import cz.maku.mommons.Response;
import cz.maku.mommons.player.CloudPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class GamePlayer extends CloudPlayer {

    @Nullable
    private GameTeam team;
    @Nullable
    private GameKit kit;
    @Nullable
    private GameMap votedMap;
    private boolean dead = false;
    private boolean adminMode = false;

    public GamePlayer(CloudPlayer cloudPlayer) {
        super(cloudPlayer);
    }

    public static @Nullable GamePlayer getInstance(String nick) {
        return GamePlayersService.PLAYERS.get(nick);
    }

    public static @Nullable GamePlayer getInstance(Player player) {
        return getInstance(player.getName());
    }

    public CompletableFuture<Double> getCoins() {
        return getValueAsync("game_coins").thenApply(object -> {
            if (object == null) {
                return 0.0;
            }

            return (double) object;
        });
    }

    public CompletableFuture<Response> setCoins(int coins) {
        return setValueAsync("game_coins", coins, true);
    }

    public boolean canSelectKit(GameKit kit) {
        return bukkit().hasPermission("gameapi.kit." + kit.getName());
    }

    public boolean isAdmin() {
        return bukkit().hasPermission("gameapi.admin");
    }
}
