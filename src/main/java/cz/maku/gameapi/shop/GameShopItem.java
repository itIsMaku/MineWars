package cz.maku.gameapi.shop;

import com.google.common.collect.Lists;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.gameapi.team.GameTeam;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public abstract class GameShopItem {

    private final String name;
    private final String displayName;
    private final String description;
    private final Material icon;
    private final List<ItemStack> items;

    public GameShopItem(String name, String displayName, String description, Material icon, List<ItemStack> items) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.items = items;
    }

    public GameShopItem(String name, String displayName, String description, ItemStack itemStack) {
        this(name, displayName, description, itemStack.getType(), Lists.newArrayList(itemStack));
    }

    public void apply(GamePlayer gamePlayer) {
        if (items.size() == 1 && items.getFirst().getType().equals(Material.WHITE_WOOL)) {
            GameTeam team = gamePlayer.getTeam();
            if (team != null) {
                items.getFirst().setType(team.getWool());
            }
        }

        gamePlayer.bukkit().getInventory().addItem(items.toArray(new ItemStack[0]));
    }

    public abstract boolean canBuy(GamePlayer gamePlayer);

    public abstract boolean buy(GamePlayer gamePlayer);
}
