package com.nextplugins.testserver.core.api.model.group.storage;

import com.github.eikefab.libs.yamladapter.ConfigAdapter;
import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.adapter.GroupYamlAdapter;
import com.nextplugins.testserver.core.api.model.map.CaseInsensitiveLinkedMap;
import com.nextplugins.testserver.core.configuration.PermissionsValue;
import lombok.val;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public class GroupStorage {

    private final CaseInsensitiveLinkedMap<Group> groups = CaseInsensitiveLinkedMap.newMap();
    private ConfigAdapter<Group> configAdapter;

    public void init() {

        val file = new File(NextTestServer.getInstance().getDataFolder(), "groups.yml");

        configAdapter = new ConfigAdapter<>(file);
        configAdapter.adapt("groups", GroupYamlAdapter.class);

        configAdapter.getValues().forEach(this::register);

        /*val section = PermissionsValue.get(PermissionsValue::section);
        GroupYamlAdapter.of(section).parseSectionList().forEach(this::register);*/

    }

    public void register(Group group) {
        groups.put(group.getName(), group);
    }

    public void unregister(String name) {
        groups.remove(name);
    }

    public Group getGroupByName(String name) {
        return groups.get(name);
    }

    public List<String> getGroupNames() {
        return Lists.newArrayList(this.groups.keySet());
    }

    public void unload() {

        val file = new File(NextTestServer.getInstance().getDataFolder(), "groups.yml");
        /*val configuration = YamlConfiguration.loadConfiguration(file);

        val groups = this.groups.values();
        for (Group group : groups) {

            val path = "groups." + group.getName();
            val configPath = "groups." + group.getName() + ".config";

            val prefix = group.getPrefix();
            val resumedPrefix = group.getResumedPrefix();
            val permissions = group.getPermissions();
            val priority = group.getPriority();
            val sorter = group.getSorter();

            configuration.set(configPath + ".prefix", prefix);
            configuration.set(configPath + ".resumedPrefix", resumedPrefix);
            configuration.set(configPath + ".priority", priority);
            configuration.set(configPath + ".sorter", sorter);

            configuration.set(path + ".permissions", permissions);

        }

        try {
            configuration.save(file);
        } catch (IOException ignored) {
            NextTestServer.getInstance().getLogger().warning("Can't save groups permissions");
        }*/

        configAdapter.setValues(new HashSet<>(groups.values()));
        configAdapter.save("groups", GroupYamlAdapter.class);

    }

}
