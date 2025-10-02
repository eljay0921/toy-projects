package example.java.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionBasicTest {

    private ExceptionBasic exceptionBasic;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        exceptionBasic = new ExceptionBasic();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void divide_정상적인_나눗셈() {
        exceptionBasic.divide(10, 2);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Result: 5"));
        assertTrue(output.contains("Division operation completed."));
    }

    @Test
    void divide_0으로_나누기_예외처리() {
        exceptionBasic.divide(10, 0);

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Error: Cannot divide by zero."));
        assertTrue(output.contains("Division operation completed."));
    }

    @Test
    void readFile_존재하지_않는_파일() {
        exceptionBasic.readFile("nonexistent.txt");

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("File not found: nonexistent.txt"));
        assertTrue(output.contains("Cleanup"));
    }

    @Test
    void readFile_존재하는_파일() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.writeString(tempFile, "Test content");

        try {
            exceptionBasic.readFile(tempFile.toString());

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Test content"));
            assertTrue(output.contains("Cleanup"));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void multiCatch_존재하지_않는_파일() {
        exceptionBasic.MultiCatch("nonexistent.txt");

        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("IO or Arithmetic error"));
    }

    @Test
    void multiCatch_존재하는_파일() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.writeString(tempFile, "Multi-catch test");

        try {
            exceptionBasic.MultiCatch(tempFile.toString());

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Multi-catch test"));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
