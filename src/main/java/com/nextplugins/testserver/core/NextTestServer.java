package com.nextplugins.testserver.core;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.registry.*;
import com.nextplugins.testserver.core.guice.PluginModule;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.ScoreboardManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import com.nextplugins.testserver.core.runnables.TagUpdateExecutor;
import lombok.Getter;
import lombok.val;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

@Getter
public final class NextTestServer extends JavaPlugin {

    private Injector injector;
    private SQLConnector sqlConnector;

    @Inject private InventoryRegistry inventoryRegistry;

    @Inject private GroupStorage groupStorage;
    @Inject private UserStorage userStorage;

    @Inject private TablistManager tablistManager;
    @Inject private LocationManager locationManager;
    @Inject private ScoreboardManager scoreboardManager;

    public static NextTestServer getInstance() {
        return getPlugin(NextTestServer.class);
    }

    @Override
    public void onEnable() {

        getLogger().info("Iniciando carregamento do plugin.");

        val loadTime = Stopwatch.createStarted();

        InventoryManager.enable(this);

        sqlConnector = configureSqlProvider(getConfig());

        injector = PluginModule.of(this).createInjector();
        injector.injectMembers(this);

        CommandRegistry.enable(this);
        ConfigurationRegistry.enable(this);
        PlaceholderRegistry.enable(this);
        ListenerRegistry.enable(this);
        TaskRegistry.enable(this);

        inventoryRegistry.init();

        groupStorage.init();
        userStorage.init();

        injector.injectMembers(TagUpdateExecutor.getInstance());

        tablistManager.init();
        locationManager.init();
        scoreboardManager.init();

        loadTime.stop();
        getLogger().log(Level.INFO, "Plugin inicializado com sucesso. ({0})", loadTime);

    }

    @Override
    public void onDisable() {

        groupStorage.unload();
        userStorage.unload();
        locationManager.unload();

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
