package cz.maku.minewars;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.bukkit.GameInventory;
import cz.maku.gameapi.kit.GameKit;
import cz.maku.mommons.worker.plugin.WorkerPlugin;
import lombok.Getter;
import me.zort.containr.internal.util.Items;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class MineWars extends WorkerPlugin {

    @Getter
    private static final Game game = new Game("minewars", "MineWars", "mw", Material.IRON_PICKAXE);

    @Override
    public @Nullable List<Class<?>> registerServices() {
        List<Class<?>> services = Lists.newArrayList(game.getServices());
        services.addAll(Lists.newArrayList());
        return services;
    }

    @Override
    public void preLoad() {
        game.init(this);

    }

    @Override
    public void load() {
        game.load();

        game.registerKit(new GameKit(
                "miner",
                "Miner",
                "Obdržíš na začátku hry železný krumpáč a po celou hru efekt Haste I.",
                Material.IRON_PICKAXE,
                50,
                false,
                GameInventory.from(
                        Map.of(
                                0, Items.create(Material.IRON_PICKAXE, "§eŽelezný krumpáč")
                        ),
                        Maps.newHashMap(),
                        Lists.newArrayList(
                                new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, 1)
                        )
                )
        ));
        game.registerKit(new GameKit(
                "miner2",
                "Miner2",
                "Obdržíš na začátku hry železný krumpáč a po celou hru efekt Haste I.",
                Material.IRON_PICKAXE,
                60,
                true,
                GameInventory.from(
                        Map.of(
                                0, Items.create(Material.IRON_PICKAXE, "§eŽelezný krumpáč")
                        ),
                        Maps.newHashMap(),
                        Lists.newArrayList(
                                new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, 1)
                        )
                )
        ));
    }

    @Override
    public void unload() {

    }
}