package io.github.milkdrinkers.colorparser.common.placeholder;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface representing a context for placeholders that can be used in various platforms.
 * This context provides information about players involved in the placeholder resolution.
 *
 * @param <T1> The type of the platform-specific player object implementation.
 * @param <T2> The type of the platform-specific player object implementation.
 * @since 4.0.0
 */
public interface PlaceholderContext<T1, T2> {
    /**
     * Enum representing the way this placeholder should resolved in this context.
     * @since 4.0.0
     */
    enum Type {
        GLOBAL,
        PLAYER,
        RELATIONAL,
    }

    /**
     * Get the type of the placeholder context
     * @since 4.0.0
     */
    @NotNull Type getType();

    /**
     * Get the player 1 UUID if available
     * @since 4.0.0
     */
    Optional<UUID> getPlayer1UUID();

    /**
     * Get the player 1 name if available
     * @since 4.0.0
     */
    Optional<String> getPlayer1Name();

    /**
     * Get the player 2 UUID if available
     * @since 4.0.0
     */
    Optional<UUID> getPlayer2UUID();

    /**
     * Get the player 2 name if available
     * @since 4.0.0
     */
    Optional<String> getPlayer2Name();

    /**
     * Get the platform-specific player 1 object
     * @since 4.0.0
     */
    Optional<T1> getPlatform1Player();

    /**
     * Get the platform-specific player 2 object
     * @since 4.0.0
     */
    Optional<T2> getPlatform2Player();
}
