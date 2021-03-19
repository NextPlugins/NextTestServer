package com.nextplugins.testserver.core.manager;

import com.google.common.collect.Maps;
import com.nextplugins.testserver.core.configuration.values.LocationValue;
import com.nextplugins.testserver.core.utils.LocationUtils;
import lombok.Getter;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Getter
@Singleton
public final class LocationManager {

    private final Map<String, Location> locationMap = Maps.newLinkedHashMap();;

    public void init() {

        val section = LocationValue.get(LocationValue::section);
        for (String key : section.getKeys(false)) {

            val line = section.getString(key);

            Location location = LocationUtils.fromString(line.split(","));
            locationMap.put(key.toLowerCase(), location);

        }

    }

    public void saveLocations() {

        val file = new File("locations.yml");
        val configuration = YamlConfiguration.loadConfiguration(file);

        for (String key : locationMap.keySet()) {

            val location = locationMap.get(key);
            configuration.set("locations." + key, LocationUtils.toString(location));

        }

        try {
            configuration.save(file);
        }catch (IOException ignored) { }

    }

    public Location getLocation(String name) {
        return locationMap.get(name.toLowerCase());
    }

}
