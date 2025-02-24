package cz.maku.minewars.shop;

import cz.maku.gameapi.player.GamePlayer;
import cz.maku.gameapi.shop.GameShopItem;
import cz.maku.mommons.utils.Texts;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MineWarsGameShopItem extends GameShopItem {

    private final int price;

    public MineWarsGameShopItem(Material icon, List<ItemStack> items, int price) {
        super(icon.name().toLowerCase(), Texts.capitalize(icon.name().toLowerCase().replace("_", "")), null, icon, items);
        this.price = price;
    }

    public MineWarsGameShopItem(Material icon, ItemStack itemStack, int price) {
        this(icon, List.of(itemStack), price);
    }

    @Override
    public boolean canBuy(GamePlayer gamePlayer) {
        Object raw = gamePlayer.getLocalValue("minecoins");
        if (raw == null) {
            gamePlayer.setLocalValue("minecoins", 0);
            return false;
        }

        int mineCoins = (int) raw;
        return mineCoins >= price;
    }

    @Override
    public boolean buy(GamePlayer gamePlayer) {
        Object raw = gamePlayer.getLocalValue("minecoins");
        if (raw == null) return false;

        int mineCoins = (int) raw;
        gamePlayer.setLocalValue("minecoins", mineCoins - price);
        return true;
    }
}
