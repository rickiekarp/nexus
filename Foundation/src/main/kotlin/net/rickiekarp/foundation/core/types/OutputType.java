package net.rickiekarp.foundation.core.types;

/**
 * Defines the output type for a given property value.
 * @param <T> Type for the output.
 */
public interface OutputType<T> {

    OutputType<String> STRING = new OutputType<String>() {
        public String convert(String value) {
            return value;
        }

        public String toString() {
            return "OutputType.STRING";
        }
    };

    OutputType<Integer> INT = new OutputType<Integer>() {
        public Integer convert(String value) {
            return Integer.parseInt(value);
        }

        public String toString() {
            return "OutputType.INTEGER";
        }
    };

    OutputType<Boolean> BOOLEAN = new OutputType<Boolean>() {
        public Boolean convert(String value) {
            return Boolean.parseBoolean(value);
        }

        public String toString() {
            return "OutputType.STRING";
        }
    };

    /**
     * Convert the given value to a requested format.
     *
     * @param value property value
     * @return property value into requested format.
     */
    T convert(String value);
}