package com.nextplugins.testserver.core.api.model.player.command;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.storage.AccountStorage;
import com.nextplugins.testserver.core.registry.InventoryRegistry;
import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class AccountCommand {

    @Inject private AccountStorage accountStorage;
    @Inject private InventoryRegistry inventoryRegistry;

    @Command(
            name = "conta",
            aliases = {"account", "player", "playerinfo", "jogador"},
            usage = "conta [jogador]",
            permission = "nextcore.editplayer",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onViewPlayerInfo(Context<Player> context,
                                 @Optional OfflinePlayer player) {

        val sender = context.getSender();
        if (player == null) {

            sender.sendMessage(ColorUtils.colored(
                    "&cEste jogador não existe."
            ));
            return;

        }

        val account = accountStorage.findAccount(player);
        if (account == null) {

            sender.sendMessage(ColorUtils.colored(
                    "&cOcorreu um erro, tente novamente quando este jogador entrar no servidor."
            ));
            return;


        }

        val accountView = this.inventoryRegistry.getAccountView();
        accountView.openInventory(sender, viewer -> viewer.getPropertyMap().set("target", account));

    }

}
