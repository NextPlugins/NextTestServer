package com.nextplugins.testserver.core.api.model.player.command;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.registry.InventoryRegistry;
import com.nextplugins.testserver.core.utils.ColorUtil;
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
public class UserCommand {

    @Inject private UserStorage userStorage;
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
        if (player == null) player = sender;

        val account = userStorage.findAccount(player);
        if (account == null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cOcorreu um erro, tente novamente quando este jogador entrar no servidor."
            ));
            return;


        }

        val accountView = this.inventoryRegistry.getUserView();
        accountView.openInventory(sender, viewer -> viewer.getPropertyMap().set("target", account));

    }

}
