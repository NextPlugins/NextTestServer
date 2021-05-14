package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.configuration.MessageValue;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CustomChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        event.setFormat(PlaceholderAPI.setPlaceholders(
                event.getPlayer(),
                MessageValue.get(MessageValue::chatFormat).replace("@message", event.getMessage()))
        );

    }


}
