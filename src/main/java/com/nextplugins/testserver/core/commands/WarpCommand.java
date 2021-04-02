package com.nextplugins.testserver.core.commands;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.configuration.MessageValue;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.utils.ColorUtils;
import com.nextplugins.testserver.core.utils.MessageUtils;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class WarpCommand {

    @Inject private LocationManager locationManager;

    @Command(
            name = "warp",
            aliases = {"locais"},
            usage = "warp <local>",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onWarpCommand(Context<Player> context,
                              String local) {

        val sender = context.getSender();

        val location = locationManager.getLocation(local);
        if (location == null) {

            sender.sendMessage(ColorUtils.colored(
                    "&cEsta warp não existe, lista de warps válidas:",
                    "&c" + locationManager.getLocationMap().keySet()
            ));
            return;

        }

        sender.teleport(location);
        MessageUtils.sendSoundAndTitle(MessageValue.get(MessageValue::teleported), Sound.NOTE_PLING, 150);

    }

    @Command(
            name = "setwarp",
            aliases = {"setlocal"},
            usage = "setwarp <local>",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onSetWarpCommand(Context<Player> context,
                                 String local) {

        val sender = context.getSender();
        locationManager.getLocationMap().put(local, sender.getLocation());

        sender.sendMessage(ColorUtils.colored(
                "&aLocalização setada com sucesso."
        ));

    }

    @Command(
            name = "delwarp",
            aliases = {"dellocal"},
            usage = "delwarp <local>",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onDelWarpCommand(Context<Player> context,
                                 String local) {

        val sender = context.getSender();
        locationManager.getLocationMap().remove(local);

        sender.sendMessage(ColorUtils.colored(
                "&aLocalização deletada com sucesso."
        ));

    }

}
