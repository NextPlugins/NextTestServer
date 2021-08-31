package com.nextplugins.testserver.core.listeners;

import com.nextplugins.testserver.core.utils.TitleUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldEvent implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setFoodLevel(20);
    }

    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent event) {
        if (event.getPlayer().getAllowFlight()
                && !event.getPlayer().getWorld().getName().equalsIgnoreCase("plot")
                && !event.getPlayer().hasPermission("server.fly.all")) {
            TitleUtils.sendTitle(event.getPlayer(), "&6&lMODO VOO<nl>&fFoi desativado por trocar de mundo", 20, 20, 20);
            event.getPlayer().setAllowFlight(false);
        }
    }

}
