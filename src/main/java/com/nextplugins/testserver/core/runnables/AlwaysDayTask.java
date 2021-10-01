package com.nextplugins.testserver.core.runnables;

import com.nextplugins.testserver.core.api.model.runnable.RunnableTaskInfo;
import org.bukkit.Bukkit;

@RunnableTaskInfo(delay = 0, period = 100, async = false)
public class AlwaysDayTask implements Runnable {

    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> world.setTime(6000L));
    }
}
