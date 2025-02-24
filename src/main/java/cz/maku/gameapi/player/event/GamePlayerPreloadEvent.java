package cz.maku.gameapi.player.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class GamePlayerPreloadEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public GamePlayerPreloadEvent(@NotNull Player player) {
        super(player);
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
