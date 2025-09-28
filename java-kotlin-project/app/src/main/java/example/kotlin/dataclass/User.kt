package example.kotlin.dataclass

data class User(
    val username: String,
    val email: String = "",
    val age: Int = 0,
    val isActive: Boolean = true
) {
    // Secondary constructor (필요한 경우)
    constructor(username: String) : this(username, "")
}

// 또는 더 간단하게 기본값만 활용
//data class User(
//    val username: String,
//    val email: String = "",
//    val age: Int = 0,
//    val isActive: Boolean = true
//)