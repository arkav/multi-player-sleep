package dev.arkav.mpsleep;

import com.google.common.collect.Lists;
import dev.arkav.oneliners.AutoConfig;

import java.util.List;

public class MPSleepConfig {
    public static MPSleepConfig INSTANCE = new AutoConfig<>(MPSleepConfig.class, "config/mpsleep.config.json").get();
    public List<String> messages = Lists.newArrayList(
            "%s went to bed. Sweet dreams!",
            "%s hit the hay.",
            "Good morning %s, did you sleep well?"
    );
}
