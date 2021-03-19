package com.nextplugins.testserver.core.listeners;

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
        if (Bukkit.getServer().hasWhitelist()) event.setMotd(motdWhitelist);
        else event.setMotd(motdNormal);
    }

}
