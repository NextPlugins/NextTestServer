package com.nextplugins.testserver.core.runnables;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.utils.ColorUtil;
import lombok.Getter;
import lombok.val;
import lombok.var;
import org.bukkit.Bukkit;

public class TagUpdateExecutor {

    @Getter private static final TagUpdateExecutor instance = new TagUpdateExecutor();
    @Inject private UserStorage userStorage;

    // idk if make changes or otimize
    private boolean executing;

    public void updateTag() {

        if (executing) return;
        executing = true;

        val plugin = NextTestServer.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (val player : Bukkit.getOnlinePlayers()) {

                var scoreboard = plugin.getScoreboardManager().getPlayerScoreboard(player).getScoreboard();
                for (val target : Bukkit.getOnlinePlayers()) {

                    val group = userStorage.findAccount(target).getGroup();

                    val teamIdentifier = group.getSorter() + group.getName();
                    var prefix = group.getPrefix() + " ";
                    var suffix = ColorUtil.colored(" &7[#NEXT]");

                    if (prefix.length() > 16) prefix = prefix.substring(0, 16);
                    if (suffix.length() > 16) suffix = suffix.substring(0, 16);

                    var team = scoreboard.getTeam(teamIdentifier);
                    if (team == null) {
                        team = scoreboard.registerNewTeam(teamIdentifier);
                        team.setPrefix(prefix);
                        team.setSuffix(suffix);
                    }

                    team.addPlayer(target);
                }

                player.setScoreboard(scoreboard);
            }

            executing = false;
        });
    }
}
