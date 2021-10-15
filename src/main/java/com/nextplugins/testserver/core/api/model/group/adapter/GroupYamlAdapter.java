package com.nextplugins.testserver.core.api.model.group.adapter;

import com.github.eikefab.libs.yamladapter.YamlAdapter;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.utils.ColorUtil;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;

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
                .prefix(ColorUtil.colored(groupConfig.getString("prefix")))
                .sorter(groupConfig.getString("sorter").charAt(0))
                .priority(groupConfig.getInt("priority"))
                .permissions(section.getStringList("permissions"))
                .defaultGroup(groupConfig.getBoolean("default", false))
                .build();

    }

    @Override
    public void adapt(Group group, ConfigurationSection section) {

        val path = group.getName();
        val configPath = group.getName() + ".config";

        val prefix = group.getPrefix();
        val resumedPrefix = group.getResumedPrefix();
        val permissions = group.getPermissions();
        val priority = group.getPriority();
        val sorter = group.getSorter();

        section.set(configPath + ".prefix", prefix);
        section.set(configPath + ".resumedPrefix", resumedPrefix);
        section.set(configPath + ".priority", priority);
        section.set(configPath + ".sorter", sorter);

        section.set(path + ".permissions", permissions);


    }

}
