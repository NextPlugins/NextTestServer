package com.nextplugins.testserver.core.commands.registry;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.group.command.GroupCommand;
import com.nextplugins.testserver.core.api.model.player.command.AccountCommand;
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

        BukkitFrame bukkitFrame = new BukkitFrame(instance);

        val usualCommand = new UsualCommand();
        val warpCommand = new WarpCommand();
        val groupCommand = new GroupCommand();
        val accountCommand = new AccountCommand();

        val injector = instance.getInjector();

        injector.injectMembers(warpCommand);
        injector.injectMembers(groupCommand);
        injector.injectMembers(accountCommand);

        bukkitFrame.registerCommands(
                warpCommand,
                usualCommand,
                groupCommand,
                accountCommand
        );

    }

}
