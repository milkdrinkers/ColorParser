package io.github.milkdrinkers.colorparser.common.placeholder;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * An abstract class representing a player in a platform-specific context.
 * This class provides basic player information such as UUID and name.
 *
 * @since 4.0.0
 */
public abstract class PlatformPlayer {
    private final UUID uuid;
    private final String name;

    public PlatformPlayer(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Gets the UUID of the player.
     *
     * @return The UUID of the player.
     * @since 4.0.0
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     * @since 4.0.0
     */
    public String getName() {
        return name;
    }
}
