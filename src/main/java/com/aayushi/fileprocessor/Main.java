package com.aayushi.fileprocessor;

import com.aayushi.fileprocessor.model.FileProcessingResult;
import com.aayushi.fileprocessor.service.SequentialFileProcessingService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Path inputDirectory = Path.of("input");
        Path outputReport = Path.of("output", "report.txt");

        SequentialFileProcessingService service =
                new SequentialFileProcessingService();

        try {
            List<FileProcessingResult> results =
                    service.processDirectory(inputDirectory);

            service.writeReport(results, outputReport);

            System.out.println("Processing completed.");
            System.out.println("Files processed: " + results.size());
            System.out.println("Report written to: " + outputReport.toAbsolutePath());
        } catch (IOException exception) {
            System.err.println("File processing failed: " + exception.getMessage());
            System.exit(1);
        }
    }
}
