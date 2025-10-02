package example.java.exception;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionBasic {

    public void divide(int a, int b) {
        try {
            int result = a / b;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: Cannot divide by zero.");
            e.printStackTrace();
        } finally {
            System.out.println("Division operation completed.");
        }
    }

    // 여러 예외 처리 (with Checked Exception)
    public void readFile(String path) {
        try {
            // 파일 읽기 로직
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            System.out.println(line);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            System.out.println("Error reading file");
        } finally {
            // 리소스 정리
            System.out.println("Cleanup");
        }
    }

    // Java 7+ Multi-catch
    public void MultiCatch(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException | ArithmeticException e) {
            System.out.println("IO or Arithmetic error: " + e.getMessage());
        }
    }
}
