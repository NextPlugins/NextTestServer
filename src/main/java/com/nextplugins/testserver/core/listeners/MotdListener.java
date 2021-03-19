package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.configuration.values.MessagesValue;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class MotdListener implements Listener {

    @EventHandler
    public void onMotd(ServerListPingEvent event) {
        if (Bukkit.getServer().hasWhitelist()) event.setMotd(MessagesValue.get(MessagesValue::motdWhitelist));
        else event.setMotd(MessagesValue.get(MessagesValue::motd));
    }

}
