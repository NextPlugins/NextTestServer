package com.nextplugins.testserver.core.api.model.group;

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

}
