package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.configuration.MessageValue;
import lombok.val;
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

    @Inject private UserStorage userStorage;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChatMessage(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        val account = userStorage.findAccount(event.getPlayer());

        event.setFormat(PlaceholderAPI.setPlaceholders(
                event.getPlayer(),
                MessageValue.get(MessageValue::chatFormat)
                        .replace("@message", event.getMessage())
                        .replace("@player", event.getPlayer().getName())
                        .replace("@group", account.getGroup().getPrefix()))
        );

    }


}
