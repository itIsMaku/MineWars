package cz.maku.minewars;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cz.maku.gameapi.Game;
import cz.maku.gameapi.GameBuilder;
import cz.maku.gameapi.bukkit.GameInventory;
import cz.maku.gameapi.kit.GameKit;
import cz.maku.minewars.shop.MineWarsGameShop;
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
    private static Game game;

    @Override
    public @Nullable List<Class<?>> registerServices() {
        List<Class<?>> services = Lists.newArrayList(Game.getServices());
        services.addAll(Lists.newArrayList());
        return services;
    }

    @Override
    public void preLoad() {
    }

    @Override
    public void load() {
        game = GameBuilder.make("minewars", "MineWars", "mw", Material.IRON_PICKAXE)
                .classes(GameKit.class, MineWarsGameMap.class, MineWarsGameShop.class)
                .shop(new MineWarsGameShop())
                .kits(
                        new GameKit(
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
                        ),
                        new GameKit(
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
                        )
                )
                .maps(
                        new MineWarsGameMap("ruins", "Ruins")
                )
                .build(this);

    }

    @Override
    public void unload() {

    }
}