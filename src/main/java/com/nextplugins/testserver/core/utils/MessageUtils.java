package com.nextplugins.testserver.core.utils;

import org.bukkit.Bukkit;

import java.util.List;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class MessageUtils {

    public static void sendSoundAndTitle(String message, String sound, int time) {
        Object[] titlePackets = TitleUtils.buildTitlePackets(message, time, time, time);

        Bukkit.getOnlinePlayers().forEach(target -> {
            TitleUtils.sendTitlePacket(target, titlePackets);
            SoundUtils.sendSound(target, sound);
        });
    }

    public static void sendSoundAndTitle(Object[] titlePackets, String sound) {
        Bukkit.getOnlinePlayers().forEach(target -> {
            TitleUtils.sendTitlePacket(target, titlePackets);
            SoundUtils.sendSound(target, sound);
        });
    }

    public static String joinStrings(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String line : list) {
            builder.append(ColorUtil.colored(line)).append("\n");
        }

        return builder.toString();
    }

}