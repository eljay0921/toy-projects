package example.kotlin.exception

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.io.path.createTempFile
import kotlin.io.path.deleteIfExists
import kotlin.io.path.writeText

class ExceptionBasicTest {

    private lateinit var exceptionBasic: ExceptionBasic
    private val outputStreamCaptor = ByteArrayOutputStream()
    private val standardOut = System.out

    @BeforeEach
    fun setUp() {
        exceptionBasic = ExceptionBasic()
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `divide 정상적인 나눗셈`() {
        exceptionBasic.divide(10, 2)

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Result: 5"))
        assertTrue(output.contains("Division operation completed."))
    }

    @Test
    fun `divide 0으로 나누기 예외처리`() {
        exceptionBasic.divide(10, 0)

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Error: Cannot divide by zero."))
        assertTrue(output.contains("Division operation completed."))
    }

    @Test
    fun `safeDivide 정상적인 나눗셈`() {
        val result = exceptionBasic.safeDivide(10, 2)
        assertEquals(5, result)
    }

    @Test
    fun `safeDivide 0으로 나누기는 null 반환`() {
        val result = exceptionBasic.safeDivide(10, 0)
        assertNull(result)
    }

    @Test
    fun `readFile 존재하지 않는 파일`() {
        exceptionBasic.readFile("nonexistent.txt")

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("File not found: nonexistent.txt"))
        assertTrue(output.contains("Completed."))
    }

    @Test
    fun `readFile 존재하는 파일`() {
        val tempFile = createTempFile("test", ".txt")
        tempFile.writeText("Test content")

        try {
            exceptionBasic.readFile(tempFile.toString())

            val output = outputStreamCaptor.toString()
            assertTrue(output.contains("Test content"))
            assertTrue(output.contains("Completed."))
        } finally {
            tempFile.deleteIfExists()
        }
    }

    @Test
    fun `getFileContent 존재하지 않는 파일은 기본값 반환`() {
        val content = exceptionBasic.getFileContent("nonexistent.txt")
        assertEquals("Default content...", content)
    }

    @Test
    fun `getFileContent 존재하는 파일의 내용 반환`() {
        val tempFile = createTempFile("test", ".txt")
        tempFile.writeText("File content")

        try {
            val content = exceptionBasic.getFileContent(tempFile.toString())
            assertEquals("File content", content)
        } finally {
            tempFile.deleteIfExists()
        }
    }
}
