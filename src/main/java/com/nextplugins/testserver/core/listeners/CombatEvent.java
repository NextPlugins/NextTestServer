package com.nextplugins.testserver.core.listeners;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.runnables.CombatLogTask;
import com.nextplugins.testserver.core.utils.ActionBarUtils;
import com.nextplugins.testserver.core.utils.ColorUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CombatEvent implements Listener {

    @Inject private LocationManager locationManager;

    @EventHandler(priority = EventPriority.HIGH)
    public void onAtack(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        val player = (Player) event.getEntity();
        val target = (Player) event.getDamager();
        putInCombat(player, target);
        putInCombat(target, player);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (CombatLogTask.inCombat(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ColorUtil.colored("&cVocê não pode usar comandos em combate."));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        if (CombatLogTask.inCombat(event.getPlayer()) && CombatLogTask.getCombatDelay().get(event.getPlayer()) > 0) {
            event.getPlayer().setHealth(0);

            val message = ColorUtil.colored("&cO jogador " + event.getPlayer().getName() + " desconectou em combate. Peidou na farofa...");
            Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(message));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (CombatLogTask.inCombat(event.getEntity())) CombatLogTask.getCombatDelay().remove(event.getEntity());
        event.setDeathMessage("");
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {

        val spawn = locationManager.getLocation("spawn");
        if (spawn == null) return;

        event.getPlayer().teleport(spawn);

    }

    private void putInCombat(Player player, Player target) {
        if (player.hasPermission("server.combat.bypass")) return;

        if (!CombatLogTask.inCombat(player)) {

            ActionBarUtils.sendActionBar(player, "&cVocê entrou em combate com " + target.getName());
            player.setAllowFlight(false);

            CombatLogTask.getCombatDelay().put(player, 15);

        } else {
            CombatLogTask.getCombatDelay().replace(player, 15);
        }
    }
}
