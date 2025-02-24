package cz.maku.gameapi.team;

import cz.maku.mommons.utils.Texts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public class GameTeam {

    private final ChatColor color;
    private final Material icon;

    public String getDisplayName() {
        return color + Texts.capitalize(color.name().replace("_", " ").toLowerCase()) + "§r";
    }
}
