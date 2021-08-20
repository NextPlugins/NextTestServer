package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.configuration.MessageValue;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class MotdListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMotd(ServerListPingEvent event) {
        if (Bukkit.getServer().hasWhitelist()) event.setMotd(MessageValue.get(MessageValue::motdWhitelist));
        else event.setMotd(MessageValue.get(MessageValue::motd));
    }

}
