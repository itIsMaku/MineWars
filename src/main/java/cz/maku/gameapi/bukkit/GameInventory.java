package cz.maku.gameapi.bukkit;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class GameInventory {

    private Map<Integer, ItemStack> items;
    private Map<ArmorType, ItemStack> armor;
    private List<PotionEffect> effects;

    public void apply(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        for (Map.Entry<ArmorType, ItemStack> entry : armor.entrySet()) {
            entry.getKey().set(player, entry.getValue());
        }

        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        player.clearActivePotionEffects();
        player.addPotionEffects(effects);
    }

    public static GameInventory from(Map<Integer, ItemStack> items, Map<ArmorType, ItemStack> armor, List<PotionEffect> effects) {
        return new GameInventory(items, armor, effects);
    }

    public static GameInventory from(Map<Integer, ItemStack> items, Map<ArmorType, ItemStack> armor) {
        return new GameInventory(items, armor, Lists.newArrayList());
    }

    public static GameInventory from(Map<Integer, ItemStack> items) {
        return new GameInventory(items, Map.of(), Lists.newArrayList());
    }

}
