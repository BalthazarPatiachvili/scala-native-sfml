import org.junit.Assert.*
import org.junit.Test

import scala.util.Using

import sfml.graphics.*
import sfml.system.*

/* Tests based on: https://github.com/SFML/SFML/blob/master/test/Graphics/CircleShape.test.cpp */
class CircleShapeTest extends NativeTest:

    @Test def constructorDefault(): Unit =
        Using(CircleShape(0)) { circle =>
            assertEquals(0, circle.radius, 0)
            assertEquals(30, circle.pointCount)

            for i <- 0 until 30 do assertEquals(Vector2[Float](0, 0), circle.point(i))
        }.get

    @Test def constructorRadius(): Unit =
        val points = Array[Vector2[Float]](
            (15, 0),
            (18.118675232f, 0.327785492f),
            (21.101049423f, 1.296817780f),
            (23.816780090f, 2.864745140f),
            (26.147172928f, 4.963042259f),
            (27.990381241f, 7.500000000f),
            (29.265848160f, 10.364745140f),
            (29.917827606f, 13.432072639f),
            (29.917827606f, 16.567928314f),
            (29.265846252f, 19.635257721f),
            (27.990381241f, 22.500000000f),
            (26.147171021f, 25.036960602f),
            (23.816780090f, 27.135253906f),
            (21.101049423f, 28.703182220f),
            (18.118675232f, 29.672214508f),
            (14.999999046f, 30.000000000f),
            (11.881320953f, 29.672214508f),
            (8.898950577f, 28.703182220f),
            (6.183218956f, 27.135253906f),
            (3.852828979f, 25.036960602f),
            (2.009618759f, 22.500000000f),
            (0.734152794f, 19.635255814f),
            (0.082171440f, 16.567928314f),
            (0.082171440f, 13.432073593f),
            (0.734151840f, 10.364746094f),
            (2.009618759f, 7.500000000f),
            (3.852827072f, 4.963040352f),
            (6.183218002f, 2.864748001f),
            (8.898950577f, 1.296818733f),
            (11.881320953f, 0.327786446f)
        )

        Using(CircleShape(15)) { circle =>
            assertEquals(15, circle.radius, 0)
            assertEquals(30, circle.pointCount)

            for i <- 0 until 30 do
                assertEquals(points(i).x, circle.point(i).x, 0.0001f)
                assertEquals(points(i).y, circle.point(i).y, 0.0001f)
        }.get

    @Test def constructorRadiusAndPointCount(): Unit =
        val points = Array[Vector2[Float]](
            (5, 0),
            (8.535533905f, 1.464465857f),
            (10.000000000f, 5.000000000f),
            (8.535533905f, 8.535533905f),
            (5.000000000f, 10.000000000f),
            (1.464466095f, 8.535533905f),
            (0.000000000f, 4.999999523f),
            (1.464465857f, 1.464466572f)
        )

        Using(CircleShape(5, 8)) { circle =>
            assertEquals(5, circle.radius, 0)
            assertEquals(8, circle.pointCount)

            for i <- 0 until 8 do
                assertEquals(points(i).x, circle.point(i).x, 0.0001f)
                assertEquals(points(i).y, circle.point(i).y, 0.0001f)
        }.get

    @Test def setRadius(): Unit =
        val points = Array[Vector2[Float]](
            (10.000000000f, 0.000000000f),
            (18.660253525f, 5.000000000f),
            (18.660253525f, 15.000000000f),
            (10.000000000f, 20.000000000f),
            (1.339746475f, 15.000000000f),
            (1.339745522f, 5.000000000f)
        )

        Using(CircleShape(1, 6)) { circle =>
            circle.radius = 10

            assertEquals(10, circle.radius, 0)
            assertEquals(6, circle.pointCount)

            for i <- 0 until 6 do
                assertEquals(points(i).x, circle.point(i).x, 0.0001f)
                assertEquals(points(i).y, circle.point(i).y, 0.0001f)
        }.get

    @Test def setPointCount(): Unit =
        val points = Array[Vector2[Float]](
            (3.999999762f, 0.000000000f),
            (8.000000000f, 4.000000000f),
            (3.999999762f, 8.000000000f),
            (0.000000000f, 3.999999762f)
        )

        Using(CircleShape(4, 10)) { circle =>
            circle.pointCount = 4

            assertEquals(4, circle.radius, 0)
            assertEquals(4, circle.pointCount)

            for i <- 0 until 4 do
                assertEquals(points(i).x, circle.point(i).x, 0.0001f)
                assertEquals(points(i).y, circle.point(i).y, 0.0001f)
        }.get

    @Test def equilateralTriangle(): Unit =
        val points = Array[Vector2[Float]](
            (1.999999881f, 0.000000000f),
            (3.732050896f, 3.000000000f),
            (0.267949224f, 3.000000000f)
        )

        Using(CircleShape(2, 3)) { circle =>
            assertEquals(2, circle.radius, 0)
            assertEquals(3, circle.pointCount)

            for i <- 0 until 3 do
                assertEquals(points(i).x, circle.point(i).x, 0.0001f)
                assertEquals(points(i).y, circle.point(i).y, 0.0001f)
        }.get
