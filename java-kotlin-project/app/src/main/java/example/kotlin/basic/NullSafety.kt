package example.kotlin.basic

import example.kotlin.dataclass.Person
import example.kotlin.dataclass.User

class NullSafety {

    // Nullable 타입과 safe call
    fun getUpperCaseName(person: Person?): String {
        return person?.name?.uppercase() ?: "UNKNOWN"
    }

    // Elvis 연산자(?:)로 기본값 제공
    fun getUserDisplayName(user: User?): String {
        return user?.username ?: "Anonymous"
    }

    // Safe call 체이닝
    fun getUserEmailDomain(user: User?): String {
        // return user?.email?.substringAfter("@") ?: "unknown" // email이 빈 문자열일 때 null이 아니라서 의도한 대로 동작하지 않았음
        return user?.email?.takeIf { it.contains("@") }?.substringAfter("@") ?: "unknown"
    }

    // let을 이용한 null 처리
    fun processUser(user: User?) {
        user?.let { u ->
            println("Processing user: ${u.username}")
            println("Email: ${u.email}")
        }
    }

    // 강제 언랩핑(!!) - 주의
    fun riskyFunction(text: String?) {
        val length = text!!.length // null이면 NullPointerException
    }

    // 스마트 캐스팅
    fun smartCastExample(text: String?) {
        if (text != null) { // 이 블록 안에서는 text가 자동으로 String으로 캐스팅
            println(text.length)    // ?. 없이 사용 가능
        }
    }
}