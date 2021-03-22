package com.nextplugins.testserver.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.commands.registry.CommandRegistry;
import com.nextplugins.testserver.core.configuration.registry.ConfigurationRegistry;
import com.nextplugins.testserver.core.guice.PluginModule;
import com.nextplugins.testserver.core.listeners.registry.ListenerRegistry;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.ScoreboardManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import com.nextplugins.testserver.core.manager.UpdaterManager;
import com.nextplugins.testserver.core.placeholder.registry.PlaceholderRegistry;
import com.nextplugins.testserver.core.registry.AutomaticRegistry;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class NextTestServer extends JavaPlugin {

    private Injector injector;

    @Inject private GroupStorage groupStorage;
    @Inject private AccountStorage accountStorage;

    @Inject private UpdaterManager updaterManager;
    @Inject private TablistManager tablistManager;
    @Inject private LocationManager locationManager;
    @Inject private ScoreboardManager scoreboardManager;

    public static NextTestServer getInstance() {
        return getPlugin(NextTestServer.class);
    }

    @Override
    public void onEnable() {

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {

            InventoryManager.enable(this);

            ConfigurationRegistry.enable(this);
            CommandRegistry.enable(this);
            ListenerRegistry.enable(this);

            this.injector = PluginModule.of(this).createInjector();
            this.injector.injectMembers(this);

            PlaceholderRegistry.enable(this);

            this.groupStorage.init();
            this.accountStorage.init();

            this.updaterManager.init();
            this.tablistManager.init();
            this.locationManager.init();
            this.scoreboardManager.init();

        });

    }

    @Override
    public void onDisable() {


        this.groupStorage.unload();
        this.locationManager.unload();

    }

}
