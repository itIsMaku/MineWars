package cz.maku.minewars.shop;

import cz.maku.gameapi.shop.GameShop;
import cz.maku.gameapi.shop.GameShopCategory;
import cz.maku.minewars.MineWarsConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MineWarsGameShop extends GameShop {
    public MineWarsGameShop() {
        super(MineWarsConfiguration.SHOP_CATEGORIES, "V obchodu obchoduješ s měnou mincí, které získáváš z těžení rud po mapě. Čím blíž středu jsi, tím lepší ruda a tím pádem větší i výdělek. §cPozor! §7Čím hodnotnější rudu těžíš tím déle se bude regenerovat.");
    }
}
