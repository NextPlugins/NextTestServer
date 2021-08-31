package com.nextplugins.testserver.core.api.model.group.command;

import com.google.inject.Inject;
import com.nextplugins.testserver.core.api.model.group.Group;
import com.nextplugins.testserver.core.api.model.group.storage.GroupStorage;
import com.nextplugins.testserver.core.api.model.group.utils.GroupUtils;
import com.nextplugins.testserver.core.api.model.player.storage.UserStorage;
import com.nextplugins.testserver.core.api.model.player.utils.UserUtil;
import com.nextplugins.testserver.core.utils.ColorUtil;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class GroupCommand {

    private static final String GROUP_INFO = " &a%sº&f: &7%s &8- %s &8- &c%s permissões";

    @Inject private GroupStorage groupStorage;
    @Inject private UserStorage userStorage;

    @Command(
            name = "setgrupo",
            permission = "nextcore.setgroup",
            usage = "setgrupo <jogador> <grupo>",
            async = true
    )
    public void onSetGroupCommand(Context<CommandSender> context,
                                  OfflinePlayer player,
                                  String groupName) {

        val sender = context.getSender();

        val group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cGrupo inexistente."
            ));
            return;


        }

        val account = userStorage.findAccount(player);
        if (account == null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cOcorreu um erro, tente novamente quando este jogador entrar no servidor."
            ));
            return;


        }

        UserUtil.changeGroup(account, group);

        sender.sendMessage(ColorUtil.colored(
                "&aGrupo do jogador &f" + player.getName() + " &aatualizado com sucesso."
        ));

    }

    @Command(
            name = "grupo",
            aliases = {"editgrupo", "editargrupo"},
            permission = "nextcore.editgrupo",
            async = true
    )
    public void onGroupCommand(Context<CommandSender> context) {

        CommandSender sender = context.getSender();
        sender.sendMessage(ColorUtil.colored(
                "",
                "&aNextCore &7- &fEditar grupo",
                "",
                " &a/grupo listar &7- &fVer todos os grupos existentes",
                " &a/grupo criar <grupo> <nome colorido> &7- &fCriar um novo grupo",
                " &a/grupo delete <grupo> &7- &fDeletar um novo grupo",
                " &a/grupo add <grupo> <permissão> &7- &fAdicionar permissão à um grupo",
                " &a/grupo remove <grupo> <permissão> &7- &fRemover permissão de um grupo",
                " &a/grupo perms <grupo> &7- &fVer as permissão de um grupo",
                ""
        ));

    }

    @Command(
            name = "grupo.listar",
            aliases = {"list"},
            permission = "nextcore.group.list",
            async = true
    )
    public void onGroupList(Context<CommandSender> context) {

        context.sendMessage(ColorUtil.colored(
                "",
                " &fTodos os &6grupos &fdo servidor &8(" + groupStorage.getGroupNames().size() + ")&7:",
                ""
        ));

        for (int i = 0; i < groupStorage.getGroupNames().size(); i++) {

            String groupName = groupStorage.getGroupNames().get(i);
            Group group = groupStorage.getGroupByName(groupName);

            context.sendMessage(ColorUtil.colored(String.format(
                    GROUP_INFO,
                    i + 1, group.getName(), group.getPrefix(), group.getPermissions().size()
            )));

        }
    }

    @Command(
            name = "grupo.criar",
            aliases = {"create"},
            permission = "nextcore.criargrupo",
            usage = "grupo criar <nome> <prefix>",
            async = true
    )
    public void onGroupCreate(Context<CommandSender> context,
                              String groupName,
                              String coloredName) {

        val sender = context.getSender();
        if (groupStorage.getGroupByName(groupName) != null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cEste grupo já existe."
            ));
            return;

        }

        val group = Group.createDefault(groupName, coloredName);
        this.groupStorage.register(group);

        sender.sendMessage(ColorUtil.colored(
                "&aGrupo criado com sucesso."
        ));

    }

    @Command(
            name = "grupo.delete",
            aliases = {"deletar"},
            permission = "nextcore.group.delete",
            usage = "grupo deletar <grupo>",
            async = true
    )
    public void onGroupDelete(Context<CommandSender> context,
                              String groupName) {

        val sender = context.getSender();
        val group = groupStorage.getGroupByName(groupName);

        if (group == null) {

            sender.sendMessage(ColorUtil.colored(
                    "&cEste grupo não existe."
            ));
            return;

        }

        this.groupStorage.unregister(group.getName());

        sender.sendMessage(ColorUtil.colored(
                "&aGrupo deletado com sucesso."
        ));

    }

    @Command(
            name = "grupo.add",
            aliases = {"adicionar"},
            permission = "nextcore.editgrupo",
            usage = "grupo add <grupo> <permissao>",
            async = true
    )
    public void onAddPermissionOnGroup(Context<CommandSender> context,
                                       String groupName,
                                       String permission) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtil.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtil.colored("&c" + groupStorage.getGroupNames()));
            return;

        }

        val success = GroupUtils.addPermission(group, permission);
        if (success) {

            sender.sendMessage(ColorUtil.colored("&aVocê adicionou a permissão &f" + permission + " &aao grupo " + group.getPrefix() + " &acom sucesso."));
            return;

        }

        sender.sendMessage(ColorUtil.colored("&cEsta permissão já é presente no grupo."));

    }

    @Command(
            name = "grupo.remove",
            aliases = {"rem"},
            permission = "nextcore.group.remove",
            usage = "group remove <grupo> <permissao>",
            async = true
    )
    public void onRemovePermissionOnGroup(Context<CommandSender> context,
                                          String groupName,
                                          String permission) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtil.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtil.colored("&c" + groupStorage.getGroupNames()));
            return;

        }

        val success = GroupUtils.removePermission(group, permission);
        if (success) {

            sender.sendMessage(ColorUtil.colored("&aVocê removeu a permissão &f" + permission + " &aao grupo " + group.getPrefix() + " &acom sucesso."));
            return;

        }

        sender.sendMessage(ColorUtil.colored("&cEsta permissão não faz parte deste grupo."));

    }

    @Command(
            name = "grupo.perms",
            aliases = {"permissoes"},
            permission = "nextcore.group.permissions",
            usage = "grupo perms <grupo>",
            async = true
    )
    public void onListPermissionGroup(Context<CommandSender> context,
                                      String groupName) {

        val sender = context.getSender();

        Group group = groupStorage.getGroupByName(groupName);
        if (group == null) {

            sender.sendMessage(ColorUtil.colored("&cEste grupo não existe, grupos válidos:"));
            sender.sendMessage(ColorUtil.colored("&c" + groupStorage.getGroupNames()));
            return;

        }

        sender.sendMessage(ColorUtil.colored("&aTodas as permissões do grupo " + group.getPrefix() + "&a:", ""));

        for (int i = 0; i < group.getPermissions().size(); i++) {

            val permission = group.getPermissions().get(i);
            sender.sendMessage(ColorUtil.colored("&8" + (i + 1) + "º: &f" + permission));

        }

    }

}
