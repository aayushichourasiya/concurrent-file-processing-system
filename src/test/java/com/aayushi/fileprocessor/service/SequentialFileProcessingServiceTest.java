package com.aayushi.fileprocessor.service;

import com.aayushi.fileprocessor.model.FileProcessingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SequentialFileProcessingServiceTest {

    @TempDir
    Path temporaryDirectory;

    private final SequentialFileProcessingService service =
            new SequentialFileProcessingService();

    @Test
    void processesOnlyTextFilesInSortedOrder() throws Exception {
        Path input = Files.createDirectory(temporaryDirectory.resolve("input"));
        Files.writeString(input.resolve("b.txt"), "second file");
        Files.writeString(input.resolve("a.txt"), "first file");
        Files.writeString(input.resolve("ignore.csv"), "not processed");
        Files.createDirectory(input.resolve("nested.txt"));

        List<FileProcessingResult> results = service.processDirectory(input);

        assertEquals(2, results.size());
        assertEquals("a.txt", results.get(0).file().getFileName().toString());
        assertEquals("b.txt", results.get(1).file().getFileName().toString());
    }

    @Test
    void rejectsMissingInputDirectory() {
        Path missing = temporaryDirectory.resolve("does-not-exist");

        assertThrows(java.io.IOException.class,
                () -> service.processDirectory(missing));
    }

    @Test
    void writesReportAndTotals() throws Exception {
        Path input = Files.createDirectory(temporaryDirectory.resolve("input"));
        Files.writeString(input.resolve("one.txt"), "alpha beta");
        List<FileProcessingResult> results = service.processDirectory(input);

        Path report = temporaryDirectory.resolve("output/report.txt");
        service.writeReport(results, report);

        String content = Files.readString(report);
        assertTrue(content.contains("File: one.txt"));
        assertTrue(content.contains("Words: 2"));
        assertTrue(content.contains("Files: 1"));
    }
}
