package com.aayushi.fileprocessor.processor;

import com.aayushi.fileprocessor.model.FileProcessingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextFileProcessorTest {

    @TempDir
    Path temporaryDirectory;

    private final TextFileProcessor processor = new TextFileProcessor();

    @Test
    void countsLinesWordsAndUnicodeCodePoints() throws Exception {
        Path file = temporaryDirectory.resolve("sample.txt");
        Files.writeString(file, "Hello world\nJava 17 🚀\n", StandardCharsets.UTF_8);

        FileProcessingResult result = processor.process(file);

        assertEquals(2, result.lineCount());
        assertEquals(5, result.wordCount());
        assertEquals(20, result.characterCount());
    }

    @Test
    void handlesEmptyFile() throws Exception {
        Path file = temporaryDirectory.resolve("empty.txt");
        Files.createFile(file);

        FileProcessingResult result = processor.process(file);

        assertEquals(0, result.lineCount());
        assertEquals(0, result.wordCount());
        assertEquals(0, result.characterCount());
    }

    @Test
    void countsFinalLineWithoutTrailingNewline() throws Exception {
        Path file = temporaryDirectory.resolve("final-line.txt");
        Files.writeString(file, "one two", StandardCharsets.UTF_8);

        FileProcessingResult result = processor.process(file);

        assertEquals(1, result.lineCount());
        assertEquals(2, result.wordCount());
        assertEquals(7, result.characterCount());
    }
}
