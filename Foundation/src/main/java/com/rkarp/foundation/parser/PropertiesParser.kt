package com.rkarp.foundation.parser

import java.util.*

class PropertiesParser {
    companion object {
        fun filterProperties(properties: Properties, identifier: String) : Properties {
            val result = Properties()
            for (entry in properties.entries) {
                if ((entry.key as String).contains(identifier + ".")) {
                    result.put(entry.key, entry.value)
                }
            }
            return result
        }

        fun filterPropertiesAndReplaceIdentifier(properties: Properties, identifier: String) : Properties {
            val result = Properties()
            for ((key, value) in properties) {
                if ((key as String).contains(identifier + ".")) {
                    result.put(
                            key.replace((identifier + ".").toRegex(), ""),
                            (value as String).replace((identifier + ".").toRegex(), "")
                    )
                }
            }
            return result
        }

        fun getPropertyMapWithIdentifier(properties: Properties, identifier: String) : HashMap<String, HashMap<Any, Any>> {
            val strings = HashMap<Any, Any>(1)
            for ((key, value) in properties) {
                strings.put(key, value)
            }
            val list = HashMap<String, HashMap<Any, Any>>(1)
            list.put(identifier, strings)
            return list
        }
    }
}