package com.nextplugins.testserver.core.registry.dao;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.NextTestServer;
import org.bukkit.plugin.Plugin;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public interface IRegistry {

    Plugin plugin = NextTestServer.getInstance();

    void register(ClassPath classPath) throws ClassNotFoundException, Exception;

}
