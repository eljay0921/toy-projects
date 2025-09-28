package example.kotlin.basic

// 상수 (컴파일 타임 상수)
const val CONSTANT = "HELLO_WORLD"

class BasicSnippets {

    // basic 01 ============================================================

    // 불변 (value, 읽기 전용)
    val number = 10
    val name = "jin"
    val isValid = true
    val price = 99.99

    // 가변 (variable)
    var userName = "jake"
    var count = 22

    // 명시적 타입 선언
    val explicitString: String = "MyName"
    val explicitInt: Int = 300

    // Null 안정성
    val nonNullString: String = "Hello"
    val nullableString: String? = null
    // var errorString: String = null   // 컴파일 에러!

    // basic 02 ============================================================

    // 기본 함수
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    // 기본 함수 -> 단일 표현식 함수
    fun add2(a: Int, b: Int) = a + b    // 반환 타입 추론

    // 기본 매개변수
    fun greet(name: String, title: String = "Mr."): String {
        return "Hello, $title $name"
    }
    // Named Arguments로 호출
    // greet("John")              // "Hello, Mr. John"
    // greet("John", "Dr.")       // "Hello, Dr. John"
    // greet(title = "Ms.", name = "Jane")  // 순서 바꿔도 OK

    // 가변 인자 (vararg)
    fun sum(vararg numbers: Int): Int {
        return numbers.sum()
    }
    // sum(1, 2, 3, ...)

    // 고차 함수 (함수를 매개변수로 받음)
    fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
        return operation(a, b)
    }
    // calculate(3, 5) { a, b -> a + b}

}

// 최상위 함수 (클래스 밖에 선언 가능)
fun multiply(a: Int, b: Int) = a * b;