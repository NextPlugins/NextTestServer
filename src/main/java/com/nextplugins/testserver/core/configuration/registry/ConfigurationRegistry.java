package com.nextplugins.testserver.core.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import com.nextplugins.testserver.core.configuration.values.MessagesValue;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data(staticConstructor = "of")
public final class ConfigurationRegistry {

    private final Plugin plugin;

    public void setup() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(
                plugin,
                "messages.yml"
        );

        configurationInjector.injectConfiguration(
                MessagesValue.instance()
        );
    }

}
