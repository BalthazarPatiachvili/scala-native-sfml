import org.junit.Assert.*
import org.junit.Test

import scala.util.Using

import sfml.ResourceBuffer
import sfml.graphics.*
import sfml.system.*

private[this] class TriangleShape(size: Vector2[Float] = Vector2()) extends Shape:

    override def point(index: Long): Vector2[Float] =
        index match
            case 0 => Vector2(size.x / 2, 0)
            case 1 => Vector2(0, size.y)
            case _ => size

    override def pointCount: Long = 3

/* Tests based on: https://github.com/SFML/SFML/blob/master/test/Graphics/Shape.test.cpp */
class ShapeTest extends NativeTest:

    @Test def constructorDefault(): Unit =
        Using(TriangleShape((0, 0))) { triangleShape =>
            assertEquals(None, triangleShape.texture)
            assertEquals(Rect[Int](), triangleShape.textureRect)
            assertEquals(Color.White(), triangleShape.fillColor)
            assertEquals(Color.White(), triangleShape.outlineColor)
            assertEquals(0, triangleShape.outlineThickness, 0)
            assertEquals(Rect[Float](), triangleShape.localBounds)
            assertEquals(Rect[Float](), triangleShape.globalBounds)
        }.get

    @Test def textureRect(): Unit =
        Using(TriangleShape()) { triangleShape =>
            triangleShape.textureRect = (4, 5, 6, 7)

            assertEquals(Rect[Int](4, 5, 6, 7), triangleShape.textureRect)
        }.get

    @Test def fillColor(): Unit =
        Using(TriangleShape()) { triangleShape =>
            triangleShape.fillColor = Color.Cyan()

            assertEquals(Color.Cyan(), triangleShape.fillColor)
        }.get

    @Test def outlineColor(): Unit =
        Using(TriangleShape()) { triangleShape =>
            triangleShape.outlineColor = Color.Magenta()

            assertEquals(Color.Magenta(), triangleShape.outlineColor)
        }.get

    @Test def outlineThickness(): Unit =
        Using(TriangleShape((10, 10))) { triangleShape =>
            triangleShape.outlineThickness = 3.14f

            assertEquals(3.14f, triangleShape.outlineThickness, 0)
        }.get

    @Test def virtualFunctions(): Unit =
        Using(TriangleShape((2, 2))) { triangleShape =>
            assertEquals(3, triangleShape.pointCount)

            assertEquals(Vector2(1, 0), triangleShape.point(0))
            assertEquals(Vector2(0, 2), triangleShape.point(1))
            assertEquals(Vector2(2, 2), triangleShape.point(2))
        }.get

    @Test def bounds(): Unit =
        Using(TriangleShape((2, 3))) { triangleShape =>
            assertEquals(Rect[Float](0, 0, 2, 3), triangleShape.localBounds)
            assertEquals(Rect[Float](0, 0, 2, 3), triangleShape.globalBounds)

            triangleShape.move((1, 1))
            triangleShape.rotate(90)

            assertEquals(Rect[Float](0, 0, 2, 3), triangleShape.localBounds)
            assertEquals(-2f, triangleShape.globalBounds.left, 0.0001)
            assertEquals(1f, triangleShape.globalBounds.top, 0.0001)
            assertEquals(3f, triangleShape.globalBounds.width, 0.0001)
            assertEquals(2f, triangleShape.globalBounds.height, 0.0001)
        }.get
