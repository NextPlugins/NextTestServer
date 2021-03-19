package com.nextplugins.testserver.core.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class ClassUtils {

    public static ImmutableSet<ClassPath.ClassInfo> getClasses(ClassPath path, String folder) {
        return path.getTopLevelClassesRecursive("com.nextplugins.testserver.core." + folder);
    }

}
