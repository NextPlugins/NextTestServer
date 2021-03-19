package com.nextplugins.testserver.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
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

            BukkitFrame bukkitFrame = new BukkitFrame(this);
            bukkitFrame.registerCommands(new UsualCommand());

            this.accountStorage.init();

        });

    }

}
