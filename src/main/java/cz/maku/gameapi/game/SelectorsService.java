package cz.maku.gameapi.game;

import com.google.common.collect.Lists;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.kit.GameKit;
import cz.maku.gameapi.kit.GameKitService;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.gameapi.team.GameTeam;
import cz.maku.gameapi.team.GameTeamService;
import cz.maku.mommons.utils.Texts;
import cz.maku.mommons.worker.annotation.BukkitEvent;
import cz.maku.mommons.worker.annotation.Load;
import cz.maku.mommons.worker.annotation.Service;
import me.zort.containr.Component;
import me.zort.containr.internal.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

@Service(listener = true)
public class SelectorsService {

    @Load
    private GameService gameService;
    @Load
    private GameTeamService gameTeamService;
    @Load
    private GameKitService gameKitService;

    @BukkitEvent(PlayerJoinEvent.class)
    public void onJoin(PlayerJoinEvent event) {
        if (!gameService.isState(GameState.WAITING)) return;

        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        inventory.clear();

        SelectorsConfiguration.ITEMS.forEach((slot, item) -> {
            if (item == SelectorsConfiguration.TEAM_SELECT_ITEM && gameService.isGameTeamless()) return;

            if (item == SelectorsConfiguration.KIT_SELECT_ITEM && gameKitService.getKits().isEmpty()) return;

            inventory.setItem(slot, item);
        });
    }

    @BukkitEvent(PlayerInteractEvent.class)
    public void onRightClick(PlayerInteractEvent event) {
        if (!gameService.isState(GameState.WAITING)) return;

        ItemStack item = event.getItem();
        if (item == null) return;

        Player player = event.getPlayer();
        ItemMeta meta = item.getItemMeta();
        String displayName = meta.getDisplayName();
        if (displayName.equalsIgnoreCase(SelectorsConfiguration.TEAM_SELECT_ITEM.getItemMeta().getDisplayName())) {
            Component.gui()
                    .title("Výběr týmu")
                    .rows(2)
                    .prepare(gui -> {
                        for (Map.Entry<GameTeam, List<String>> entry : gameTeamService.getPlayersTeams().entrySet()) {
                            GameTeam team = entry.getKey();
                            List<String> players = entry.getValue().stream().map(pl -> "§8- §f" + pl).toList();
                            gui.appendElement(Component.element()
                                    .item(Items.create(team.getIcon(), team.getDisplayName(), players.toArray(new String[0])))
                                    .click(click -> {
                                        Player pl = click.getPlayer();
                                        pl.closeInventory();
                                        gameTeamService.joinTeam(pl, team);
                                        ItemStack cloned = SelectorsConfiguration.TEAM_SELECT_ITEM.clone();
                                        cloned.setType(team.getIcon());
                                        pl.getInventory().setItem(0, cloned);
                                    })
                                    .build());
                        }
                    })
                    .build()
                    .open(player);
            return;
        }

        if (displayName.equalsIgnoreCase(SelectorsConfiguration.KIT_SELECT_ITEM.getItemMeta().getDisplayName())) {
            Component.gui()
                    .title("Výběr kitu")
                    .rows(1)
                    .prepare(gui -> {
                        GamePlayer gamePlayer = GamePlayer.getInstance(player);
                        if (gamePlayer == null) {
                            Game.getLogger().severe("GamePlayer is null for player " + player.getName() + "during kit selection");
                            return;
                        }

                        for (GameKit kit : gameKitService.getKits()) {
                            List<String> lore = Lists.newArrayList();

                            if (kit.isTemporaryPrice()) {
                                lore.add("§7Cena: §f" + kit.getPrice() + " §7za hru");
                            } else if (gamePlayer.canSelectKit(kit)) {
                                lore.add("§aTento kit máš zakoupený!");
                            } else {
                                lore.add("§7Cena: §f" + kit.getPrice());
                            }
                            lore.add("");
                            lore.addAll(Texts.createTextBlock(35, "§7" + kit.getDescription()));
                            lore.add("");
                            if (gamePlayer.getKit() != null && gamePlayer.getKit().equals(kit)) {
                                lore.add("§a§nVybráno");
                            } else if (gamePlayer.canSelectKit(kit) && !kit.isTemporaryPrice()) {
                                lore.add("§e§nVybrat");
                            } else {
                                lore.add("§e§nZakoupit");
                            }

                            gui.appendElement(Component.element()
                                    .item(Items.create(kit.getIcon(), "§e§l" + kit.getDisplayName(), lore.toArray(new String[0])))
                                    .click(click -> {
                                        Player pl = click.getPlayer();
                                        pl.closeInventory();

                                        gameKitService.selectKit(gamePlayer, kit);
                                    })
                                    .build());
                        }
                    })
                    .build()
                    .open(player);
        }
    }
}
