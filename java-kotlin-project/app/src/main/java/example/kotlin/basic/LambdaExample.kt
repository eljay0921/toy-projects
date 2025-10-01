package example.kotlin.basic

class LambdaExample {

    // 함수형 인터페이스 불필요 : 함수 타입 직접 사용 (함수 자체가 일급 객체다)
    fun main() {

        // 기본(람다) 표현식
        val add: (Int, Int) -> Int = { a, b -> a + b }
        val multiply = { a: Int, b: Int -> a * b }         // 타입 추론

        println(add(2, 4))  // 6
        println(multiply(2, 4)) // 8

        // N줄 람다 (마지막 표현식이 반환값)
        val complexCal = {
            a: Int, b: Int ->
            var result = a + b
            result *= 2
        }

        println(complexCal(2, 4)) // 12

        // 매개변수 없는 람다
        val task: () -> Unit = { println("Hello") }
        task()

        // 매개변수가 하나인 람다 (it 사용)
        val printer: (String) -> Unit = { println(it) }
        printer("hi!")

        // 명시적으로 이름 지정도 가능
        val printerWithName: (String) -> Unit = { msg -> println(msg) }
        printerWithName("hi~~")
    }
}