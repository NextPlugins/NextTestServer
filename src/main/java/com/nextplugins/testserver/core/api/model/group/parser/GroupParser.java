package com.nextplugins.testserver.core.api.model.group.parser;

import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Data(staticConstructor = "of")
public class GroupParser {

    private final ConfigurationSection section;

    public List<Group> parseSectionList() {

        List<Group> groups = Lists.newArrayList();

        for (String key : section.getKeys(false)) {
            groups.add(parseSection(section.getConfigurationSection(key)));
        }

        return groups;

    }

    public Group parseSection(ConfigurationSection section) {

        ConfigurationSection groupConfig = section.getConfigurationSection("config");

        return Group.builder()
                .name(section.getName())
                .prefix(ColorUtils.colored(groupConfig.getString("prefix")))
                .sorter(groupConfig.getString("sorter").charAt(0))
                .priority(groupConfig.getInt("priority"))
                .permissions(section.getStringList("permissions"))
                .build();

    }

}
