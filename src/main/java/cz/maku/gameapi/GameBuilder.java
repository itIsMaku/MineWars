package cz.maku.gameapi;

import com.google.common.collect.Lists;
import cz.maku.gameapi.kit.GameKit;
import cz.maku.gameapi.map.GameMap;
import cz.maku.gameapi.shop.GameShop;
import cz.maku.gameapi.shop.GameShopItem;
import cz.maku.mommons.worker.plugin.WorkerPlugin;
import org.bukkit.Material;

import java.util.List;

public class GameBuilder<K extends GameKit, M extends GameMap, S extends GameShop> {

    private final Game game;
    private final List<K> kits = Lists.newArrayList();
    private final List<M> maps = Lists.newArrayList();

    public GameBuilder(Game game) {
        this.game = game;
    }

    public static <K extends GameKit, M extends GameMap, S extends GameShop> GameBuilder<K, M, S> make(String name, String displayName, String shortcut, Material icon) {
        return new GameBuilder<>(new Game(name, displayName, shortcut, icon));
    }

    public <K extends GameKit, M extends GameMap, S extends GameShop> GameBuilder<K, M, S> classes(Class<K> k, Class<M> m, Class<S> s) {
        return (GameBuilder<K, M, S>) this;
    }

    public GameBuilder<K, M, S> shop(S gameShop) {
        game.setGameShop(gameShop);
        return this;
    }

    @SafeVarargs
    public final GameBuilder<K, M, S> kits(K... kits) {
        this.kits.addAll(Lists.newArrayList(kits));
        return this;
    }

    @SafeVarargs
    public final GameBuilder<K, M, S> maps(M... maps) {
        this.maps.addAll(Lists.newArrayList(maps));
        return this;
    }

    public Game build(WorkerPlugin plugin) {
        game.load(plugin);
        kits.forEach(game::registerKit);
        maps.forEach(game::registerMap);
        return game;
    }

}
