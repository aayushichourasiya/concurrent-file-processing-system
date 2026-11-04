# Concurrent File Processing System

A standalone Java 17 + Maven project. **Milestone 1 implements the sequential baseline** that later milestones will extend with ExecutorService, Callable/Future, thread pools, and CompletableFuture.

## What it does

- Finds regular `.txt` files directly inside `input/` (not recursively).
- Processes files in filename order, one at a time.
- Counts lines, whitespace-separated words, and Unicode code points.
- Writes per-file results and totals to `output/report.txt`.

Counting rules:
- A line is each value returned by `BufferedReader.readLine()`. A final line without a trailing newline counts.
- A word is a non-empty token separated by whitespace.
- Characters are Unicode code points in line content. Line terminators are excluded.

## Requirements

- JDK 17
- Maven 3.8+

Verify:
```bash
java -version
mvn -version
```

## Run from the project root

```bash
mvn clean test
mvn exec:java
```

The `exec-maven-plugin` is configured to run `com.aayushi.fileprocessor.Main`.
The sample input files are included. Add more `.txt` files to `input/` and rerun.

The generated report is `output/report.txt`.

## Run in IntelliJ IDEA

1. Open this folder as a Maven project.
2. Set the Project SDK to JDK 17.
3. Wait for Maven dependencies to import.
4. Run `src/main/java/com/aayushi/fileprocessor/Main.java`.
5. Ensure the run configuration working directory is the project root.

## Project structure

```text
src/
  main/java/com/aayushi/fileprocessor/
    Main.java
    model/FileProcessingResult.java
    processor/TextFileProcessor.java
    service/SequentialFileProcessingService.java
  test/java/com/aayushi/fileprocessor/
    processor/TextFileProcessorTest.java
    service/SequentialFileProcessingServiceTest.java
input/
output/
pom.xml
README.md
```

## Milestones

1. Sequential baseline (this version)
2. ExecutorService
3. Callable and Future
4. Configurable ThreadPoolExecutor
5. Per-file failure isolation
6. CompletableFuture
7. Integration tests and sequential/concurrent performance comparison
