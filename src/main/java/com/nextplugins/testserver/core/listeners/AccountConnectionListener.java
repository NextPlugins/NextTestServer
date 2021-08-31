package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.configuration.ScoreboardValue;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import com.nextplugins.testserver.core.runnables.TagUpdateExecutor;
import fr.minuskube.netherboard.Netherboard;
import lombok.val;
import org.bukkit.Bukkit;
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

        Netherboard.instance().createBoard(event.getPlayer(), ScoreboardValue.get(ScoreboardValue::title));

        Bukkit.getScheduler().runTaskAsynchronously(
                NextTestServer.getInstance(),
                () -> {
                    tablistManager.sendTablist(event.getPlayer());
                    TagUpdateExecutor.getInstance().updateTag();
                }
        );

        val spawn = locationManager.getLocation("spawn");
        if (spawn == null) return;

        spawn.getChunk().load(true);
        event.getPlayer().teleport(spawn);

    }

    @EventHandler
    public void unloadAttachment(PlayerQuitEvent event) {

        val user = userStorage.findAccount(event.getPlayer());
        if (user == null) return;

        event.getPlayer().removeAttachment(user.getAttachment());

    }

}
