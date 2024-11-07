abstract class Shape {
    abstract var color: String
    abstract fun draw()
    abstract fun move(deltaX: Int, deltaY: Int)
}

open class Point(var x: Int, var y: Int) : Shape() {
    override var color: String = "black"

    constructor(x: Int, y: Int, color: String) : this(x, y) {
        this.color = color
    }

    override fun draw() {
        println("Рисуем точку в ($x, $y) с цветом $color")
    }

    override fun move(deltaX: Int, deltaY: Int) {
        x += deltaX
        y += deltaY
    }
}

class ColoredPoint(x: Int, y: Int, override var color: String) : Point(x, y) {
    override fun draw() {
        println("Рисуем цветную точку в ($x, $y) с цветом $color")
    }
}

open class Line(protected val start: Point, protected val end: Point) : Shape() {
    override var color: String
        get() = start.color
        set(value) {
            start.color = value
            end.color = value
        }

    override fun draw() {
        println("Рисуем линию от (${start.x}, ${start.y}) до (${end.x}, ${end.y}) с цветом $color")
    }

    override fun move(deltaX: Int, deltaY: Int) {
        start.move(deltaX, deltaY)
        end.move(deltaX, deltaY)
    }
}

class ColoredLine(start: Point, end: Point, override var color: String) : Line(start, end) {
    override fun draw() {
        println("Рисуем цветную линию от (${start.x}, ${start.y}) до (${end.x}, ${end.y}) с цветом $color")
    }
}

class Polygon(private val points: MutableList<Point>) : Shape() {
    override var color: String
        get() = if (points.isNotEmpty()) points[0].color else "нет цвета"
        set(value) {
            for (point in points) {
                point.color = value
            }
        }

    override fun draw() {
        println("Рисуем многоугольник с точками:")
        for (point in points) {
            println("(${point.x}, ${point.y}) с цветом ${point.color}")
        }
    }

    override fun move(deltaX: Int, deltaY: Int) {
        for (point in points) {
            point.move(deltaX, deltaY)
        }
    }

    fun moveAlongX(deltaX: Int) {
        for (point in points) {
            point.move(deltaX, 0)
        }
    }

    fun moveAlongY(deltaY: Int) {
        for (point in points) {
            point.move(0, deltaY)
        }
    }
}

fun main() {
    val shapes: Array<Shape> = arrayOf(
        Point(1, 2),
        ColoredPoint(3, 4, "red"),
        Line(Point(0, 0), Point(5, 5)),
        Polygon(mutableListOf(Point(1, 1), Point(2, 2), Point(3, 1)))
    )

    for (shape in shapes) {
        shape.draw()
        shape.move(1, 1)
        shape.draw()

        if (shape is Polygon) {
            shape.moveAlongX(2)
            shape.moveAlongY(3)
            shape.draw()
        }
    }
}