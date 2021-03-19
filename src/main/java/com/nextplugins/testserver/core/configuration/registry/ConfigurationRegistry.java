package com.nextplugins.testserver.core.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import com.nextplugins.testserver.core.configuration.values.*;
import org.bukkit.plugin.Plugin;

public final class ConfigurationRegistry {

    public static void enable(Plugin plugin) {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "config.yml",
                "groups.yml",
                "messages.yml",
                "locations.yml",
                "scoreboard.yml"
        );

        configurationInjector.injectConfiguration(
                ConfigValue.instance(),
                MessagesValue.instance(),
                PermissionsValue.instance(),
                LocationValue.instance(),
                ScoreboardValue.instance()
        );
    }

}
