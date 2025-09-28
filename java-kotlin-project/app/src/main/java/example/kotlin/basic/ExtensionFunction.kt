package example.kotlin.basic

class ExtensionFunction {

    // String 클래스에 확장 함수 생성
    fun String.isPalindrome(): Boolean {
        return this == this.reversed()
    }

    // Int 클래스에 확장 함수 생성
    fun Int.isEven(): Boolean = this % 2 == 0

    // List에 확장 함수 생성
    fun <T> List<T>.secondOrNull(): T? {
        return if (size >= 2) this[1] else null
    }

    fun test() {
        val word = "level"
        println(word.isPalindrome()) // true

        println(100.isEven()) // true

        val list = listOf("가", "나", "다")
        println(list.secondOrNull()) // "나"
    }
}

