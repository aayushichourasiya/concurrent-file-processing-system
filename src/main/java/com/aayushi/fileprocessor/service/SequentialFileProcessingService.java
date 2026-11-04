package com.aayushi.fileprocessor.service;

import com.aayushi.fileprocessor.model.FileProcessingResult;
import com.aayushi.fileprocessor.processor.TextFileProcessor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class SequentialFileProcessingService {

    private final TextFileProcessor processor;

    public SequentialFileProcessingService() {
        this(new TextFileProcessor());
    }

    public SequentialFileProcessingService(TextFileProcessor processor) {
        this.processor = processor;
    }

    public List<FileProcessingResult> processDirectory(Path inputDirectory)
            throws IOException {

        if (!Files.exists(inputDirectory)) {
            throw new IOException("Input directory does not exist: " + inputDirectory);
        }
        if (!Files.isDirectory(inputDirectory)) {
            throw new IOException("Input path is not a directory: " + inputDirectory);
        }

        List<Path> inputFiles = new ArrayList<>();

        try (Stream<Path> paths = Files.list(inputDirectory)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString()
                            .toLowerCase(Locale.ROOT).endsWith(".txt"))
                    .sorted(Comparator.comparing(
                            path -> path.getFileName().toString()))
                    .forEach(inputFiles::add);
        }

        List<FileProcessingResult> results = new ArrayList<>();

        // Milestone 1 is intentionally sequential: each file finishes before the next starts.
        for (Path file : inputFiles) {
            results.add(processor.process(file));
        }

        return results;
    }

    public void writeReport(List<FileProcessingResult> results, Path reportPath)
            throws IOException {

        Path parent = reportPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        long totalLines = 0;
        long totalWords = 0;
        long totalCharacters = 0;

        try (BufferedWriter writer =
                     Files.newBufferedWriter(reportPath, StandardCharsets.UTF_8)) {
            writer.write("CONCURRENT FILE PROCESSING SYSTEM");
            writer.newLine();
            writer.write("Milestone 1 - Sequential processing");
            writer.newLine();
            writer.newLine();

            for (FileProcessingResult result : results) {
                writer.write("File: " + result.file().getFileName());
                writer.newLine();
                writer.write("  Lines: " + result.lineCount());
                writer.newLine();
                writer.write("  Words: " + result.wordCount());
                writer.newLine();
                writer.write("  Characters: " + result.characterCount());
                writer.newLine();
                writer.newLine();

                totalLines += result.lineCount();
                totalWords += result.wordCount();
                totalCharacters += result.characterCount();
            }

            writer.write("TOTALS");
            writer.newLine();
            writer.write("Files: " + results.size());
            writer.newLine();
            writer.write("Lines: " + totalLines);
            writer.newLine();
            writer.write("Words: " + totalWords);
            writer.newLine();
            writer.write("Characters: " + totalCharacters);
            writer.newLine();
        }
    }
}
