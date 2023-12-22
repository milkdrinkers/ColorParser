package com.github.milkdrinkers.colorparser;

// TODO Use https://docs.advntr.dev/minimessage/api.html#tag-resolvers to create a resolver that replaces text with small-caps like: https://small.text-generator.org/minecraft
// Python version: https://github.com/gvoxry/SmallCapsConverter/blob/main/SmallCapsConverter.py

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SmallTag {
    private static final List<String> STOP_PARSING = new ArrayList<>();

    private static final char[] SMALL_CAPS_ALPHABET = "\u1D00\u0299\u1D04\u1D05\u1D07\uA730\u0262\u029C\u026A\u1D0A\u1D0B\u029F\u1D0D\u0274\u1D0F\u1D18\u01EB\u0280\u0455\u1D1B\u1D1C\u1D20\u1D21x\u028F\u1D22".toCharArray();
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    private String test(String original) {
        char[] newString = original.toCharArray();
        final char[] comparable = original.toLowerCase().toCharArray();

        List<Character> list = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        for (int i = 0; i < newString.length; i++) {
            char character = newString[i];
            final char comparableCharacter = comparable[i];
            final boolean shouldReplace = list.contains(comparableCharacter);

            if (shouldReplace)
                // TODO Use hashmap mapped from lowercase alphabet to small caps here
        }
        for (char c : comparable) {
            final boolean shouldReplace = list.contains(c);


            original[1]
        }

    }

    private static String toSmallCaps(String text) {
        final int length = text.length();

        StringBuilder smallCaps = new StringBuilder(length);

        for(int i=0; i<length; ++i) {
            char c = text.charAt(i);
            if(c >= 'a' && c <= 'z') {
                smallCaps.append(SMALL_CAPS_ALPHABET[c - 'a']);
            } else {
                smallCaps.append(c);
            }
        }

        return smallCaps.toString();
    }

    private static String convertStringArrayToString(String[] strArr) {
        StringBuilder sb = new StringBuilder();

        for (String str : strArr)
            sb.append(str);

        return sb.toString();
    }

    public static String getStopParsing(String string) {
//        final String test = "<small>My Nome daurk &rheheheh";
//        final String test2 = "<small>My Nome daurk heheheh</small>";

//        test.replaceFirst()
        final Pattern LEGACY_REGEX = Pattern.compile("[<small>](.*)");

        final String[] matcher = LEGACY_REGEX.split(string, 1);

        if (matcher.length > 1) {
            for (int i = 1; i < matcher.length + 1;) {
                matcher[i] = toSmallCaps(matcher[i]);
            }
        }

        return convertStringArrayToString(matcher);

        /*while (matcher.find()) {
            final String match = matcher.group();
            textParsed = textParsed.replace(match, ColorHolder.getInstance().getLegacyToMiniMessage().getOrDefault(match, match));
        }*/


        /*List<String> STOP_PARSING = List.of(
            "§0",
            "§1",
            "§2",
            "§3",
            "§4",
            "§5",
            "§6",
            "§7",
            "§8",
            "§9",
            "§a",
            "§b",
            "§c",
            "§d",
            "§e",
            "§f",
            "§r",
            "&0",
            "&1",
            "&2",
            "&3",
            "&4",
            "&5",
            "&6",
            "&7",
            "&8",
            "&9",
            "&a",
            "&b",
            "&c",
            "&d",
            "&e",
            "&f",
            "&r",
            "<black>",
            "<dark_blue>",
            "<dark_green>",
            "<dark_aqua>",
            "<dark_red>",
            "<dark_purple>",
            "<gold>",
            "<gray>",
            "<dark_gray>",
            "<blue>",
            "<green>",
            "<aqua>",
            "<red>",
            "<light_purple>",
            "<yellow>",
            "<white>",
            "<reset>"
        );*/
    }

    /*private static final Map<String, String> STOP_PARSING = Map.ofEntries(
        Map.entry("§0", "<black>"),
        Map.entry("§1", "<dark_blue>"),
        Map.entry("§2", "<dark_green>"),
        Map.entry("§3", "<dark_aqua>"),
        Map.entry("§4", "<dark_red>"),
        Map.entry("§5", "<dark_purple>"),
        Map.entry("§6", "<gold>"),
        Map.entry("§7", "<gray>"),
        Map.entry("§8", "<dark_gray>"),
        Map.entry("§9", "<blue>"),
        Map.entry("§a", "<green>"),
        Map.entry("§b", "<aqua>"),
        Map.entry("§c", "<red>"),
        Map.entry("§d", "<light_purple>"),
        Map.entry("§e", "<yellow>"),
        Map.entry("§f", "<white>"),
        Map.entry("§r", "<reset>"),
        Map.entry("&0", "<black>"),
        Map.entry("&1", "<dark_blue>"),
        Map.entry("&2", "<dark_green>"),
        Map.entry("&3", "<dark_aqua>"),
        Map.entry("&4", "<dark_red>"),
        Map.entry("&5", "<dark_purple>"),
        Map.entry("&6", "<gold>"),
        Map.entry("&7", "<gray>"),
        Map.entry("&8", "<dark_gray>"),
        Map.entry("&9", "<blue>"),
        Map.entry("&a", "<green>"),
        Map.entry("&b", "<aqua>"),
        Map.entry("&c", "<red>"),
        Map.entry("&d", "<light_purple>"),
        Map.entry("&e", "<yellow>"),
        Map.entry("&f", "<white>"),
        Map.entry("&r", "<reset>")
    );*/

//
//    private static final String SMALL = "small";
//
//    public SmallTag () {
//        Tag.selfClosingInserting(() -> ),
//
//            /*Tag.styling(
//
//            )*/
//
//        TagResolver.builder().tag("small",  Tag.preProcessParsed())
//    }
//
//    static final TagResolver RESOLVER = SerializableResolver.claimingStyle(
//        SMALL,
//            SmallTag::create,
//        StyleClaim.claim(SMALL, Style::configureAndBuild, SmallTag::emit)
//        );
//
//    /*static final TagResolver MEME = TokenEmitter(
//        SMALL,
//        SmallTag::create,
//        StyleClaim.claim(SMALL, SmallTag::emit)
//    );*/
//    //TagResolver.resolver(RESET, ParserDirective.RESET); // Not serializable -- we don't reeealy want to encourage its use
//
//    private SmallTag() {
//    }
//
//    static Tag create(final ArgumentQueue args, final Context ctx) throws ParsingException {
//
//        /*final Key font;
//        @Subst("empty") final String valueOrNamespace = args.popOr("A font tag must have either arguments of either <value> or <namespace:value>").value();
//        try {
//            if (!args.hasNext()) {
//                font = Key.key(valueOrNamespace);
//            } else {
//                @Subst("empty") final String fontKey = args.pop().value();
//                font = Key.key(valueOrNamespace, fontKey);
//            }
//        } catch (final InvalidKeyException ex) {
//            throw ctx.newException(ex.getMessage(), args);
//        }*/
//        args.
//        return Tag.styling(builder -> builder.font(font));
//    }
//
//    static void emit(final Key font, final TokenEmitter emitter) {
//        if (font.namespace().equals(Key.MINECRAFT_NAMESPACE)) {
//            emitter.argument(font.value());
//        } else {
//            emitter.arguments(font.namespace(), font.value());
//        }
//    }
}
