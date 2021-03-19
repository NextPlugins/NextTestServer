package com.nextplugins.testserver.core.api.model.player.command;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.Account;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;


/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class AccountCommand {

    @Inject private AccountStorage accountStorage;

    @Command(
            name = "conta",
            aliases = {"account", "player", "playerinfo", "jogador", "ver"},
            usage = "/ver [jogador]",
            permission = "nextcore.editplayer",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onViewPlayerInfo(Context<Player> context,
                                 @Optional Player player) {

        val sender = context.getSender();
        if (player == null) player = sender;

        Account account = accountStorage.loadPlayer(player);
        // TODO

    }

}
