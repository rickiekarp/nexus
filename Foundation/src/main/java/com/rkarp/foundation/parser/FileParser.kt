package com.rkarp.foundation.parser

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class FileParser {
    companion object {
        fun readFileAndReturnString(path: String) : String {
            return String(readFile(path), StandardCharsets.UTF_8)
        }

        fun readFile(path: String) : ByteArray {
            return Files.readAllBytes(Paths.get(path))
        }
    }
}