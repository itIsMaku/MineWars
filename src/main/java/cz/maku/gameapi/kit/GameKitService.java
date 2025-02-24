package cz.maku.gameapi.kit;

import com.google.common.collect.Lists;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.mommons.Response;
import cz.maku.mommons.worker.annotation.Service;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Service
public class GameKitService {

    private final List<GameKit> kits = Lists.newArrayList();

    public boolean registerKit(GameKit kit) {
        return kits.add(kit);
    }

    public boolean unregisterKit(GameKit kit) {
        return kits.remove(kit);
    }

    public void selectKit(GamePlayer player, GameKit kit) {
        if (!kits.contains(kit)) {
            throw new IllegalArgumentException("Kit is not registered!");
        }

        Game game = Game.getInstance();
        Player bukkitPlayer = player.bukkit();
        if (player.canSelectKit(kit) && !kit.isTemporaryPrice()) {
            player.setKit(kit);
            game.sendPlayerMessage(bukkitPlayer, "§7Vybral jsi si kit §e" + kit.getDisplayName() + "§7!");
            return;
        }

        player.getCoins().thenAccept(coins -> {
            if (coins < kit.getPrice()) {
                game.sendPlayerMessage(player.bukkit(), "§7Nemáš dostatek coinů na zakoupení kitu §c" + kit.getDisplayName() + "§7!");
                return;
            }

            player.setKit(kit);
            game.sendPlayerMessage(bukkitPlayer, "§7Zakoupil jsi si kit §e" + kit.getDisplayName() + "§7 za §e" + kit.getPrice() + "§7 coinů!");

            if (!kit.isTemporaryPrice()) {
                player.setCoins((int) (coins - kit.getPrice())).thenAccept(response -> {
                    if (Response.isValid(response) && !Response.isException(response)) {
                        // TODO: add permission
                        return;
                    }

                    game.sendPlayerMessage(bukkitPlayer, "§7Nastala chyba při odebrání coinů za kit §c" + kit.getDisplayName() + "§7!");
                    Game.getLogger().severe("An error occurred while removing coins for kit " + kit.getName() + "for player " + player.getNickname() + "!");
                    Game.getLogger().severe("Response: " + response.getCode() + " - " + response.getContent());
                });
            }
        });

    }
}
