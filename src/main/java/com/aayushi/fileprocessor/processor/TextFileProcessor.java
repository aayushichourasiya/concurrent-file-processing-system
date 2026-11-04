package com.aayushi.fileprocessor.processor;

import com.aayushi.fileprocessor.model.FileProcessingResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileProcessor {

    public FileProcessingResult process(Path file) throws IOException {
        long lineCount = 0;
        long wordCount = 0;
        long characterCount = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                characterCount += line.codePointCount(0, line.length());

                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    wordCount += trimmedLine.split("\\s+").length;
                }
            }
        }

        return new FileProcessingResult(
                file,
                lineCount,
                wordCount,
                characterCount
        );
    }
}
