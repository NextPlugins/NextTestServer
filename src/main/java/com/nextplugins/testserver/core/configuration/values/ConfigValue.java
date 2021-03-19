package com.nextplugins.testserver.core.configuration.values;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.function.Function;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigValue implements ConfigurationInjectable {

    @Getter private static final ConfigValue instance = new ConfigValue();

    @ConfigField("github.username") private String githubUsername;
    @ConfigField("github.accessToken") private String githubAccessToken;

    public static <T> T get(Function<ConfigValue, T> function) {
        return function.apply(instance);
    }

}
