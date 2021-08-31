package com.nextplugins.testserver.core.registry;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.runnable.RunnableTaskInfo;
import com.nextplugins.testserver.core.utils.ClassUtils;
import lombok.val;
import org.bukkit.Bukkit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class TaskRegistry {

    public static void enable(NextTestServer plugin) {

        try {

            val classPath = ClassPath.from(ListenerRegistry.class.getClassLoader());
            val scheduler = Bukkit.getScheduler();
            for (val classInfo : ClassUtils.getClasses(classPath, "runnables")) {

                val pathClass = Class.forName(classInfo.getName());

                if (Runnable.class.isAssignableFrom(pathClass) && pathClass.isAnnotationPresent(RunnableTaskInfo.class)) {

                    val info = (RunnableTaskInfo) pathClass.getAnnotation(RunnableTaskInfo.class);
                    val runnable = (Runnable) pathClass.newInstance();

                    if (info.async()) scheduler.runTaskTimerAsynchronously(plugin, runnable, info.delay(), info.period());
                    else scheduler.runTaskTimer(plugin, runnable, info.delay(), info.period());
                }


            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
