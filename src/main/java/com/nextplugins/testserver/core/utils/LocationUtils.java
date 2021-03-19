package com.nextplugins.testserver.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class LocationUtils {

    public static Location fromString(String... s) {
        return new Location(
                Bukkit.getWorld(s[0]),
                Double.parseDouble(s[1]),
                Double.parseDouble(s[2]),
                Double.parseDouble(s[3]),
                Float.parseFloat(s[4]),
                Float.parseFloat(s[5])
        );
    }

    public static String toString(Location l) {
        return l.getWorld().getName() + ","
                + l.getX() + ","
                + l.getY() + ","
                + l.getZ() + ","
                + l.getYaw() + ","
                + l.getPitch();
    }

}
