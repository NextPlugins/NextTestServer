package com.nextplugins.testserver.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.nextplugins.testserver.core.api.model.group.command.GroupCommand;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.command.AccountCommand;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.commands.UsualCommand;
import com.nextplugins.testserver.core.configuration.registry.ConfigurationRegistry;
import com.nextplugins.testserver.core.guice.PluginModule;
import com.nextplugins.testserver.core.registry.AutomaticRegistry;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class NextTestServer extends JavaPlugin {

    private Injector injector;

    @Inject private GroupStorage groupStorage;
    @Inject private AccountStorage accountStorage;

    public static NextTestServer getInstance() {
        return getPlugin(NextTestServer.class);
    }

    @Override
    public void onEnable() {

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {

            InventoryManager.enable(this);
            ConfigurationRegistry.enable(this);
            AutomaticRegistry.enable(this);

            this.injector = PluginModule.of(this).createInjector();
            this.injector.injectMembers(this);

            registerCommands();

            this.groupStorage.init();
            this.accountStorage.init();

        });

    }

    @Override
    public void onDisable() {
        this.groupStorage.purge();
    }

    private void registerCommands() {

        BukkitFrame bukkitFrame = new BukkitFrame(this);

        UsualCommand usualCommand = new UsualCommand();
        GroupCommand groupCommand = new GroupCommand();
        AccountCommand accountCommand = new AccountCommand();

        this.injector.injectMembers(groupCommand);
        this.injector.injectMembers(accountCommand);

        bukkitFrame.registerCommands(
                usualCommand,
                accountCommand,
                groupCommand
        );

    }
}
