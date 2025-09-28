package example.kotlin.dataclass

class Rectangle(width: Double, height: Double) {
    var width: Double = width
        set(value) {
            require(value > 0) { "Wrong width" }
            field = value
        }

    var height: Double = height
        set(value) {
            require(value > 0) { "Wrong height" }
            field = value
        }

    val area: Double
        get() = width * height
}