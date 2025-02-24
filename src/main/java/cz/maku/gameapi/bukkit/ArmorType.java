package cz.maku.gameapi.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ArmorType {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public void set(Player player, ItemStack itemStack) {
        switch (this) {
            case HELMET:
                player.getInventory().setHelmet(itemStack);
                break;
            case CHESTPLATE:
                player.getInventory().setChestplate(itemStack);
                break;
            case LEGGINGS:
                player.getInventory().setLeggings(itemStack);
                break;
            case BOOTS:
                player.getInventory().setBoots(itemStack);
                break;
            default:
                throw new IllegalArgumentException("Unknown armor type: " + this);
        }
    }
}
