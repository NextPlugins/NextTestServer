package com.nextplugins.testserver.core.placeholder;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class PlaceholderHook extends PlaceholderExpansion {

    private final Plugin plugin;

    @Inject private UserStorage userStorage;

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return "NextPlugins";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "&cOcorreu um erro!";

        if (params.equalsIgnoreCase("group")) {

            val account = userStorage.findAccount(player);
            return account.getGroup().getPrefix();

        }

        return "Placeholder inválida";
    }

}
