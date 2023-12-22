package com.github.milkdrinkers.colorparser;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Stream;

public class SmallCapsTag extends AbstractCharacterChangingTag {
    private static final String GRADIENT = "gradient";

    static final TagResolver RESOLVER = TagResolver.resolver(GRADIENT, SmallCapsTag::create);

    private int index = 0;

    private double multiplier = 1;

    private final TextColor[] colors;
    private @Range(from = -1, to = 1) double phase;

    static Tag create(final ArgumentQueue args, final Context ctx) {
        double phase = 0;
        final List<TextColor> textColors;
        if (args.hasNext()) {
            textColors = new ArrayList<>();
            while (args.hasNext()) {
                final Tag.Argument arg = args.pop();
                // last argument? maybe this is the phase?
                if (!args.hasNext()) {
                    final OptionalDouble possiblePhase = arg.asDouble();
                    if (possiblePhase.isPresent()) {
                        phase = possiblePhase.getAsDouble();
                        if (phase < -1d || phase > 1d) {
                            throw ctx.newException(String.format("Gradient phase is out of range (%s). Must be in the range [-1.0, 1.0] (inclusive).", phase), args);
                        }
                        break;
                    }
                }

                final TextColor parsedColor = ColorTagResolver.resolveColor(arg.value(), ctx);
                textColors.add(parsedColor);
            }

            if (textColors.size() == 1) {
                throw ctx.newException("Invalid gradient, not enough colors. Gradients must have at least two colors.", args);
            }
        } else {
            textColors = Collections.emptyList();
        }

        return new SmallCapsTag(phase, textColors);
    }

    private SmallCapsTag(final double phase, final List<TextColor> colors) {
//        if (colors.isEmpty()) {
//            this.colors = new TextColor[]{TextColor.color(0xffffff), TextColor.color(0x000000)};
//        } else {
//            this.colors = colors.toArray(new TextColor[0]);
//        }

        if (phase < 0) {
            this.phase = 1 + phase; // [-1, 0) -> [0, 1)
//            Collections.reverse(Arrays.asList(this.colors));
        } else {
            this.phase = phase;
        }
    }

    @Override
    protected void init() {
        // Set a scaling factor for character indices, so that the colours in a gradient are evenly spread across the original text
        // make it so the max character index maps to the maximum colour
        this.multiplier = this.size() == 1 ? 0 : (double) (this.colors.length - 1) / (this.size() - 1);
        this.phase *= this.colors.length - 1;
        this.index = 0;
    }

    @Override
    protected void advanceColor() {
        this.index++;
    }

    @Override
    protected TextColor color() {
        // from [0, this.colors.length - 1], select the position in the gradient
        // we will wrap around in order to preserve an even cycle as would be seen with non-zero phases
        final double position = ((this.index * this.multiplier) + this.phase);
        final int lowUnclamped = (int) Math.floor(position);

        final int high = (int) Math.ceil(position) % this.colors.length;
        final int low = lowUnclamped % this.colors.length;

        return TextColor.lerp((float) position - lowUnclamped, this.colors[low], this.colors[high]);
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
            ExaminableProperty.of("phase", this.phase),
            ExaminableProperty.of("colors", this.colors)
        );
    }

    @Override
    public boolean equals(final @Nullable Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        final SmallCapsTag that = (SmallCapsTag) other;
        return this.index == that.index
            && this.phase == that.phase
            && Arrays.equals(this.colors, that.colors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.index, this.phase);
        result = 31 * result + Arrays.hashCode(this.colors);
        return result;
    }
}
