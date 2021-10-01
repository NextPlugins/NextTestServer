package com.nextplugins.testserver.core.commands;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.configuration.MessageValue;
import com.nextplugins.testserver.core.manager.LocationManager;
import com.nextplugins.testserver.core.utils.ColorUtil;
import com.nextplugins.testserver.core.utils.SoundUtils;
import com.nextplugins.testserver.core.utils.TitleUtils;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class WarpCommand {

    @Inject
    private LocationManager locationManager;

    @Command(
            name = "warp",
            aliases = {"locais"},
            usage = "warp <local>",
            permission = "nextcore.usewarp",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onWarpCommand(Context<Player> context,
                              String local) {

        val sender = context.getSender();

        val location = locationManager.getLocation(local);
        if (location == null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cEsta warp não existe, lista de warps válidas:",
                    "&c" + locationManager.getLocationMap().keySet()
            ));
            return;

        }

        sender.teleport(location);
        TitleUtils.sendTitle(
                sender,
                MessageValue.get(MessageValue::teleported).replace("%warp%", local.toLowerCase()),
                20, 40, 20
        );

        SoundUtils.sendSound(sender, "NOTE_PLING");
    }

    @Command(
            name = "setwarp",
            aliases = {"setlocal"},
            usage = "setwarp <local>",
            permission = "nextcore.createwarp",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onSetWarpCommand(Context<Player> context,
                                 String local) {

        val sender = context.getSender();
        locationManager.getLocationMap().put(local, sender.getLocation());

        sender.sendMessage(ColorUtil.colored(
                "&aLocalização setada com sucesso."
        ));

    }

    @Command(
            name = "delwarp",
            aliases = {"dellocal"},
            usage = "delwarp <local>",
            permission = "nextcore.createwarp",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void onDelWarpCommand(Context<Player> context,
                                 String local) {

        val sender = context.getSender();
        locationManager.getLocationMap().remove(local);

        sender.sendMessage(ColorUtil.colored(
                "&aLocalização deletada com sucesso."
        ));

    }

}
