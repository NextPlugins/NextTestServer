package com.nextplugins.testserver.core.api.model.group;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data
@Builder
public class Group {

    private final String name;
    private final String prefix;
    private final String resumedPrefix;

    private final List<String> permissions;

    private final int priority;
    private final char sorter;
    private final boolean defaultGroup;

    public static Group createDefault(String name, String coloredName) {
        return Group.builder()
                .name(name)
                .resumedPrefix("&c" + name)
                .prefix(coloredName)
                .priority(0)
                .defaultGroup(false)
                .sorter('z')
                .permissions(Lists.newArrayList())
                .build();
    }

}
