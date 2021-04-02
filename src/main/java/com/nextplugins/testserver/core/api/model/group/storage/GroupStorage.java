package com.nextplugins.testserver.core.api.model.group.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.parser.GroupParser;
import com.nextplugins.testserver.core.configuration.PermissionsValue;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public class GroupStorage {

    private final Map<String, Group> groups = Maps.newHashMap();

    public void init() {

        val section = PermissionsValue.get(PermissionsValue::section);
        GroupParser.of(section).parseSectionList().forEach(this::register);

    }

    public void register(Group group) {
        groups.put(group.getName(), group);
    }

    public void unregister(String name) {
        groups.remove(name);
    }

    public Group getGroupByName(String name) {

        val groupEntry = groups.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);

        return groupEntry == null ? null : groupEntry.getValue();

    }

    public List<String> getGroupNames() {
        return Lists.newArrayList(this.groups.keySet());
    }

    public void unload() {

        val file = new File(NextTestServer.getInstance().getDataFolder(), "groups.yml");
        val configuration = YamlConfiguration.loadConfiguration(file);

        val groups = this.groups.values();
        for (Group group : groups) {

            ConfigurationSection section = configuration.getConfigurationSection("groups." + group.getName());

            val permissions = group.getPermissions();
            section.set("permissions", permissions);

        }

        try {
            configuration.save(file);
        } catch (IOException ignored) {
            NextTestServer.getInstance().getLogger().warning("Can't save groups permissions");
        }

    }

}
