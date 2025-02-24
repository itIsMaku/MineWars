package cz.maku.gameapi.team;

import com.google.common.collect.Maps;
import cz.maku.mommons.utils.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Map;

@SuppressWarnings("all")
public final class TeamsConfiguration {

    public static final Map<Integer, Pair<ChatColor, Material>> TEAM_COLORS;

    static {
        Map<Integer, Pair<ChatColor, Material>> map = Maps.newHashMap();
        map.put(0, new Pair<>(ChatColor.RED, Material.RED_BANNER));
        map.put(1, new Pair<>(ChatColor.BLUE, Material.BLUE_BANNER));
        map.put(2, new Pair<>(ChatColor.GREEN, Material.GREEN_BANNER));
        map.put(3, new Pair<>(ChatColor.YELLOW, Material.YELLOW_BANNER));
        map.put(4, new Pair<>(ChatColor.AQUA, Material.LIGHT_BLUE_BANNER));
        map.put(5, new Pair<>(ChatColor.GOLD, Material.ORANGE_BANNER));
        map.put(6, new Pair<>(ChatColor.LIGHT_PURPLE, Material.MAGENTA_BANNER));
        map.put(7, new Pair<>(ChatColor.DARK_PURPLE, Material.PURPLE_BANNER));
        map.put(8, new Pair<>(ChatColor.DARK_AQUA, Material.CYAN_BANNER));
        map.put(9, new Pair<>(ChatColor.DARK_GREEN, Material.LIME_BANNER));
        map.put(10, new Pair<>(ChatColor.DARK_RED, Material.RED_BANNER));
        map.put(11, new Pair<>(ChatColor.DARK_BLUE, Material.BLUE_BANNER));
        map.put(12, new Pair<>(ChatColor.DARK_GRAY, Material.GRAY_BANNER));
        map.put(13, new Pair<>(ChatColor.GRAY, Material.LIGHT_GRAY_BANNER));
        map.put(14, new Pair<>(ChatColor.BLACK, Material.BLACK_BANNER));
        map.put(15, new Pair<>(ChatColor.WHITE, Material.WHITE_BANNER));

        TEAM_COLORS = map;
    }

}
