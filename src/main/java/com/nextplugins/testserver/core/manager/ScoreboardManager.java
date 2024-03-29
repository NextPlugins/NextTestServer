package com.nextplugins.testserver.core.manager;


import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.configuration.ScoreboardValue;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.val;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Singleton;

/**
 * This class is to organize some functions of the scoreboard,
 * but the original system is the NetherBoard
 *
 * @author https://github.com/MinusKube/Netherboard
 */

@Singleton
public class ScoreboardManager {

    private final Netherboard netherboard = Netherboard.instance();

    public void init() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                NextTestServer.getInstance(),
                this::updateAllScoreboards,
                20L * 15L, 20 * 15L
        );
    }

    public void updateScoreboard(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(NextTestServer.getInstance(), () -> {
            for (String s : ScoreboardValue.get(ScoreboardValue::worldsDisabled)) {
                if (player.getWorld().getName().equalsIgnoreCase(s)) {
                    return;
                }
            }

            val scoreboard = getPlayerScoreboard(player);

            val lines = ScoreboardValue.get(ScoreboardValue::lines);
            for (int i = 0; i < lines.size(); i++) {

                val line = lines.get(lines.size() - (1 + i));
                scoreboard.set(PlaceholderAPI.setPlaceholders(player, line), i);

            }
        });
    }

    public BPlayerBoard getPlayerScoreboard(Player player) {
        return getCacheBoard(player);
    }

    public BPlayerBoard getCacheBoard(Player player) {
        return this.netherboard.getBoard(player);
    }

    public void updateAllScoreboards() {
        Bukkit.getOnlinePlayers().forEach(this::updateScoreboard);
    }


}
