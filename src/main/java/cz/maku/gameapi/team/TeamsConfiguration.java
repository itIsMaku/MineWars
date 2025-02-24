package cz.maku.gameapi.team;

import com.google.common.collect.Maps;
import cz.maku.mommons.utils.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Map;

@SuppressWarnings("all")
public final class TeamsConfiguration {

    public static final Map<Integer, Pair<ChatColor, Material>> TEAM_COLORS;
    public static final Map<ChatColor, Material> TEAM_WOOLS;

    static {
        Map<Integer, Pair<ChatColor, Material>> colors = Maps.newHashMap();
        colors.put(0, new Pair<>(ChatColor.RED, Material.RED_BANNER));
        colors.put(1, new Pair<>(ChatColor.BLUE, Material.BLUE_BANNER));
        colors.put(2, new Pair<>(ChatColor.GREEN, Material.GREEN_BANNER));
        colors.put(3, new Pair<>(ChatColor.YELLOW, Material.YELLOW_BANNER));
        colors.put(4, new Pair<>(ChatColor.AQUA, Material.LIGHT_BLUE_BANNER));
        colors.put(5, new Pair<>(ChatColor.GOLD, Material.ORANGE_BANNER));
        colors.put(6, new Pair<>(ChatColor.LIGHT_PURPLE, Material.MAGENTA_BANNER));
        colors.put(7, new Pair<>(ChatColor.DARK_PURPLE, Material.PURPLE_BANNER));
        colors.put(8, new Pair<>(ChatColor.DARK_AQUA, Material.CYAN_BANNER));
        colors.put(9, new Pair<>(ChatColor.DARK_GREEN, Material.LIME_BANNER));
        colors.put(10, new Pair<>(ChatColor.DARK_RED, Material.RED_BANNER));
        colors.put(11, new Pair<>(ChatColor.DARK_BLUE, Material.BLUE_BANNER));
        colors.put(12, new Pair<>(ChatColor.DARK_GRAY, Material.GRAY_BANNER));
        colors.put(13, new Pair<>(ChatColor.GRAY, Material.LIGHT_GRAY_BANNER));
        colors.put(14, new Pair<>(ChatColor.BLACK, Material.BLACK_BANNER));
        colors.put(15, new Pair<>(ChatColor.WHITE, Material.WHITE_BANNER));

        TEAM_COLORS = colors;

        Map<ChatColor, Material> wools = Maps.newHashMap();
        wools.put(ChatColor.RED, Material.RED_WOOL);
        wools.put(ChatColor.BLUE, Material.BLUE_WOOL);
        wools.put(ChatColor.GREEN, Material.GREEN_WOOL);
        wools.put(ChatColor.YELLOW, Material.YELLOW_WOOL);
        wools.put(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL);
        wools.put(ChatColor.GOLD, Material.ORANGE_WOOL);
        wools.put(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL);
        wools.put(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL);
        wools.put(ChatColor.DARK_AQUA, Material.CYAN_WOOL);
        wools.put(ChatColor.DARK_GREEN, Material.LIME_WOOL);
        wools.put(ChatColor.DARK_RED, Material.RED_WOOL);
        wools.put(ChatColor.DARK_BLUE, Material.BLUE_WOOL);
        wools.put(ChatColor.DARK_GRAY, Material.GRAY_WOOL);
        wools.put(ChatColor.GRAY, Material.LIGHT_GRAY_WOOL);
        wools.put(ChatColor.BLACK, Material.BLACK_WOOL);
        wools.put(ChatColor.WHITE, Material.WHITE_WOOL);

        TEAM_WOOLS = wools;
    }


}
