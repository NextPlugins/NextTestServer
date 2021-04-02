package com.nextplugins.testserver.core.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Function;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("locations.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocationValue implements ConfigurationInjectable {

    @Getter private static final LocationValue instance = new LocationValue();

    @ConfigField("locations") private ConfigurationSection section;

    public static <T> T get(Function<LocationValue, T> function) {
        return function.apply(instance);
    }

}
