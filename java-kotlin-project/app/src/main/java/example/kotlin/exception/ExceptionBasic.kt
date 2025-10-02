package example.kotlin.exception

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class ExceptionBasic {

    // 기본 : kotlin에서 try-catch는 표현식이다.
    fun divide(a: Int, b: Int) {
        try {
            val result = a / b
            println("Result: $result")
        } catch (e: ArithmeticException) {
            println("Error: Cannot divide by zero.")
            e.printStackTrace()
        } finally {
            println("Division operation completed.")
        }
    }

    // try를 표현식으로 사용
    fun safeDivide(a: Int, b: Int): Int? {
        return try {
            a / b
        } catch (e: ArithmeticException) {
            null
        }
    }

    // 여러 예외 처리
    fun readFile(path: String) {
        try {
            val content = File(path).readText()
            println(content)
        } catch (e: FileNotFoundException) {
            println("File not found: $path")
        } catch (e: IOException) {
            println("Error reading file.")
        } finally {
            println("Completed.")
        }
    }

    // 표현식으로 기본값 제공 가능
    fun getFileContent(path: String): String {
        return try {
            File(path).readText()
        } catch (e: Exception) {
            "Default content..."
        }
    }
}