package com.nextplugins.testserver.core.listeners.registry;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.utils.ClassUtils;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class ListenerRegistry {

    public static void enable(NextTestServer plugin) {

        try {

            ClassPath classPath = ClassPath.from(ListenerRegistry.class.getClassLoader());

            PluginManager pluginManager = Bukkit.getPluginManager();
            for (ClassPath.ClassInfo classInfo : ClassUtils.getClasses(classPath, "listeners")) {

                Class pathClass = Class.forName(classInfo.getName());
                Object instance = pathClass.newInstance();

                if (!(instance instanceof Listener)) continue;

                val listener = (Listener) instance;
                plugin.getInjector().injectMembers(listener);

                pluginManager.registerEvents(listener, plugin);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
