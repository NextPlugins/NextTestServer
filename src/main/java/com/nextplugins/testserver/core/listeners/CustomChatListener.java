package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.configuration.MessageValue;
import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CustomChatListener implements Listener {

    @Inject private AccountStorage accountStorage;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncPlayerChatEvent event) {

        if (event.isCancelled()) return;

        val account = accountStorage.from(event.getPlayer());
        event.setFormat(ColorUtils.colored(MessageValue.get(MessageValue::chatFormat)
                .replace("@player", event.getPlayer().getName())
                .replace("@group", account.getGroup().getPrefix())
                .replace("@message", event.getMessage())
        ));

    }


}
