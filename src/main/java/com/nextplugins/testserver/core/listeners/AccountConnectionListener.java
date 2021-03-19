package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class AccountConnectionListener implements Listener {

    @Inject private AccountStorage accountStorage;

    @EventHandler
    public void unloadAttachment(PlayerQuitEvent event) {

        Account account = accountStorage.loadPlayer(event.getPlayer());
        event.getPlayer().removeAttachment(account.getAttachment());

    }

}
