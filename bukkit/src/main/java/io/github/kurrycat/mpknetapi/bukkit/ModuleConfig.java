package io.github.kurrycat.mpknetapi.bukkit;

import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ModuleConfig {
    private final Mode mode;
    private final List<String> entries;
    private final boolean failKick, failNotify;
    private final int failDelay;
    private final String failKickMessage;
    private final String failNotifyMessage;

    public ModuleConfig(Configuration config) {
        this(
            Mode.valueOf(config.getString("modules.mode", "blacklist").toUpperCase()),
            config.getStringList("modules.entries"),
            config.getBoolean("modules.fail.kick"),
            config.getBoolean("modules.fail.notify"),
            config.getInt("modules.fail.delay"),
            config.getString("modules.fail.kick-message").replace("\\n", "\n"),
            config.getString("modules.fail.notify-message").replace("\\n", "\n")
        );
    }

    public ModuleConfig(Mode mode, List<String> entries, boolean failKick, boolean failNotify, int failDelay, String failKickMessage, String failNotifyMessage) {
        this.mode = mode;
        this.entries = entries;
        this.failKick = failKick;
        this.failNotify = failNotify;
        this.failDelay = failDelay;
        this.failKickMessage = failKickMessage;
        this.failNotifyMessage = failNotifyMessage;
    }

    public boolean checkModuleCompliance(List<String> modules) {
        return getIncompliantModules(modules).isEmpty();
    }

    public List<String> getIncompliantModules(List<String> modules) {
        switch (mode) {
            case DISABLED:
                break;

            case BLACKLIST:
                return modules.stream().filter(entries::contains).collect(Collectors.toList());

            case WHITELIST:
                return modules.stream().filter(m -> !entries.contains(m)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public enum Mode {
        BLACKLIST,
        WHITELIST,
        DISABLED
    }

    public Mode getMode() {
        return mode;
    }

    public List<String> getEntries() {
        return entries;
    }

    public boolean isFailKick() {
        return failKick;
    }

    public boolean isFailNotify() {
        return failNotify;
    }

    public int getFailDelay() {
        return failDelay;
    }

    public String getFailKickMessage() {
        return failKickMessage;
    }

    public String getFailNotifyMessage() {
        return failNotifyMessage;
    }
}
