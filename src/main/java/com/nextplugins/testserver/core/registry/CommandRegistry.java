package com.nextplugins.testserver.core.registry;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.command.GroupCommand;
import com.nextplugins.testserver.core.api.model.player.command.UserCommand;
import com.nextplugins.testserver.core.commands.FancyCommand;
import com.nextplugins.testserver.core.commands.UsualCommand;
import com.nextplugins.testserver.core.commands.WarpCommand;
import lombok.val;
import me.saiintbrisson.bukkit.command.BukkitFrame;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class CommandRegistry {

    public static void enable(NextTestServer instance) {

        val bukkitFrame = new BukkitFrame(instance);
        val injector = instance.getInjector();

        val warpCommand = new WarpCommand();
        val usualCommand = new UsualCommand();
        val fancyCommand = new FancyCommand();
        val groupCommand = new GroupCommand();
        val accountComamnd = new UserCommand();

        bukkitFrame.registerCommands(
                warpCommand,
                fancyCommand,
                groupCommand,
                usualCommand,
                accountComamnd
        );

        injector.injectMembers(warpCommand);
        injector.injectMembers(fancyCommand);
        injector.injectMembers(groupCommand);
        injector.injectMembers(usualCommand);
        injector.injectMembers(accountComamnd);

    }

}
