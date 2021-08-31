package com.nextplugins.testserver.core.api.model.group.event;

import com.nextplugins.testserver.core.api.model.group.Group;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class GroupUpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public final Player player;
    public final Group group;

    public static HandlerList getHandlerList() {
        return handlers;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
}
