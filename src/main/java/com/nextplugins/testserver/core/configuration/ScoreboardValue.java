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

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigSection("scoreboard")
@ConfigFile("scoreboard.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScoreboardValue implements ConfigurationInjectable {

    @Getter private static final ScoreboardValue instance = new ScoreboardValue();

    @ConfigField("title") private String title;
    @ConfigField("lines") private List<String> lines;

    public static <T> T get(Function<ScoreboardValue, T> function) {
        return function.apply(instance);
    }

}
