package com.nextplugins.testserver.core.commands;

import com.google.common.collect.Lists;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.utils.ColorUtils;
import com.nextplugins.testserver.core.utils.PluginInformationUtils;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class UsualCommand {

    @Command(
            name = "plugins",
            aliases = {"about", "?", "help", "ajuda", "plugin"},
            description = "Ver as informações do servidor de teste",
            target = CommandTarget.PLAYER
    )
    public void onHelpCommand(Context<Player> context) {

        Player sender = context.getSender();

        sender.sendMessage(ColorUtils.colored(
                "",
                "&fEste servidor foi feito pela equipe &bNextPlugins",
                "&fO coração do servidor é o plugin &a&nNextTestServer v" + NextTestServer.getInstance().getDescription().getVersion(),
                "&fVeja os plugins do servidor usando &e/" + context.getLabel() + " plugins",
                ""
        ));

    }

    @Command(
            name = "plugins.plugins",
            description = "Ver as informações do servidor de teste",
            target = CommandTarget.PLAYER
    )
    public void onViewPluginsCommand(Context<Player> context) {

        List<Plugin> plugins = Arrays.asList(Bukkit.getPluginManager().getPlugins());

        List<String> ownPlugins = Lists.newArrayList();
        List<String> otherPlugins = Lists.newArrayList();

        for (Plugin plugin : plugins) {

            List<String> authors = plugin.getDescription().getAuthors();
            if (authors.size() < 1) {

                otherPlugins.add(plugin.getName());
                continue;

            }

            if (plugin.getName().contains("Next")) ownPlugins.add(plugin.getName());
            else otherPlugins.add(plugin.getName());
        }

        context.getSender().sendMessage(ColorUtils.colored(
                "&fTodos os plugins usados no &6Lyces&f: &8(" + plugins.size() + " plugins)",
                "",
                "&fPlugins &epróprios&f: &8(" + ownPlugins.size() + " plugins)",
                PluginInformationUtils.concatPlugins(ownPlugins),
                "",
                "&fPlugins &3públicos&f: &8(" + otherPlugins.size() + " plugins)",
                PluginInformationUtils.concatPlugins(otherPlugins),
                "")
        );

    }

}
