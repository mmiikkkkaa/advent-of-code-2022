package net.mikka.adventofcode2022

class ResourceReader {
    fun readLinesFromResourceFile(file: String): List<String> {
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return this::class.java
            .getResource(file)
            .readText()
            .lines()
            .dropLastWhile { it.isBlank() }
    }
}