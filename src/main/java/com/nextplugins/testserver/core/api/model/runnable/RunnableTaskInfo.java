package com.nextplugins.testserver.core.api.model.runnable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RunnableTaskInfo {

    long delay();
    long period();
    boolean async();
}

