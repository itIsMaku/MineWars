package cz.maku.gameapi.kit;

import cz.maku.gameapi.bukkit.GameInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@RequiredArgsConstructor
@Getter
public class GameKit {

    private final String name;
    private final String displayName;
    private final String description;
    private final Material icon;
    private final int price;
    private final boolean temporaryPrice;
    private final GameInventory inventory;

}
