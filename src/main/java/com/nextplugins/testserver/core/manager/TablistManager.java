package com.nextplugins.testserver.core.manager;

import com.nextplugins.testserver.core.api.model.TabListAPI;
import com.nextplugins.testserver.core.configuration.MessageValue;
import com.nextplugins.testserver.core.utils.*;
import org.bukkit.entity.Player;

import javax.inject.Singleton;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public class TablistManager {

    public void sendTablist(Player player) {
        TabListAPI.sendTabList(player);
    }

    public void init() {
        TabListAPI.init(
                MessageUtils.joinStrings(MessageValue.get(MessageValue::tablistHeader)),
                MessageUtils.joinStrings(MessageValue.get(MessageValue::tablistFooter))
        );
    }
}
