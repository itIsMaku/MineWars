package cz.maku.gameapi.shop;

import com.google.common.collect.Lists;
import cz.maku.gameapi.player.GamePlayer;
import cz.maku.mommons.utils.Texts;
import lombok.Data;
import me.zort.containr.*;
import me.zort.containr.component.gui.AnimatedGUI;
import me.zort.containr.internal.util.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class GameShop {

    private final Map<Integer, GameShopCategory> categories;
    @Nullable
    private final String shopDescription;

    public boolean open(GamePlayer gamePlayer) {
        if (gamePlayer.isDead()) return false;

        Player player = gamePlayer.bukkit();
        AtomicReference<GameShopCategory> selectedCategory = new AtomicReference<>(null);
        Component.gui()
                .title("Obchod")
                .rows(6)
                .prepare(gui -> {
                    player.sendMessage("updated");
                    if (shopDescription != null) {
                        gui.setElement(53, Component.element()
                                .item(
                                        Items.create(
                                                Material.BOOK,
                                                "§eInformace",
                                                Texts.createTextBlock(35, "§7" + shopDescription).toArray(new String[0])
                                        )
                                )
                                .build()
                        );
                    }

                    PagedContainer categoriesContainer = Component.pagedContainer()
                            .size(8, 1)
                            .init(container -> {
                                categories.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(entry -> {
                                    GameShopCategory category = entry.getValue();
                                    List<String> lore = Lists.newArrayList();
                                    lore.addAll(Texts.createTextBlock(35, "§7" + category.getDescription()));
                                    lore.add("");
                                    lore.add("§e§nOtevřít");
                                    container.setElement(entry.getKey(), Component.element()
                                            .item(
                                                    Items.create(
                                                            selectedCategory.get() == category ? Material.LIME_STAINED_GLASS_PANE : category.getIcon(),
                                                            selectedCategory.get() == category ? "§a" + category.getDisplayName() : "§c" + category.getDisplayName(),
                                                            lore.toArray(new String[0])
                                                    )
                                            )
                                            .click(click -> {
                                                player.sendMessage("test");
                                                player.sendMessage(category.getName());
                                            })
                                            .build()
                                    );

                                });
                            })
                            .build();

                    StaticContainer staticContainer = Component
                            .staticContainer()
                            .size(8, 1)
                            .init(container -> {
                                container.fillElement(Component.element(Items.blank(Material.BLACK_STAINED_GLASS_PANE)).build());
                            })
                            .build();

                    gui.setContainer(10, categoriesContainer);
                    gui.setContainer(19, staticContainer);
                })
                .build()
                .open(player);

        return true;
    }
}
