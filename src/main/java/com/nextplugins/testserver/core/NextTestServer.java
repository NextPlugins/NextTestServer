package com.nextplugins.testserver.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.commands.registry.CommandRegistry;
import com.nextplugins.testserver.core.configuration.registry.ConfigurationRegistry;
import com.nextplugins.testserver.core.guice.PluginModule;
import com.nextplugins.testserver.core.listeners.registry.ListenerRegistry;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.ScoreboardManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import com.nextplugins.testserver.core.placeholder.registry.PlaceholderRegistry;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class NextTestServer extends JavaPlugin {

    private Injector injector;
    private SQLConnector sqlConnector;

    @Inject private GroupStorage groupStorage;
    @Inject private AccountStorage accountStorage;

    @Inject private TablistManager tablistManager;
    @Inject private LocationManager locationManager;
    @Inject private ScoreboardManager scoreboardManager;

    public static NextTestServer getInstance() {
        return getPlugin(NextTestServer.class);
    }

    @Override
    public void onEnable() {

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {

            try {

                InventoryManager.enable(this);

                this.sqlConnector = configureSqlProvider(this.getConfig());

                this.injector = PluginModule.of(this).createInjector();
                this.injector.injectMembers(this);

                ConfigurationRegistry.enable(this);
                PlaceholderRegistry.enable(this);
                ListenerRegistry.enable(this);
                CommandRegistry.enable(this);

                this.groupStorage.init();
                this.accountStorage.init();

                this.tablistManager.init();
                this.locationManager.init();
                this.scoreboardManager.init();

                this.getLogger().info("Plugin enabled successfully");
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });

    }

    @Override
    public void onDisable() {

        this.groupStorage.unload();
        this.accountStorage.unload();
        this.locationManager.unload();

    }

    private SQLConnector configureSqlProvider(ConfigurationSection section) {

        SQLConnector connector;
        if (section.getBoolean("connection.mysql.enable")) {

            ConfigurationSection mysqlSection = section.getConfigurationSection("connection.mysql");

            connector = MySQLDatabaseType.builder()
                    .address(mysqlSection.getString("address"))
                    .username(mysqlSection.getString("username"))
                    .password(mysqlSection.getString("password"))
                    .database(mysqlSection.getString("database"))
                    .build()
                    .connect();

        } else {

            ConfigurationSection sqliteSection = section.getConfigurationSection("connection.sqlite");

            connector = SQLiteDatabaseType.builder()
                    .file(new File(sqliteSection.getString("file")))
                    .build()
                    .connect();

        }

        return connector;

    }

}
