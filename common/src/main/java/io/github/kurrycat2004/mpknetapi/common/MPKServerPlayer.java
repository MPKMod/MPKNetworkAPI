package io.github.kurrycat2004.mpknetapi.common;

import java.util.List;
import java.util.UUID;

public class MPKServerPlayer {
    private final UUID uuid;
    public List<String> loadedModules;

    public MPKServerPlayer(UUID uuid, List<String> loadedModules) {
        this.uuid = uuid;
        this.loadedModules = loadedModules;
    }

    public UUID getUuid() {
        return uuid;
    }
}
