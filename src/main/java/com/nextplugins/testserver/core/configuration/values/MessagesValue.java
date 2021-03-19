package com.nextplugins.testserver.core.configuration.values;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("messages")
@ConfigFile("messages.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessagesValue implements ConfigurationInjectable {

    @Getter private static final MessagesValue instance = new MessagesValue();

    @ConfigField("motd.normal") private String motd;
    @ConfigField("motd.whitelist") private String motdWhitelist;

    public static <T> T get(Function<MessagesValue, T> function) {
        return function.apply(instance);
    }

}
