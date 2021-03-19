package com.nextplugins.testserver.core.manager;


import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

/**
    This class is to organize some functions of the scoreboard,
    but the original system is the NetherBoard
    @author https://github.com/MinusKube/Netherboard
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScoreboardManager {

    public static BPlayerBoard createDefault(Player player) {

        Netherboard.instance().createBoard(player, "");

    }


}
