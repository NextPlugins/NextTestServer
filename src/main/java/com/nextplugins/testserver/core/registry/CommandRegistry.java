package com.nextplugins.testserver.core.registry;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.command.GroupCommand;
import com.nextplugins.testserver.core.api.model.player.command.AccountCommand;
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

        //val usualCommand = new UsualCommand();
        val warpCommand = new WarpCommand();
        val groupCommand = new GroupCommand();
        val accountComamnd = new AccountCommand();

        injector.injectMembers(warpCommand);
        injector.injectMembers(groupCommand);
        injector.injectMembers(accountComamnd);

        bukkitFrame.registerCommands(
                //usualCommand,
                warpCommand,
                groupCommand,
                accountComamnd
        );

        //injector.injectMembers(usualCommand)

    }

}
