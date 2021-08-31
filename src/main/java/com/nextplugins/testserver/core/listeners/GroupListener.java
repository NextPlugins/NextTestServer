package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.api.model.group.event.GroupUpdateEvent;
import com.nextplugins.testserver.core.runnables.TagUpdateExecutor;
import org.bukkit.event.EventHandler;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public final class GroupListener {

    @EventHandler
    public void onGroupUpdate(GroupUpdateEvent event) {
        TagUpdateExecutor.getInstance().updateTag();
    }

}
