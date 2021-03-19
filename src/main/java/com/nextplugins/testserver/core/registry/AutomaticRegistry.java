package com.nextplugins.testserver.core.registry;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.registry.dao.IRegistry;
import lombok.Data;

import java.util.logging.Logger;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data(staticConstructor = "createDefault")
public class AutomaticRegistry {

    private static final Logger LOGGER = NextTestServer.getInstance().getLogger();

    public void init() {

        try {

            ClassPath classPath = ClassPath.from(getClass().getClassLoader());

            String packageName = "com.nextplugins.testserver.core.registry.dao.impl";
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {

                Class pathClass = Class.forName(classInfo.getName());
                if (!IRegistry.class.isAssignableFrom(pathClass)) continue;

                IRegistry registry = (IRegistry) pathClass.newInstance();
                registry.register(classPath);

            }

        } catch (Exception exception) {

            exception.printStackTrace();
            LOGGER.severe("Failed to read a registry");

        }

    }

}
