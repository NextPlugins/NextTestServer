package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.player.User;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class AccountConnectionListener implements Listener {

    @Inject private UserStorage userStorage;
    @Inject private LocationManager locationManager;
    @Inject private TablistManager tablistManager;

    @EventHandler
    public void loadPlayer(PlayerJoinEvent event) {

        Bukkit.getScheduler().runTaskAsynchronously(
                NextTestServer.getInstance(),
                () -> tablistManager.sendTablist(event.getPlayer())
        );

        Location spawn = locationManager.getLocation("spawn");
        if (spawn == null) return;

        event.getPlayer().teleport(spawn);

    }

    @EventHandler
    public void unloadAttachment(PlayerQuitEvent event) {

        User user = userStorage.findAccount(event.getPlayer());
        event.getPlayer().removeAttachment(user.getAttachment());

    }

}
