package com.nextplugins.testserver.core.api.model.group.adapter;

import com.github.eikefab.libs.yamladapter.YamlAdapter;
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

public final class GroupYamlAdapter implements YamlAdapter<Group> {

    @Override
    public Group adapt(String key, ConfigurationSection section) {

        ConfigurationSection groupConfig = section.getConfigurationSection("config");

        return Group.builder()
                .name(section.getName())
                .prefix(ColorUtils.colored(groupConfig.getString("prefix")))
                .sorter(groupConfig.getString("sorter").charAt(0))
                .priority(groupConfig.getInt("priority"))
                .permissions(section.getStringList("permissions"))
                .build();

    }

    @Override
    public ConfigurationSection adapt(Group value) {
        return null;
    }
}
