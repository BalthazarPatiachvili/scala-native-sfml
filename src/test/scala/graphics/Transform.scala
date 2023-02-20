import org.junit.Assert.*
import org.junit.Test

import sfml.graphics.*
import sfml.system.*

/* Tests based on: https://github.com/SFML/SFML/blob/master/test/Graphics/Transform.test.cpp */
class TestTransform:

    @Test def constructorDefault(): Unit =
        assertEquals(Transform.Identity(), Transform())

    @Test def constructorMatrix3x3(): Unit =
        val transform = Transform(10, 11, 12, 13, 14, 15, 16, 17, 18)

        assertArrayEquals(Array[Float](10, 13, 0, 16, 11, 14, 0, 17, 0, 0, 1, 0, 12, 15, 0, 18), transform.matrix, 0)

    @Test def identityMatrix(): Unit =
        assertArrayEquals(Array[Float](1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1), Transform.Identity().matrix, 0)

    @Test def transformPoint(): Unit =
        assertEquals(Vector2[Float](-10, -10), Transform.Identity().transformPoint((-10, -10)))
        assertEquals(Vector2[Float](-1, -1), Transform.Identity().transformPoint((-1, -1)))
        assertEquals(Vector2[Float](-1, 0), Transform.Identity().transformPoint((-1, 0)))
        assertEquals(Vector2[Float](0, 0), Transform.Identity().transformPoint((0, 0)))
        assertEquals(Vector2[Float](0, 1), Transform.Identity().transformPoint((0, 1)))
        assertEquals(Vector2[Float](1, 1), Transform.Identity().transformPoint((1, 1)))
        assertEquals(Vector2[Float](10, 10), Transform.Identity().transformPoint((10, 10)))

        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)
        assertEquals(Vector2[Float](0, -5), transform.transformPoint((-1, -1)))
        assertEquals(Vector2[Float](3, 4), transform.transformPoint((0, 0)))
        assertEquals(Vector2[Float](6, 13), transform.transformPoint((1, 1)))

    @Test def transformRect(): Unit =
        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)

        assertEquals(Rect[Float](-300, -300, 100, 100), Transform.Identity().transformRect((-200, -200, -100, -100)))
        assertEquals(Rect[Float](0, 0, 0, 0), Transform.Identity().transformRect((0, 0, 0, 0)))
        assertEquals(Rect[Float](100, 100, 200, 200), Transform.Identity().transformRect((100, 100, 200, 200)))

        assertEquals(Rect[Float](-297, -896, 600, 1800), transform.transformRect((-100, -100, 200, 200)))
        assertEquals(Rect[Float](3, 4, 0, 0), transform.transformRect((0, 0, 0, 0)))
        assertEquals(Rect[Float](303, 904, 600, 1800), transform.transformRect((100, 100, 200, 200)))

    @Test def combine(): Unit =
        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)

        assertEquals(Transform.Identity(), Transform.Identity().combine(Transform.Identity()))
        assertEquals(Transform.Identity(), Transform.Identity().combine(Transform.Identity()).combine(Transform.Identity()))

        assertEquals(transform, Transform.Identity().combine(transform))
        assertEquals(transform, transform.combine(Transform.Identity()))
        assertEquals(Transform(18, 18, 14, 36, 41, 36, 14, 18, 18), transform.combine(transform))
        assertEquals(
            Transform(672, 1216, 914, 1604, 2842, 2108, 752, 1288, 942),
            transform.combine(Transform(10, 2, 3, 4, 50, 40, 30, 20, 10))
        )

    @Test def translate(): Unit =
        val transform = Transform(9, 8, 7, 6, 5, 4, 3, 2, 1)

        assertEquals(Transform(9, 8, 257, 6, 5, 164, 3, 2, 71), transform.translate((10, 20)))
        assertEquals(Transform(9, 8, 507, 6, 5, 324, 3, 2, 141), transform.translate((10, 20)))

    @Test def rotateAroundOrigin(): Unit =
        assertArrayEquals(
            Transform(0, -1, 0, 1, 0, 0, 0, 0, 1).matrix.map(_.toDouble),
            Transform().rotate(90).matrix.map(_.toDouble),
            0.01
        )

    @Test def rotateAroundCustomPoint(): Unit =
        assertArrayEquals(
            Transform(0, -1, 1, 1, 0, -1, 0, 0, 1).matrix.map(_.toDouble),
            Transform().rotate(90, (1, 0)).matrix.map(_.toDouble),
            0.01
        )

    @Test def scaleAboutOrigin(): Unit =
        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)

        assertEquals(Transform(2, 8, 3, 8, 20, 4, 6, 8, 1), transform.scale((2, 4)))
        assertEquals(Transform(0, 0, 3, 0, 0, 4, 0, 0, 1), transform.scale((0, 0)))
        assertEquals(Transform(0, 0, 3, 0, 0, 4, 0, 0, 1), transform.scale((10, 10)))

    @Test def scaleAboutCustomPoint(): Unit =
        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)

        assertEquals(Transform(1, 4, 3, 4, 10, 4, 3, 4, 1), transform.scale((1, 2), (1, 0)))
        assertEquals(Transform(0, 0, 4, 0, 0, 8, 0, 0, 4), transform.scale((0, 0), (1, 0)))

    @Test def operatorTimes(): Unit =
        assertEquals(Transform.Identity(), Transform.Identity() * Transform.Identity())
        assertEquals(Transform.Identity(), Transform.Identity() * Transform.Identity() * Transform.Identity())

        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)
        assertEquals(transform, Transform.Identity() * transform)
        assertEquals(transform, transform * Transform.Identity())

        assertEquals(Transform(18, 18, 14, 36, 41, 36, 14, 18, 18), transform * transform)
        assertEquals(
            Transform(108, 162, 113, 180, 338, 252, 68, 126, 99),
            transform * Transform(10, 2, 3, 4, 50, 40, 30, 20, 10)
        )

    @Test def operatorTimesAssign(): Unit =
        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)

        transform *= Transform.Identity()
        assertEquals(Transform(1, 2, 3, 4, 5, 4, 3, 2, 1), transform)

        transform *= transform
        assertEquals(Transform(18, 18, 14, 36, 41, 36, 14, 18, 18), transform)

        transform *= Transform(10, 2, 3, 4, 50, 40, 30, 20, 10)
        assertEquals(Transform(672, 1216, 914, 1604, 2842, 2108, 752, 1288, 942), transform)

    @Test def operatorTimesWithVector(): Unit =
        assertEquals(Vector2[Float](-10, -10), Transform.Identity() * (-10, -10))
        assertEquals(Vector2[Float](-1, -1), Transform.Identity() * (-1, -1))
        assertEquals(Vector2[Float](-1, 0), Transform.Identity() * (-1, 0))
        assertEquals(Vector2[Float](0, 0), Transform.Identity() * (0, 0))
        assertEquals(Vector2[Float](0, 1), Transform.Identity() * (0, 1))
        assertEquals(Vector2[Float](1, 1), Transform.Identity() * (1, 1))
        assertEquals(Vector2[Float](10, 10), Transform.Identity() * (10, 10))

        val transform = Transform(1, 2, 3, 4, 5, 4, 3, 2, 1)
        assertEquals(Vector2[Float](0, -5), transform * (-1, -1))
        assertEquals(Vector2[Float](3, 4), transform * (0, 0))
        assertEquals(Vector2[Float](6, 13), transform * (1, 1))

    @Test def operatorEquals(): Unit =
        assertTrue(Transform.Identity() == Transform.Identity())
        assertTrue(Transform() == Transform())
        assertTrue(
            Transform(0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001) == Transform(0.0001, 0.0001, 0.0001,
                0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001)
        )
        assertTrue(
            Transform(1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000) == Transform(1000, 1000, 1000, 1000, 1000, 1000, 1000,
                1000, 1000)
        )

    @Test def operatorNotEquals(): Unit =
        assertFalse(Transform.Identity() != Transform.Identity())
        assertFalse(Transform() != Transform())
        assertFalse(
            Transform(0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001) != Transform(0.0001, 0.0001, 0.0001,
                0.0001, 0.0001, 0.0001, 0.0001, 0.0001, 0.0001)
        )
        assertFalse(
            Transform(1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000) != Transform(1000, 1000, 1000, 1000, 1000, 1000, 1000,
                1000, 1000)
        )

        assertTrue(Transform(1, 0, 0, 0, 0, 0, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 1, 0, 0, 0, 0, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 1, 0, 0, 0, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 1, 0, 0, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 0, 1, 0, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 0, 0, 1, 0, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 0, 0, 0, 1, 0, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 0, 0, 0, 0, 1, 0) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
        assertTrue(Transform(0, 0, 0, 0, 0, 0, 0, 0, 1) != Transform(0, 0, 0, 0, 0, 0, 0, 0, 0))
