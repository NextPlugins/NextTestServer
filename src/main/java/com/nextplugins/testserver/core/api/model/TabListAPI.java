package com.nextplugins.testserver.core.api.model;

import com.nextplugins.testserver.core.utils.ClazzContainer;
import com.nextplugins.testserver.core.utils.ColorUtil;
import com.nextplugins.testserver.core.utils.ReflectionUtils;
import com.nextplugins.testserver.core.utils.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * The API methods for TabList.
 * 
 * @author montlikadani
 */
public final class TabListAPI {

	private static java.lang.reflect.Constructor<?> playerListHeaderFooterConstructor;
	private static Field headerField, footerField;

	public static Object packet;

	static {
		Class<?> playerListHeaderFooter = null;

		try {
			playerListHeaderFooter = ReflectionUtils.getPacketClass("net.minecraft.network.protocol.game",
					"PacketPlayOutPlayerListHeaderFooter");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (playerListHeaderFooter != null) {
			try {
				playerListHeaderFooterConstructor = playerListHeaderFooter.getConstructor();
			} catch (NoSuchMethodException s) {
				try {
					playerListHeaderFooterConstructor = playerListHeaderFooter.getConstructor(
							ClazzContainer.getIChatBaseComponent(), ClazzContainer.getIChatBaseComponent());
				} catch (NoSuchMethodException e) {
					try {
						playerListHeaderFooterConstructor = playerListHeaderFooter
								.getConstructor(ClazzContainer.getIChatBaseComponent());
					} catch (NoSuchMethodException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public static void init(String header, String footer) {
		if (header == null) header = "";
		if (footer == null) footer = "";

		if (ServerVersion.isCurrentEqualOrLower(ServerVersion.v1_15_R2)) {
			header = ColorUtil.colored(header);
			footer = ColorUtil.colored(footer);
		}

		Object tabHeader, tabFooter;
		try {
			tabHeader = ReflectionUtils.getAsIChatBaseComponent(header);
			tabFooter = ReflectionUtils.getAsIChatBaseComponent(footer);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		try {
			if (ServerVersion.isCurrentEqualOrHigher(ServerVersion.v1_17_R1)) {
				packet = playerListHeaderFooterConstructor.newInstance(tabHeader, tabFooter);
			} else {
				packet = playerListHeaderFooterConstructor.newInstance();

				if (ServerVersion.isCurrentEqualOrHigher(ServerVersion.v1_13_R2)) {
					if (headerField == null) {
						(headerField = packet.getClass().getDeclaredField("header")).setAccessible(true);
					}

					if (footerField == null) {
						(footerField = packet.getClass().getDeclaredField("footer")).setAccessible(true);
					}
				} else {
					if (headerField == null) {
						(headerField = packet.getClass().getDeclaredField("a")).setAccessible(true);
					}

					if (footerField == null) {
						(footerField = packet.getClass().getDeclaredField("b")).setAccessible(true);
					}
				}

				headerField.set(packet, tabHeader);
				footerField.set(packet, tabFooter);
			}

		} catch (Exception f) {
			try {
				try {
					packet = playerListHeaderFooterConstructor.newInstance(tabHeader);
				} catch (IllegalArgumentException e) {
					try {
						packet = playerListHeaderFooterConstructor.newInstance();
					} catch (IllegalArgumentException ex) {
					}
				}

				if (packet != null) {
					if (footerField == null) {
						(footerField = packet.getClass().getDeclaredField("b")).setAccessible(true);
					}

					footerField.set(packet, tabFooter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendTabList(Player player) {
		ReflectionUtils.sendPacket(player, packet);

	}

}