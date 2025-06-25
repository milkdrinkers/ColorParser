package io.github.milkdrinkers.colorparser.common;

import io.github.milkdrinkers.colorparser.common.engine.ParserEngine;
import io.github.milkdrinkers.colorparser.common.placeholder.PlaceholderContext;
import io.github.milkdrinkers.colorparser.common.placeholder.PlatformPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.TagPattern;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generic ComponentBuilder class implemented by each platform for creating Adventure {@link Component}'s.
 * <p>
 * The builder class handles user configurable options as well as the actual parsing of the content into an Adventure Component.
 * It uses the platform-specific ParserEngine to parse the content and apply the placeholders.
 *
 * @param <ColorParser>   The platform-specific {@link ComponentBuilder} type, e.g., BukkitComponentBuilder or PaperComponentBuilder
 * @param <EngineBuilder> The platform-specific {@link ParserEngine.EngineBuilder} type, e.g., BukkitParserEngineBuilder or PaperParserEngineBuilder
 * @param <Engine>        The platform-specific {@link ParserEngine} type, e.g., BukkitParserEngine or PaperParserEngine
 * @param <Context>       The platform-specific {@link PlaceholderContext} type, e.g., BukkitSimplePlaceholderContext or PaperSimplePlaceholderContext
 * @since 4.0.0
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class ComponentBuilder<ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>, EngineBuilder extends ParserEngine.EngineBuilder<ColorParser, EngineBuilder, Engine, Context>, Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>, Context extends PlaceholderContext<? extends PlatformPlayer>> {
    private final @NotNull Engine engine;
    private String content;
    private boolean parseLegacy;
    private final List<TagResolver> placeholders = new ArrayList<>(8);
    private final List<TagResolver> miscTagResolvers = new ArrayList<>(4);

    /**
     * Creates a new ComponentBuilder with the given engine and content.
     *
     * @param engine  The ParserEngine to use
     * @param content The content to parse
     * @since 4.0.0
     */
    public ComponentBuilder(@NotNull Engine engine, @NotNull String content) {
        this.engine = engine;
        this.content = content;
        this.parseLegacy = engine.isParsingLegacy();
    }

    /**
     * Creates and adds a placeholder tag that will be parsed during {@link #build()}.
     *
     * @param key   The placeholder tag name like <code>player_name</code>
     * @param value The value of this tag, a unparsed string like: <code>{@literal "<gold><bold>some text here..."}</code>
     * @return The current builder instance
     * @apiNote The literal {@link String} value is inserted into the MiniMessage content and parsed along with the rest of the content in {@link #build()}.
     * @implNote Uses {@link Placeholder#parsed(String, String)}
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser with(@NotNull @Subst("test_placeholder") @TagPattern String key, @NotNull String value) {
        placeholders.add(
            Placeholder.parsed(
                key,
                value
            )
        );

        return (ColorParser) this;
    }

    /**
     * Creates and adds a placeholder tag that inserts a {@link Component} from the {@link ColorParser}. The value is parsed immediately, before {@link #build()} is called.
     *
     * @param key   The placeholder tag name like <code>player_name</code>
     * @param value The value of this tag, an unbuilt {@link ColorParser} instance which is built immediately
     * @return The current builder instance
     * @implNote Uses {@link Placeholder#component(String, ComponentLike)}
     * @since 4.0.0
     */
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    @NotNull
    public final ColorParser with(@NotNull @Subst("test_placeholder") @TagPattern String key, @NotNull ColorParser value) {
        placeholders.add(
            Placeholder.component(
                key,
                value.build()
            )
        );

        return (ColorParser) this;
    }

    /**
     * Creates and adds a placeholder tag that inserts a {@link Component}. The value is parsed immediately, before {@link #build()} is called.
     *
     * @param key   The placeholder tag name like <code>player_name</code>
     * @param value The value of this tag, an Adventure {@link ComponentLike}
     * @return The current builder instance
     * @implNote Uses {@link Placeholder#component(String, ComponentLike)}
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser with(@NotNull @Subst("test_placeholder") @TagPattern String key, @NotNull ComponentLike value) {
        placeholders.add(
            Placeholder.component(
                key,
                value
            )
        );
        return (ColorParser) this;
    }

    /**
     * Adds a placeholder tag that will be present in the final {@link Component}.
     *
     * @param resolver The tag resolver to add
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser with(@NotNull TagResolver resolver) {
        placeholders.add(resolver);
        return (ColorParser) this;
    }

    /**
     * Adds placeholder tags that will be present in the final {@link Component}.
     *
     * @param resolvers The tag resolvers to add
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser with(@NotNull Collection<TagResolver> resolvers) {
        placeholders.addAll(resolvers);
        return (ColorParser) this;
    }

    /**
     * Adds placeholder tags that will be present in the final {@link Component}.
     *
     * @param resolvers The tag resolvers to add
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser with(@NotNull TagResolver... resolvers) {
        Collections.addAll(placeholders, resolvers);
        return (ColorParser) this;
    }

    /**
     * Enables legacy color codes parsing like {@literal §} and {@literal &} into their minimessage equivalents.
     *
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser legacy() {
        this.parseLegacy = true;
        return (ColorParser) this;
    }

    /**
     * Sets whether to parse legacy color codes like {@literal §} and {@literal &} into their minimessage equivalents.
     *
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser legacy(final boolean shouldParseLegacy) {
        this.parseLegacy = shouldParseLegacy;
        return (ColorParser) this;
    }

    /**
     * Adds a custom tag resolver that will be used in the final {@link Component}.
     * <p>
     * This is useful for adding custom tags that are not placeholders, such as custom tags for MiniMessage.
     *
     * @param tagResolver The tag resolver to add
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser tag(@NotNull TagResolver tagResolver) {
        this.miscTagResolvers.add(tagResolver);
        return (ColorParser) this;
    }

    /**
     * Adds multiple custom tag resolvers that will be used in the final {@link Component}.
     * <p>
     * This is useful for adding custom tags that are not placeholders, such as custom tags for MiniMessage.
     *
     * @param tagResolvers The collection of tag resolvers to add
     * @return The current builder instance
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public final ColorParser tag(@NotNull Collection<TagResolver> tagResolvers) {
        this.miscTagResolvers.addAll(tagResolvers);
        return (ColorParser) this;
    }

    /**
     * Builds the final Adventure {@link Component} with all placeholders and custom tags resolved.
     *
     * @return Your parsed Adventure {@link Component} with all placeholders and custom tags resolved.
     * @since 4.0.0
     */
    @NotNull
    public Component build() {
        final String text = parseLegacy ? getEngine().getLegacyColorsProcessor().process(content) : content;

        // Merge all custom tag resolvers into a single array
        final int totalResolvers = placeholders.size() + miscTagResolvers.size();
        final TagResolver[] resolvers = new TagResolver[totalResolvers];

        System.arraycopy(placeholders.toArray(new TagResolver[0]), 0, resolvers, 0, placeholders.size());
        System.arraycopy(miscTagResolvers.toArray(new TagResolver[0]), 0, resolvers, placeholders.size(), miscTagResolvers.size());


        return getEngine().getMiniMessage().deserialize(text, resolvers); // Parse the final string to a Component
    }

    /**
     * Get the parser engine.
     *
     * @return The parser engine
     * @since 4.0.0
     */
    protected final @NotNull Engine getEngine() {
        return engine;
    }

    /**
     * Get the content.
     *
     * @return The content
     * @since 4.0.0
     */
    protected final String getContent() {
        return content;
    }

    /**
     * Sets the content to be parsed.
     *
     * @param content The content to parse
     * @return The current builder instance
     * @apiNote This overrides the current content, use with caution.
     * @since 4.0.0
     */
    @SuppressWarnings({"unchecked", "UnusedReturnValue"})
    @NotNull
    protected final ColorParser setContent(final @NotNull String content) {
        this.content = content;
        return (ColorParser) this;
    }
}