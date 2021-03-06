package com.nextplugins.testserver.core.registry;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.placeholder.PlaceholderHook;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlaceholderRegistry {

    public static void enable(NextTestServer plugin) {

        val placeholderHook = new PlaceholderHook(plugin);
        plugin.getInjector().injectMembers(placeholderHook);

        placeholderHook.register();

        plugin.getLogger().info("Placeholder registrada com sucesso!");
    }

}
