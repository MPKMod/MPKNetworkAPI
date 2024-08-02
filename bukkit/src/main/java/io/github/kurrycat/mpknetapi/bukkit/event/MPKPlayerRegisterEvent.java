package io.github.kurrycat.mpknetapi.bukkit.event;

import io.github.kurrycat.mpknetapi.common.MPKServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@SuppressWarnings("unused")
public class MPKPlayerRegisterEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final MPKServerPlayer player;

    public MPKPlayerRegisterEvent(MPKServerPlayer player) {
        super(Bukkit.getPlayer(player.getUuid()));
        this.player = player;
    }

    public MPKServerPlayer getMpkPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
