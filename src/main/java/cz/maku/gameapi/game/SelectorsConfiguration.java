package cz.maku.gameapi.game;

import com.google.common.collect.Lists;
import cz.maku.mommons.bukkit.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class SelectorsConfiguration {

    public static final ItemStack TEAM_SELECT_ITEM = Items.create(Material.WHITE_BANNER, 1, 0, "§eVýběr týmu", Lists.newArrayList(), false);
    public static final ItemStack KIT_SELECT_ITEM = Items.create(Material.FEATHER, 1, 0, "§eVýběr kitu", Lists.newArrayList(), false);
    public static final ItemStack QUIT_ITEM = Items.create(Material.RED_DYE, 1, 0, "§cZpět na Hub", Lists.newArrayList(), false);
    public static final ItemStack GADGETS_ITEM = Items.create(Material.ENDER_CHEST, 1, 0, "§bGadgety", Lists.newArrayList(), false);
    public static final ItemStack MAP_VOTING_ITEM = Items.create(Material.MAP, 1, 0, "§aHlasování o mapě", Lists.newArrayList(), false);

    public static final Map<Integer, ItemStack> ITEMS = Map.of(
            0, TEAM_SELECT_ITEM,
            1, KIT_SELECT_ITEM,
            4, MAP_VOTING_ITEM,
            7, GADGETS_ITEM,
            8, QUIT_ITEM
    );

}
