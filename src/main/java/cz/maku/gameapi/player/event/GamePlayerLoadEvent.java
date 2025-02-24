package cz.maku.gameapi.player.event;

import cz.maku.gameapi.player.GamePlayer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class GamePlayerLoadEvent extends PlayerEvent implements Cancellable {

    @Getter
    private final GamePlayer gamePlayer;
    private boolean cancelled = false;
    private static final HandlerList handlers = new HandlerList();

    public GamePlayerLoadEvent(@NotNull Player player, GamePlayer gamePlayer) {
        super(player);
        this.gamePlayer = gamePlayer;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
