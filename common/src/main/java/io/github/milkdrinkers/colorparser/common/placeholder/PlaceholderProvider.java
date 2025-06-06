package io.github.milkdrinkers.colorparser.common.placeholder;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

/**
 * Interface for placeholder providers.
 * Placeholder providers are used to parse placeholders in components.
 */
public interface PlaceholderProvider<Context extends SimplePlaceholderContext<?, ?>> {
    /**
     * Enum representing the type of placeholder provider.
     * This is used to determine how a provider parses their placeholders.
     */
    enum Type {
        /**
         * Placeholder provider that uses string manipulation.
         */
        STRING,

        /**
         * Placeholder provider that uses the MiniMessage API.
         */
        MINIMESSAGE
    }

    /**
     * Get the type of this provider
     */
    @NotNull Type getType();

    /**
     * Check if this provider is available
     */
    boolean isAvailable();

    /**
     * Get the name of this provider
     */
    @NotNull String getName();

    /**
     * Get the priority of this provider (higher = more priority)
     */
    default int getPriority() {
        return 0;
    }

    /**
     * Parse placeholders in the given string using the context
     */
    default @NotNull BiFunction<@NotNull Context, @NotNull String, @NotNull String> getStringResolver() {
        return (context, s) -> s;
    }

    /**
     * Get the tag resolver for this provider
     *
     * @return The tag resolver for this provider
     */
    default @NotNull TagResolver getTagResolver(@NotNull Context context) {
        return TagResolver.empty();
    }
}
