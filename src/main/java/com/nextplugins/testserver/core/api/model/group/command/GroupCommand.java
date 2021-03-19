package com.nextplugins.testserver.core.api.model.group.command;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.group.utils.GroupUtils;
import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class GroupCommand {

    @Inject private GroupStorage groupStorage;

    @Command(
            name = "grupo",
            aliases = {"editgrupo", "editargrupo"},
            permission = "nextcore.editgrupo",
            async = true
    )
    public void onGroupCommand(Context<CommandSender> context) {

        CommandSender sender = context.getSender();
        sender.sendMessage(ColorUtils.colored(
                "",
                "&aNextCore &7- &fEditar grupo",
                "",
                " &a/grupo add <grupo> <permissão> &7- &fAdicionar permissão à um grupo",
                " &a/grupo remove <grupo> <permissão> &7- &fRemover permissão de um grupo",
                " &a/grupo listar <grupo> &7- &fVer as permissão de um grupo",
                ""
        ));

    }

    @Command(
            name = "grupo.add",
            aliases = {"adicionar"},
            permission = "nextcore.editgrupo",
            async = true
    )
    public void onAddPermissionOnGroup(Context<CommandSender> context,
                                       String groupName,
                                       String permission) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtils.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtils.colored("&c" + groupStorage.getGroups()));
            return;

        }

        val success = GroupUtils.addPermission(group, permission);
        if (success) {

            sender.sendMessage(ColorUtils.colored("&aVocê adicionou a permissão &f" + permission + " &aao grupo " + group.getPrefix() + " &acom sucesso."));
            return;

        }

        sender.sendMessage(ColorUtils.colored("&cEsta permissão já é presente no grupo."));

    }

    @Command(
            name = "grupo.remove",
            aliases = {"rem"},
            permission = "nextcore.editgrupo",
            async = true
    )
    public void onRemovePermissionOnGroup(Context<CommandSender> context,
                                          String groupName,
                                          String permission) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtils.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtils.colored("&c" + groupStorage.getGroups()));
            return;

        }

        val success = GroupUtils.removePermission(group, permission);
        if (success) {

            sender.sendMessage(ColorUtils.colored("&aVocê removeu a permissão &f" + permission + " &aao grupo " + group.getPrefix() + " &acom sucesso."));
            return;

        }

        sender.sendMessage(ColorUtils.colored("&cEsta permissão não faz parte deste grupo."));

    }

    @Command(
            name = "grupo.listar",
            aliases = {"list"},
            permission = "nextcore.editgrupo",
            async = true
    )
    public void onRemovePermissionOnGroup(Context<CommandSender> context,
                                          String groupName) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtils.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtils.colored("&c" + groupStorage.getGroups()));
            return;

        }

        sender.sendMessage(ColorUtils.colored("&aTodas as permissões do grupo " + group.getPrefix() + "&a:"));

        for (int i = 0; i < group.getPermissions().size(); i++) {

            String permission = group.getPermissions().get(i);
            sender.sendMessage(ColorUtils.colored("&8" + i + "º: &f" + permission));

        }

    }

}
