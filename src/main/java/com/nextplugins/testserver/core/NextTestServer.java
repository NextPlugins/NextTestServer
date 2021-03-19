package com.nextplugins.testserver.core;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.nextplugins.testserver.core.commands.OtherCommand;
import com.nextplugins.testserver.core.registry.AutomaticRegistry;
import me.bristermitten.pdm.PluginDependencyManager;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

public final class NextTestServer extends JavaPlugin {

    public static NextTestServer getInstance() {
        return getPlugin(NextTestServer.class);
    }

    @Override
    public void onEnable() {

        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {

            InventoryManager.enable(this);

            BukkitFrame bukkitFrame = new BukkitFrame(this);
            bukkitFrame.registerCommands(new OtherCommand());

            AutomaticRegistry.createDefault().init();

        });

    }

}
