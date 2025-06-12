package io.github.milkdrinkers.colorparser.common;

import io.github.milkdrinkers.colorparser.common.mock.MockComponentBuilder;
import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngine;
import io.github.milkdrinkers.colorparser.common.mock.engine.MockParserEngineBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@State(Scope.Benchmark)
public class ComponentBuilderBenchmark {
    private static final String SIMPLE_CONTENT = "<red>Hello <bold>World</bold></red>";
    private static final String COMPLEX_CONTENT = "<gradient:red:blue>Complex <hover:show_text:'<green>Tooltip</green>'>hover text</hover> with <click:run_command:'/test'>click</click> and <player_name> placeholder</gradient>";
    private static final String LEGACY_CONTENT = "&cRed &lBold &r&9Blue &o&nUnderlined";

    private MockParserEngine engine;
    private MockComponentBuilder reusableBuilder;
    private List<TagResolver> tagResolvers;

    @Setup(Level.Trial)
    public void setupTrial() {
        engine = new MockParserEngineBuilder().build();
        reusableBuilder = engine.parse(SIMPLE_CONTENT);

        // Pre-create tag resolvers for TagResolver benchmark
        tagResolvers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tagResolvers.add(Placeholder.parsed("tag_" + i, "<color:#ff" + String.format("%04x", i) + ">Tag " + i + "</color>"));
        }
    }

    @Benchmark
    public Component benchmarkSimpleComponentBuilding() {
        final MockComponentBuilder b = engine.parse(SIMPLE_CONTENT);
        return b.build();
    }

    @Benchmark
    public Component benchmarkComplexComponentWithPlaceholders() {
        final MockComponentBuilder b = engine.parse(COMPLEX_CONTENT);
        return b.with("player_name", "<gold>TestPlayer</gold>")
            .with("server_name", "<blue>TestServer</blue>")
            .with("timestamp", "<gray>2025-06-12</gray>")
            .build();
    }

    @Benchmark
    public Component benchmarkLegacyColorProcessing() {
        final MockComponentBuilder b = engine.parse(LEGACY_CONTENT);
        return b.legacy().build();
    }

    @Benchmark
    public Component benchmarkBuilderReuse() {
        reusableBuilder.with("test1", "value1").build();
        return reusableBuilder.with("test2", "value2").build();
    }

    @Benchmark
    public Component benchmarkMemoryAllocation(Blackhole bh) {
        final List<Component> components = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            final MockComponentBuilder b = engine.parse("<red>" + i + "</red>");
            Component component = b.build();
            components.add(component);
            bh.consume(component);
        }

        return components.getLast();
    }

    @Benchmark
    public Component benchmarkTagResolvers() {
        final MockComponentBuilder b = engine.parse(generateContentWithTags(20));
        tagResolvers.forEach(b::tag);
        return b.build();
    }

    @State(Scope.Benchmark)
    public static class PlaceholderState {
        @Param({"1", "5", "10", "25", "50", "100"})
        public int placeholderCount;

        public MockParserEngine engine;
        public String content;

        @Setup(Level.Trial)
        public void setup() {
            engine = new MockParserEngineBuilder().build();
            content = generateContentWithPlaceholders(placeholderCount);
        }
    }

    @Benchmark
    public Component benchmarkScalingWithPlaceholders(PlaceholderState state) {
        final MockComponentBuilder b = state.engine.parse(state.content);

        for (int i = 0; i < state.placeholderCount; i++) {
            b.with("placeholder_" + i, "<color:#" + String.format("%06x", i * 1000) + ">Value " + i + "</color>");
        }

        return b.build();
    }

    private static String generateContentWithPlaceholders(int count) {
        final StringBuilder sb = new StringBuilder("<gradient:red:blue>");
        for (int i = 0; i < count; i++) {
            sb.append("<placeholder_").append(i).append("> ");
        }
        sb.append("</gradient>");
        return sb.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private static String generateContentWithTags(int count) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("<tag_").append(i).append("> ");
        }
        return sb.toString();
    }
}