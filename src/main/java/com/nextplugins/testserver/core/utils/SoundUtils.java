package com.nextplugins.testserver.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SoundUtils {

    public static void sendSound(Player player, String soundName) {

        Sound sound;
        try {
            sound = Sound.valueOf(soundName);
        } catch (Exception exception) {
            sound = Sound.BLOCK_NOTE_BLOCK_PLING;
        }

        sendSound(player, sound);

    }

    public static void sendSound(Player player, Sound sound) {
        sendSound(player, sound, 0.5f, 1f);
    }

    public static void sendSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

}
