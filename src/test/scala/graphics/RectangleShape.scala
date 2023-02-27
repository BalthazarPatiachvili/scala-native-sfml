import org.junit.Assert.*
import org.junit.Test

import scala.util.Using

import sfml.graphics.*
import sfml.system.*

/* Tests based on: https://github.com/SFML/SFML/blob/master/test/Graphics/RectangleShape.test.cpp */
class RectangleShapeTest extends NativeTest:

    @Test def constructorDefault(): Unit =
        Using(RectangleShape()) { rectangle =>
            assertEquals(Vector2[Float](0, 0), rectangle.size)
            assertEquals(4, rectangle.pointCount)

            for i <- 0 until 4 do assertEquals(Vector2[Float](0, 0), rectangle.point(i))
        }.get

    @Test def constructorSize(): Unit =
        val points = Array[Vector2[Float]](
            (0, 0),
            (9, 0),
            (9, 8),
            (0, 8)
        )

        Using(RectangleShape((9, 8))) { rectangle =>
            assertEquals(Vector2[Float](9, 8), rectangle.size)
            assertEquals(4, rectangle.pointCount)

            for i <- 0 until 4 do assertEquals(points(i), rectangle.point(i))
        }.get

    @Test def size(): Unit =
        Using(RectangleShape((7, 6))) { rectangle =>
            rectangle.size = (5, 4)

            assertEquals(Vector2[Float](5, 4), rectangle.size)
        }.get
