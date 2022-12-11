package net.mikka.adventofcode2022

import java.util.*
import java.util.function.Predicate

@Suppress("unused")
class Dec07 : PuzzleDay<Int, Int>() {

    override fun getPuzzle1(): Puzzle<Int> {
        return Puzzle01()
    }

    override fun getPuzzle2(): Puzzle<Int> {
        return Puzzle02()
    }


    abstract class FileSystemItem(
        val name: String,
        val parent: Directory?
    ) {
        abstract fun size(): Int
        abstract fun entries(): List<FileSystemItem>
    }

    class File(name: String, private val size: Int, parent: Directory?) : FileSystemItem(name, parent) {
        override fun size(): Int {
            return size
        }

        override fun entries(): List<FileSystemItem> {
            return emptyList()
        }
    }

    open class Directory(name: String, parent: Directory?) : FileSystemItem(name, parent) {
        private val entries: MutableList<FileSystemItem> = mutableListOf()

        override fun size(): Int {
            return entries.sumOf { it.size() }
        }

        override fun entries(): MutableList<FileSystemItem> {
            return entries
        }
    }

    class Root : Directory("/", null)

    companion object {
        fun String.toFileSystemItem(currentDirectory: Directory): FileSystemItem {
            if (this.startsWith("dir"))
                return Directory(this.substringAfter("dir "), currentDirectory)

            return File(this.substringAfter(" "), this.substringBefore(" ").toInt(), currentDirectory)
        }

        private fun buildFileSystemTree(input: List<String>): Root {
            val root = Root()
            var currentDirectory: Directory = Root()

            for (line in input) {

                if (line.startsWith("$ cd")) {
                    val command = line.substringAfter("$ cd ")
                    currentDirectory = when (command) {
                        "/" -> root
                        ".." -> currentDirectory.parent!!
                        else -> currentDirectory.entries().single { it.name == command } as Directory
                    }

                } else if (line == "$ ls") {
                    // done by next line
                } else {
                    currentDirectory.entries().add(line.toFileSystemItem(currentDirectory))
                }
            }
            return root
        }

        fun findDirsBySizeCondition(directory: Directory, sizeCondition: Predicate<Int>): List<Directory> {
            val dirs = mutableListOf<Directory>()
            if (sizeCondition.test(directory.size()))
                dirs.add(directory)
            directory.entries().filter { it is Directory }
                .forEach { dirs.addAll(findDirsBySizeCondition(it as Directory, sizeCondition)) }

            return dirs
        }
    }

    class Puzzle01 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            val root = buildFileSystemTree(input)
            return findDirsBySizeCondition(root) { size -> size <= 100_000 }.sumOf { it.size() }
        }


    }

    class Puzzle02 : Puzzle<Int> {
        override fun solve(input: List<String>): Int {

            val root = buildFileSystemTree(input)

            val fileSystemSpace = 70_000_000
            val neededSpaceForUpdate = 30_000_000
            val spaceToFree = neededSpaceForUpdate - (fileSystemSpace - root.size())

            return findDirsBySizeCondition(root) { size -> size >= spaceToFree }.minByOrNull { it.size() }!!.size()
        }
    }
}
