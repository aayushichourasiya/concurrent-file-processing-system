package com.aayushi.fileprocessor.model;

import java.nio.file.Path;

public record FileProcessingResult(
        Path file,
        long lineCount,
        long wordCount,
        long characterCount
) {
}
