package io.github.milkdrinkers.colorparser.common.processor;

/**
 * An interface representing a processor that can processes an input string.
 * <p>
 * Implementations of this interface should provide the logic to process the input string and return the processed result.
 * The priority of the processor can also be defined, with lower values indicating higher priority.
 *
 * @since 4.0.0
 */
public interface Processor {
    /**
     * Processes the input string and returns the processed result.
     *
     * @param input The input string to process
     * @return The processed string
     * @since 4.0.0
     */
    String process(String input);

    /**
     * Returns the name of the processor.
     *
     * @return The name of the processor
     * @since 4.0.0
     */
    @SuppressWarnings("SameReturnValue")
    String getName();

    /**
     * Returns the priority of the processor.
     * Lower values indicate higher priority.
     *
     * @return The priority of the processor
     * @since 4.0.0
     */
    @SuppressWarnings("SameReturnValue")
    default int getPriority() {
        return 100; // Default priority is 0, can be overridden by implementations
    }
}
