package io.github.milkdrinkers.colorparser.common.tag;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SmallcapsResolver implements TagResolver {
    private final static @NotNull Map<Character, Character> CHARACTER_MAP;

    static {
        final Map<Character, Character> map = new HashMap<>();
        map.put('a', 'ᴀ');
        map.put('b', 'ʙ');
        map.put('c', 'ᴄ');
        map.put('d', 'ᴅ');
        map.put('e', 'ᴇ');
        map.put('f', 'ғ');
        map.put('g', 'ɢ');
        map.put('h', 'ʜ');
        map.put('i', 'ɪ');
        map.put('j', 'ᴊ');
        map.put('k', 'ᴋ');
        map.put('l', 'ʟ');
        map.put('m', 'ᴍ');
        map.put('n', 'ɴ');
        map.put('o', 'ᴏ');
        map.put('p', 'ᴘ');
        map.put('q', 'ǫ');
        map.put('r', 'ʀ');
        map.put('s', 'ˢ');
        map.put('t', 'ᴛ');
        map.put('u', 'ᴜ');
        map.put('v', 'ᴠ');
        map.put('w', 'ᴡ');
        map.put('x', 'ᴤ');
        map.put('y', 'ʏ');
        map.put('z', 'ᴢ');

        CHARACTER_MAP = Collections.unmodifiableMap(map);
    }

    @Override
    public @Nullable Tag resolve(@NotNull String name, @NotNull ArgumentQueue arguments, @NotNull Context ctx) throws ParsingException {
        if (!has(name))
            return null;

        return TagUtil.modify(string -> {
            if (string == null || string.isEmpty())
                return string;

            final StringBuilder converted = new StringBuilder();

            for (char c : string.toCharArray()) {
                converted.append(CHARACTER_MAP.getOrDefault(Character.toLowerCase(c), c));
            }

            return converted.toString();
        });
    }

    @Override
    public boolean has(@NotNull String name) {
        return name.equals("smallcaps") || name.equals("small");
    }
}
