package com.nextplugins.testserver.core.manager;

import com.nextplugins.testserver.core.NextTestServer;
import com.nextplugins.testserver.core.api.model.map.CaseInsensitiveLinkedMap;
import com.nextplugins.testserver.core.configuration.LocationValue;
import com.nextplugins.testserver.core.utils.LocationUtils;
import lombok.Getter;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Getter
@Singleton
public final class LocationManager {

    private final CaseInsensitiveLinkedMap<Location> locationMap = new CaseInsensitiveLinkedMap<>();

    public void init() {

        val section = LocationValue.get(LocationValue::section);
        for (val key : section.getKeys(false)) {

            val line = section.getString(key);

            val location = LocationUtils.fromString(line.split(","));
            locationMap.put(key, location);

        }

    }

    public void unload() {

        val file = new File(NextTestServer.getInstance().getDataFolder(), "locations.yml");
        val configuration = YamlConfiguration.loadConfiguration(file);

        for (val entry : locationMap.entrySet()) {
            configuration.set("locations." + entry.getKey(), LocationUtils.toString(entry.getValue()));
        }

        try {
            configuration.save(file);
        } catch (IOException ignored) {
        }

    }

    public Location getLocation(String name) {
        return locationMap.get(name);
    }

}
