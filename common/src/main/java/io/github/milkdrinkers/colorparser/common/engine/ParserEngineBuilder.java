package io.github.milkdrinkers.colorparser.common.engine;

import io.github.milkdrinkers.colorparser.common.ComponentBuilder;
import io.github.milkdrinkers.colorparser.common.placeholder.SimplePlaceholderContext;
import io.github.milkdrinkers.colorparser.common.tag.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

/**
 * Builder for creating customized ParserEngine instances.
 */
public abstract class ParserEngineBuilder<ColorParser extends ComponentBuilder<ColorParser, EngineBuilder, Engine, Context>, EngineBuilder extends ParserEngineBuilder<ColorParser, EngineBuilder, Engine, Context>, Engine extends ParserEngine<ColorParser, EngineBuilder, Engine, Context>, Context extends SimplePlaceholderContext<?, ?>> {
    protected MiniMessage miniMessage;
    protected boolean parseLegacy = true;

    /**
     * Sets a custom MiniMessage instance to use.
     *
     * @param miniMessage The MiniMessage instance
     * @return This builder
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public EngineBuilder miniMessage(@NotNull MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
        return (EngineBuilder) this;
    }

    /**
     * Sets whether this engine parses legacy message format.
     *
     * @param value Whether to parse legacy format or not
     * @return This builder
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public EngineBuilder parseLegacy(boolean value) {
        this.parseLegacy = value;
        return (EngineBuilder) this;
    }

    /**
     * Executes default build logic for the engine.
     */
    protected void buildInternal() {
        if (miniMessage == null) {
            final MiniMessage.Builder miniMessageBuilder = MiniMessage.builder()
                .strict(false)
                .editTags(
                    builder -> builder.resolvers(
                        TagResolver.standard(),
                        new UppercaseResolver(),
                        new LowercaseResolver(),
                        new NumberResolver(),
                        new PluralResolver(),
                        new ChoiceResolver(),
                        new TrimResolver(),
                        new LighterResolver(),
                        new SmallcapsResolver()
                    ).build()
                );

            miniMessage = miniMessageBuilder.build();
        }
    }

    /**
     * Builds the ParserEngine instance.
     *
     * @return The built ParserEngine instance
     */
    public abstract Engine build();
}