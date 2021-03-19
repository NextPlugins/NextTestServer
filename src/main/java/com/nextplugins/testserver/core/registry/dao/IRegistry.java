package com.nextplugins.testserver.core.registry.dao;

import com.google.common.reflect.ClassPath;
import com.nextplugins.testserver.core.NextTestServer;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public interface IRegistry {

    NextTestServer plugin = NextTestServer.getInstance();

    void register(ClassPath classPath) throws ClassNotFoundException, Exception;

}
