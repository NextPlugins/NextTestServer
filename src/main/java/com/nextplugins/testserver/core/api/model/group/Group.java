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

    private final List<String> permissions;

    private final int priority;
    private final char sorter;

    public static Group createDefault(String name) {

        return Group.builder()
                .name(name)
                .prefix("&c" + name)
                .priority(0)
                .sorter('c')
                .permissions(Lists.newArrayList())
                .build();
    }

}
