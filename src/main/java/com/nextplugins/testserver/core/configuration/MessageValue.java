package com.nextplugins.testserver.core.configuration;

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
public final class MessageValue implements ConfigurationInjectable {

    @Getter private static final MessageValue instance = new MessageValue();

    @ConfigField("chatFormat") private String chatFormat;

    @ConfigField("motd.normal") private String motd;
    @ConfigField("motd.whitelist") private String motdWhitelist;

    @ConfigField("tablist.header") private List<String> tablistHeader;
    @ConfigField("tablist.footer") private List<String> tablistFooter;

    @ConfigField("teleport.teleporting") private String teleporting;
    @ConfigField("teleport.teleported") private String teleported;

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }
}
