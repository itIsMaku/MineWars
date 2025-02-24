package cz.maku.gameapi.shop;

import lombok.Data;
import org.bukkit.Material;

import java.util.Map;

@Data
public class GameShopCategory {

    private final String name;
    private final String displayName;
    private final String description;
    private final Material icon;
    private final Map<Integer, GameShopItem> items;

}
