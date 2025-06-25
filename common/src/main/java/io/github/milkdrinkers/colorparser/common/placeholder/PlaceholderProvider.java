package io.github.milkdrinkers.colorparser.common.placeholder;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Interface for placeholder providers.
 * Placeholder providers are used to parse placeholders in components.
 * @since 4.0.0
 */
public interface PlaceholderProvider<Context extends SimplePlaceholderContext<?, ?>> {
    /**
     * Enum representing the type of placeholder provider.
     * This is used to determine how a provider parses their placeholders.
     * @since 4.0.0
     */
    enum Type {
        /**
         * Placeholder provider that uses string manipulation.
         * @since 4.0.0
         */
        STRING,

        /**
         * Placeholder provider that uses the MiniMessage API.
         * @since 4.0.0
         */
        MINIMESSAGE
    }

    /**
     * Get the type of this provider
     * @since 4.0.0
     */
    @NotNull Type getType();

    /**
     * Check if this provider is available
     * @since 4.0.0
     */
    boolean isAvailable();

    /**
     * Get the name of this provider
     * @since 4.0.0
     */
    @NotNull String getName();

    /**
     * Get the priority of this provider (higher = more priority)
     * @since 4.0.0
     */
    default int getPriority() {
        return 0;
    }

    /**
     * Parse placeholders in the given string using the context
     * @since 4.0.0
     */
    default @NotNull BiFunction<@NotNull Context, @NotNull String, @NotNull String> getStringResolver() {
        return (context, s) -> s;
    }

    /**
     * Get the tag resolver for this provider
     *
     * @return The tag resolver for this provider
     * @since 4.0.0
     */
    default @NotNull TagResolver getTagResolver(@NotNull Context context) {
        return TagResolver.empty();
    }
}
