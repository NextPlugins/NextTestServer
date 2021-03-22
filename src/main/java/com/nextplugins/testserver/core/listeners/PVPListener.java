package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.manager.LocationManager;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class PVPListener implements Listener {

    @Inject private LocationManager locationManager;

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {

        val spawn = locationManager.getLocation("spawn");
        if (spawn == null) return;

        event.getPlayer().teleport(spawn);

    }

}
