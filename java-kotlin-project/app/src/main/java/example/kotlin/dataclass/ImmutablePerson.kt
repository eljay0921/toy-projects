package example.kotlin.dataclass

data class ImmutablePerson (
    val name: String,
    val age: Int,
    val hobbies: List<String>
) {
    // copy() 메서드 자동 생성됨
    fun withAge(newAge: Int) = copy(age = newAge)
}