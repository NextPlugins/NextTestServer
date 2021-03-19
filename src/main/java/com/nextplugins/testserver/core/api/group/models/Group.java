package com.nextplugins.testserver.core.api.group.models;

import com.nextplugins.testserver.core.utils.ColorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Group {

    MEMBER('i', 0, "Membro", ColorUtils.colored("&a[Membro]")),
    CLIENT('h', 1, "Cliente", ColorUtils.colored("&b[Cliente]")),
    BOOSTER('g', 2, "Booster", ColorUtils.colored("&d[Booster]")),
    PARTNER('f', 3, "Parceiro", ColorUtils.colored("&c[Parceiro]")),
    YOUTUBER('e', 4, "Youtuber", ColorUtils.colored("&c[Youtuber]")),
    HELPER('d', 5, "Suporte", ColorUtils.colored("&6[Suporte]")),
    ADMIN('c', 6, "Admin", ColorUtils.colored("&4[Admin]")),
    NEXT('b', 7, "Next", ColorUtils.colored("&3[Next]")),
    CONSOLE('a', 8, "Console", ColorUtils.colored("&e[Console]"));

    public final char identificator;
    public final int priority;
    public final String name, tag;

    public static Group getByName(String name) {
        return Arrays.stream(Group.values()).filter(group -> group.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Group getByPrority(int position) {
        return Arrays.stream(Group.values()).filter(group -> group.priority == position).findFirst().orElse(null);
    }

    public String getTeam() { return identificator + name; }

    public static Group getByPlayer(CommandSender sender) {

        if (sender instanceof ConsoleCommandSender) return Group.CONSOLE;
        return Group.MEMBER;

    }

    public boolean hasPermission(Group minimum) {
        return priority >= minimum.priority;
    }

}
