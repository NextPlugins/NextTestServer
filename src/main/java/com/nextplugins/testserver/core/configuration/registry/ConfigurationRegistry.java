package com.nextplugins.testserver.core.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import com.nextplugins.testserver.core.configuration.values.ConfigValue;
import com.nextplugins.testserver.core.configuration.values.MessagesValue;
import com.nextplugins.testserver.core.configuration.values.PermissionsValue;
import com.nextplugins.testserver.core.configuration.values.ScoreboardValue;
import org.bukkit.plugin.Plugin;

public final class ConfigurationRegistry {

    public static void enable(Plugin plugin) {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "config.yml",
                "groups.yml",
                "messages.yml",
                "scoreboard.yml"
        );

        configurationInjector.injectConfiguration(
                ConfigValue.instance(),
                MessagesValue.instance(),
                PermissionsValue.instance(),
                ScoreboardValue.instance()
        );
    }

}
