package com.nextplugins.testserver.core.registry.dao.impl;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.registry.dao.IRegistry;
import com.nextplugins.testserver.core.utils.ClassUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class ListenerRegistry implements IRegistry {

    @Override
    public void register(ClassPath classPath) throws Exception {

        PluginManager pluginManager = Bukkit.getPluginManager();
        for (ClassPath.ClassInfo classInfo : ClassUtils.getClasses(classPath, "listeners")) {

            Class pathClass = Class.forName(classInfo.getName());
            Object instance = pathClass.newInstance();

            if (instance instanceof Listener) pluginManager.registerEvents((Listener) instance, plugin);

        }

    }

}
