package io.github.milkdrinkers.colorparser.common.processor;

public interface Processor {
    /**
     * Processes the input string and returns the processed result.
     *
     * @param input The input string to process
     * @return The processed string
     */
    String process(String input);

    /**
     * Returns the name of the processor.
     *
     * @return The name of the processor
     */
    @SuppressWarnings("SameReturnValue")
    String getName();

    /**
     * Returns the priority of the processor.
     * Lower values indicate higher priority.
     *
     * @return The priority of the processor
     */
    @SuppressWarnings("SameReturnValue")
    default int getPriority() {
        return 100; // Default priority is 0, can be overridden by implementations
    }
}
