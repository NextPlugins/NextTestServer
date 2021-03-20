package com.nextplugins.testserver.core;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration;
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials;
import com.henryfabio.minecraft.githubupdater.bukkit.BukkitGithubUpdater;
import com.henryfabio.minecraft.githubupdater.bukkit.plugin.BukkitUpdatablePlugin;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.nextplugins.testserver.core.api.model.group.command.GroupCommand;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.player.command.AccountCommand;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.commands.UsualCommand;
import com.nextplugins.testserver.core.commands.WarpCommand;
import com.nextplugins.testserver.core.configuration.registry.ConfigurationRegistry;
import com.nextplugins.testserver.core.configuration.values.ConfigValue;
import com.nextplugins.testserver.core.guice.PluginModule;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.manager.ScoreboardManager;
import com.nextplugins.testserver.core.manager.TablistManager;
import com.nextplugins.testserver.core.placeholder.registry.PlaceholderRegistry;
import com.nextplugins.testserver.core.registry.AutomaticRegistry;
import lombok.Getter;
import lombok.val;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class NextTestServer extends JavaPlugin {

    private Injector injector;

    private BukkitGithubUpdater githubUpdater;

    @Inject private GroupStorage groupStorage;
    @Inject private AccountStorage accountStorage;
    @Inject private LocationManager locationManager;
    @Inject private ScoreboardManager scoreboardManager;
    @Inject private TablistManager tablistManager;

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

            PlaceholderRegistry.enable(this);

            registerUpdater();
            registerCommands();

            this.groupStorage.init();
            this.accountStorage.init();
            this.scoreboardManager.init();
            this.locationManager.init();
            this.tablistManager.init();

        });

    }

    @Override
    public void onDisable() {
        this.groupStorage.purge();
        this.githubUpdater.stop();
    }

    private void registerUpdater() {

        this.githubUpdater = new BukkitGithubUpdater(
                this,
                UpdaterConfiguration.DEFAULT,
                GithubCredentials.builder()
                        .username(ConfigValue.get(ConfigValue::githubUsername))
                        .accessToken(ConfigValue.get(ConfigValue::githubAccessToken))
                        .build()
        );

        this.githubUpdater.registerUpdatablePlugin(new BukkitUpdatablePlugin(
                this,
                "NextPlugins/NextTestServer"
        ));

    }

    private void registerCommands() {

        BukkitFrame bukkitFrame = new BukkitFrame(this);

        val usualCommand = new UsualCommand();
        val warpCommand = new WarpCommand();
        val groupCommand = new GroupCommand();
        val accountCommand = new AccountCommand();

        this.injector.injectMembers(warpCommand);
        this.injector.injectMembers(groupCommand);
        this.injector.injectMembers(accountCommand);

        bukkitFrame.registerCommands(
                warpCommand,
                usualCommand,
                groupCommand,
                accountCommand
        );

    }
}
