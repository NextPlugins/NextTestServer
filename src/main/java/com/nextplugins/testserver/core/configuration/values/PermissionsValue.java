package com.nextplugins.testserver.core.configuration.values;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
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
@Accessors(fluent = true)
@ConfigFile("groups.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionsValue implements ConfigurationInjectable {

    @Getter private static final PermissionsValue instance = new PermissionsValue();

    @ConfigField("groups") private ConfigurationSection section;

    public static <T> T get(Function<PermissionsValue, T> function) {
        return function.apply(instance);
    }

}

