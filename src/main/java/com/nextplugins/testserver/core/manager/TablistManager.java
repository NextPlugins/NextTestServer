package com.nextplugins.testserver.core.manager;

import com.nextplugins.testserver.core.configuration.MessageValue;
import com.nextplugins.testserver.core.utils.MessageUtils;
import com.nextplugins.testserver.core.utils.PacketUtils;
import lombok.val;
import org.bukkit.entity.Player;

import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Singleton
public class TablistManager {

    private Object packet;

    public void init() {

        try {

            val header = MessageValue.get(MessageValue::tablistHeader);
            val footer = MessageValue.get(MessageValue::tablistFooter);

            Object tabHeader = buildComponentPacket(MessageUtils.joinStrings(header));
            Object tabFooter = buildComponentPacket(MessageUtils.joinStrings(footer));

            Constructor<?> titleConstructor = PacketUtils.getNMSClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor(PacketUtils.getNMSClass("IChatBaseComponent"));

            packet = titleConstructor.newInstance(tabHeader);

            Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabFooter);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendTablist(Player player) {
        PacketUtils.sendPacket(player, packet);
    }

    private Object buildComponentPacket(String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return PacketUtils.getNMSClass("IChatBaseComponent")
                .getDeclaredClasses()[0]
                .getMethod("a", String.class)
                .invoke(null, "{\"text\":\"" + message + "\"}");

    }

}
