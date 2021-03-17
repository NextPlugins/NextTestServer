package com.nextplugins.testserver.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginInformationUtils {

    public static String concatPlugins(List<String> plugins) {
        StringBuilder builder = new StringBuilder();
        builder.append(coloredPlugin(plugins.get(0)));

        plugins.remove(0);

        for (String plugin : plugins) {
            builder.append("&f, ").append(coloredPlugin(plugin));
        }

        return builder.toString();
    }

    private static String coloredPlugin(String pluginName) {
        return (isActive(pluginName) ? "&e" : "&7") + pluginName;
    }

    private static boolean isActive(String pluginName) {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null) return false;

        return plugin.isEnabled();

    }

}
