package io.github.milkdrinkers.colorparser.common.placeholder;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manages the registration and retrieval of {@link PlaceholderProvider} instances.
 * @since 4.0.0
 */
public class PlaceholderProviderManager<Context extends SimplePlaceholderContext<?, ?>> {
    private final Map<String, PlaceholderProvider<Context>> stringPlaceholderProviders = new HashMap<>();
    private final Map<String, PlaceholderProvider<Context>> miniMessagePlaceholderProviders = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Adds a single {@link PlaceholderProvider} to the manager.
     *
     * @param provider the provider to add
     * @since 4.0.0
     */
    public void add(@NotNull PlaceholderProvider<Context> provider) {
        try {
            lock.writeLock().lock();
            switch (provider.getType()) {
                case STRING:
                    stringPlaceholderProviders.put(provider.getName(), provider);
                    break;
                case MINIMESSAGE:
                    miniMessagePlaceholderProviders.put(provider.getName(), provider);
                    break;
                default:
                    break;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Adds multiple {@link PlaceholderProvider} instances to the manager.
     *
     * @param providers the collection of providers to add
     * @since 4.0.0
     */
    public void addAll(@NotNull Collection<PlaceholderProvider<Context>> providers) {
        try {
            lock.writeLock().lock();
            for (final PlaceholderProvider<Context> provider : providers) {
                switch (provider.getType()) {
                    case STRING:
                        stringPlaceholderProviders.put(provider.getName(), provider);
                        break;
                    case MINIMESSAGE:
                        miniMessagePlaceholderProviders.put(provider.getName(), provider);
                        break;
                    default:
                        break;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Clears all placeholder providers from the manager.
     * This method removes all providers from both string and mini-message lists.
     * @since 4.0.0
     */
    public void clear() {
        try {
            lock.writeLock().lock();
            stringPlaceholderProviders.clear();
            miniMessagePlaceholderProviders.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Retrieves all placeholder providers, both string and mini-message types.
     *
     * @return an unmodifiable list of all placeholder providers
     * @since 4.0.0
     */
    public @NotNull List<PlaceholderProvider<Context>> getAll() {
        try {
            lock.readLock().lock();
            return Collections.unmodifiableList(Stream.concat(
                stringPlaceholderProviders.values().stream(),
                miniMessagePlaceholderProviders.values().stream()
            ).collect(Collectors.toList()));
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves a string resolver for the specified provider name.
     *
     * @param providerName the name of the placeholder provider
     * @return an Optional containing the BiFunction if available, or empty if not
     * @since 4.0.0
     */
    public @NotNull Optional<BiFunction<Context, String, String>> getStringResolver(final @NotNull String providerName) {
        try {
            lock.readLock().lock();
            final PlaceholderProvider<Context> provider = stringPlaceholderProviders.get(providerName);
            if (provider != null && provider.isAvailable()) {
                return Optional.of(provider.getStringResolver());
            }
            return Optional.empty();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves a MiniMessage tag resolver for the specified provider name and context.
     *
     * @param providerName the name of the MiniMessage placeholder provider
     * @param context      the context in which to resolve the tags
     * @return an Optional containing the TagResolver if available, or empty if not
     * @since 4.0.0
     */
    public @NotNull Optional<TagResolver> getMiniMessageTagResolver(final @NotNull String providerName, final @NotNull Context context) {
        try {
            lock.readLock().lock();
            final PlaceholderProvider<Context> provider = miniMessagePlaceholderProviders.get(providerName);
            if (provider != null && provider.isAvailable()) {
                return Optional.of(provider.getTagResolver(context));
            }
            return Optional.empty();
        } finally {
            lock.readLock().unlock();
        }
    }
}
