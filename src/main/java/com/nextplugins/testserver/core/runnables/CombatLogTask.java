package com.nextplugins.testserver.core.runnables;

import com.nextplugins.testserver.core.api.model.runnable.RunnableTaskInfo;
import com.nextplugins.testserver.core.utils.ActionBarUtils;
import lombok.Getter;
import lombok.val;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@RunnableTaskInfo(delay = 20L, period = 20L, async = true)
public class CombatLogTask implements Runnable {

    @Getter private static final Map<Player, Integer> combatDelay = new HashMap<>();
    public static boolean inCombat(Player player) {
        return combatDelay.containsKey(player);
    }

    @Override
    public void run() {
        for (val entry : combatDelay.entrySet()) {
            val player = entry.getKey();
            val delay = entry.getValue() - 1;
            if (delay <= 0) {
                combatDelay.remove(player);
                ActionBarUtils.sendActionBar(player, "&aVocê saiu de combate, já pode deslogar em segurança");
                player.playSound(player.getLocation(), "ORB_PICKUP", 10, 1);
                return;
            }

            ActionBarUtils.sendActionBar(player, "&cVocê está em combate por &f" + delay + " &csegundos");
            combatDelay.replace(player, delay);
        }
    }
}
