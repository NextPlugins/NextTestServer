package com.nextplugins.testserver.core.manager;

import com.henryfabio.minecraft.githubupdater.api.configuration.UpdaterConfiguration;
import com.henryfabio.minecraft.githubupdater.api.credentials.GithubCredentials;
import com.henryfabio.minecraft.githubupdater.bukkit.BukkitGithubUpdater;
import com.henryfabio.minecraft.githubupdater.bukkit.plugin.BukkitUpdatablePlugin;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.configuration.values.ConfigValue;
import lombok.val;

import javax.inject.Singleton;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public class UpdaterManager {

    private BukkitGithubUpdater githubUpdater;

    public void init() {

        val plugin = NextTestServer.getInstance();

        this.githubUpdater = new BukkitGithubUpdater(
                plugin,
                UpdaterConfiguration.DEFAULT,
                GithubCredentials.builder()
                        .username(ConfigValue.get(ConfigValue::githubUsername))
                        .accessToken(ConfigValue.get(ConfigValue::githubAccessToken))
                        .build()
        );

        this.githubUpdater.registerUpdatablePlugin(new BukkitUpdatablePlugin(
                plugin,
                "NextPlugins/NextTestServer"
        ));

    }
    
    public void unload() {
        this.githubUpdater.stop();
    }

}
