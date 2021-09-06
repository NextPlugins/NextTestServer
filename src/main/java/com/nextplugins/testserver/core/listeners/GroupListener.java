package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.api.model.group.event.GroupUpdateEvent;
import com.nextplugins.testserver.core.runnables.TagUpdateExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public final class GroupListener implements Listener {

    @EventHandler
    public void onGroupUpdate(GroupUpdateEvent event) {
        TagUpdateExecutor.getInstance().updateTag();
    }

}
