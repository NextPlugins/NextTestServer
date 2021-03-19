package com.nextplugins.testserver.core.registry;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.registry.dao.IRegistry;
import org.bukkit.plugin.Plugin;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

public class AutomaticRegistry {
    public static void enable(Plugin plugin) {

        try {

            ClassPath classPath = ClassPath.from(AutomaticRegistry.class.getClassLoader());

            String packageName = "com.nextplugins.testserver.core.registry.dao.impl";
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {

                Class pathClass = Class.forName(classInfo.getName());
                if (!IRegistry.class.isAssignableFrom(pathClass)) continue;

                IRegistry registry = (IRegistry) pathClass.newInstance();
                registry.register(classPath);

            }

        } catch (Exception exception) {

            exception.printStackTrace();
            plugin.getLogger().severe("Failed to read a registry");

        }

    }

}
