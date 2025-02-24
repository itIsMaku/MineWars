package cz.maku.minewars;

import com.google.common.collect.Maps;
import cz.maku.gameapi.shop.GameShopCategory;
import cz.maku.minewars.shop.MineWarsGameShopItem;
import me.zort.containr.internal.util.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public final class MineWarsConfiguration {

    public static final Map<Integer, GameShopCategory> SHOP_CATEGORIES;

    static {
        Map<Integer, GameShopCategory> shopCategories = Maps.newHashMap();

        shopCategories.put(0, new GameShopCategory(
                "blocks",
                "Bloky",
                "Bloky pro stavění cest, obrany a další využití.",
                Material.SANDSTONE,
                Map.of(
                        0, new MineWarsGameShopItem(
                                Material.WHITE_WOOL,
                                ItemStack.of(Material.WHITE_WOOL, 1),
                                10
                        ),
                        1, new MineWarsGameShopItem(
                                Material.SANDSTONE,
                                ItemStack.of(Material.SANDSTONE, 2),
                                15
                        ),
                        2, new MineWarsGameShopItem(
                                Material.HAY_BLOCK,
                                ItemStack.of(Material.HAY_BLOCK, 1),
                                30
                        ),
                        3, new MineWarsGameShopItem(
                                Material.IRON_BLOCK,
                                ItemStack.of(Material.IRON_BLOCK, 1),
                                35
                        ),
                        4, new MineWarsGameShopItem(
                                Material.OBSIDIAN,
                                ItemStack.of(Material.GOLD_BLOCK, 1),
                                40
                        )
                )
        ));
        shopCategories.put(1, new GameShopCategory(
           "tools",
                "Nástroje",
                "Nástroje pro těžbu, obranu i útok.",
                Material.GOLDEN_PICKAXE,
                Map.of(
                        0, new MineWarsGameShopItem(
                                Material.WOODEN_PICKAXE,
                                ItemStack.of(Material.WOODEN_PICKAXE, 1),
                                30
                        ),
                        1, new MineWarsGameShopItem(
                                Material.STONE_PICKAXE,
                                ItemStack.of(Material.STONE_PICKAXE, 1),
                                40
                        ),
                        2, new MineWarsGameShopItem(
                                Material.IRON_PICKAXE,
                                ItemStack.of(Material.IRON_PICKAXE, 1),
                                50
                        ),
                        4, new MineWarsGameShopItem(
                                Material.GOLDEN_PICKAXE,
                                ItemStack.of(Material.GOLDEN_PICKAXE, 1),
                                60
                        ),
                        3, new MineWarsGameShopItem(
                                Material.DIAMOND_PICKAXE,
                                ItemStack.of(Material.DIAMOND_PICKAXE, 1),
                                70
                        )

                )
        ));

        SHOP_CATEGORIES = shopCategories;

    }

}
