package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.utils.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CustomChatListener implements Listener {

    private static final String CHAT_FORMAT = "@group @player&f: &7@message";

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncPlayerChatEvent event) {
        event.setFormat(ColorUtils.colored(CHAT_FORMAT
                .replace("@player", event.getPlayer().getName())
                .replace("@group", "&a[Membro]")
                .replace("@message", event.getMessage())
        ));
    }


}
