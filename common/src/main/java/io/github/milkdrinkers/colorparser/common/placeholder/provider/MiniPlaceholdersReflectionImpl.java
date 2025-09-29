package io.github.milkdrinkers.colorparser.common.placeholder.provider;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Used by MiniPlaceholders Placeholder Providers to handle reflection and multi-version support.
 */
public abstract class MiniPlaceholdersReflectionImpl {
    private enum Version {
        V3,
        V2V1,
        UNKNOWN
    }

    private volatile boolean initialized = false;
    private volatile Version version = Version.UNKNOWN;
    private volatile @Nullable Method global = null;
    private volatile @Nullable Method audience = null;
    private volatile @Nullable Method placeholders = null;

    /**
     * Lazily initializes fields for MiniPlaceholders usage.
     * This also implements code to make usage of MiniPlaceholders version agnostic between V2 and V3.
     */
    protected void lazyLoad() {
        // Lazily do reflection
        if (!initialized) {
            synchronized (MiniPlaceholdersReflectionImpl.class) {
                try {
                    final ClassLoader classLoader = this.getClass().getClassLoader();

                    // Figure out what version of mini placeholders is running based on present classes
                    try {
                        final Class<?> miniPlaceholdersClass = classLoader.loadClass("io.github.miniplaceholders.api.MiniPlaceholders");
                        miniPlaceholdersClass.getMethod("getGlobalPlaceholders");
                        version = Version.V2V1;
                    } catch (ClassNotFoundException | NoSuchMethodException | RuntimeException e) {
                        try {
                            final Class<?> miniPlaceholdersClass = classLoader.loadClass("io.github.miniplaceholders.api.MiniPlaceholders");
                            miniPlaceholdersClass.getMethod("globalPlaceholders");
                            version = Version.V3;
                        } catch (ClassNotFoundException | NoSuchMethodException | RuntimeException e2) {
                            version = Version.UNKNOWN;
                        }
                    }

                    // Lazy initialize fields depending on running version
                    if (!version.equals(Version.UNKNOWN) && classLoader != null) {
                        final Class<?> miniPlaceholdersClass = classLoader.loadClass("io.github.miniplaceholders.api.MiniPlaceholders");
                        global = isV3() ? miniPlaceholdersClass.getMethod("globalPlaceholders") : miniPlaceholdersClass.getMethod("getGlobalPlaceholders");
                        audience = isV3() ? miniPlaceholdersClass.getMethod("audiencePlaceholders") : miniPlaceholdersClass.getMethod("getAudiencePlaceholders", Audience.class);
                        placeholders = isV3() ? miniPlaceholdersClass.getMethod("relationalPlaceholders") : miniPlaceholdersClass.getMethod("getRelationalPlaceholders", Audience.class, Audience.class);
                    }
                } catch (Exception ignored) {
                } finally {
                    initialized = true;
                }
            }
        }
    }

    /**
     * Whether the running version of MiniPlaceholders is version 3.X.X.
     * @return boolean
     */
    protected boolean isV3() {
        return version.equals(Version.V3);
    }

    /**
     * Whether the running version of MiniPlaceholders is version 2.X.X.
     * @return boolean
     */
    protected boolean isV2() {
        return version.equals(Version.V2V1);
    }

    /**
     * Whether the running version of MiniPlaceholders is version unknown.
     * @return boolean
     */
    protected boolean isUnknown() {
        return version.equals(Version.UNKNOWN);
    }

    /**
     * Get the global tag resolver method as an optional.
     * @return optional method
     */
    private Optional<Method> getGlobal() {
        return Optional.ofNullable(global);
    }

    /**
     * Get the audience tag resolver method as an optional.
     * @return optional method
     */
    private Optional<Method> getAudience() {
        return Optional.ofNullable(audience);
    }

    /**
     * Get the relational tag resolver method as an optional.
     * @return optional method
     */
    private Optional<Method> getRelational() {
        return Optional.ofNullable(placeholders);
    }

    /**
     * Get a global tag resolver from MiniPlaceholders.
     * @return the tag resolver or an empty tag resolver as fallback
     */
    protected TagResolver resolveGlobal() {
        return getGlobal()
            .map(method -> {
                try {
                    return (TagResolver) method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return TagResolver.empty();
                }
            })
            .orElse(TagResolver.empty());
    }

    /**
     * Get an audience tag resolver from MiniPlaceholders.
     * @param audience1 the audience to resolve tags for (or null if using MiniPlaceholders v3.X.X)
     * @return the tag resolver or an empty tag resolver as fallback
     */
    protected TagResolver resolveAudience(final @Nullable Audience audience1) {
        if (audience1 == null)
            return resolveGlobal();

        return getAudience()
            .map(method -> {
                try {
                    return TagResolver.resolver(
                        resolveGlobal(),
                        isV3() ?
                            (TagResolver) method.invoke(null) :
                            (TagResolver) method.invoke(null, audience1)
                    );
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return TagResolver.empty();
                }
            })
            .orElse(TagResolver.empty());
    }

    /**
     * Get a relational tag resolver from MiniPlaceholders.
     * @param audience1 the audience to resolve tags for (or null if using MiniPlaceholders v3.X.X)
     * @param audience2 the audience to resolve tags for (or null if using MiniPlaceholders v3.X.X)
     * @return the tag resolver or an empty tag resolver as fallback
     */
    protected TagResolver resolveRelational(final @Nullable Audience audience1, final @Nullable Audience audience2) {
        if (audience1 == null || audience2 == null)
            return resolveAudience(audience1);

        return getRelational()
            .map(method -> {
                try {
                    return TagResolver.resolver(
                        resolveAudience(audience1),
                        isV3() ?
                            (TagResolver) method.invoke(null) :
                            (TagResolver) method.invoke(null, audience1, audience2)
                    );
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return TagResolver.empty();
                }
            })
            .orElse(TagResolver.empty());
    }
}
