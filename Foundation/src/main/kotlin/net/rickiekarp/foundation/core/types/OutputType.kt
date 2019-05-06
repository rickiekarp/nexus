package net.rickiekarp.foundation.core.types

/**
 * Defines the output type for a given property value.
 * @param <T> Type for the output.
</T> */
interface OutputType<T> {

    /**
     * Convert the given value to a requested format.
     *
     * @param value property value
     * @return property value into requested format.
     */
    fun convert(value: String): T

    companion object {

        val STRING: OutputType<String> = object : OutputType<String> {
            override fun convert(value: String): String {
                return value
            }

            override fun toString(): String {
                return "OutputType.STRING"
            }
        }

        val INT: OutputType<Int> = object : OutputType<Int> {
            override fun convert(value: String): Int {
                return Integer.parseInt(value)
            }

            override fun toString(): String {
                return "OutputType.INTEGER"
            }
        }

        val BOOLEAN: OutputType<Boolean> = object : OutputType<Boolean> {
            override fun convert(value: String): Boolean {
                return java.lang.Boolean.parseBoolean(value)
            }

            override fun toString(): String {
                return "OutputType.STRING"
            }
        }
    }
}