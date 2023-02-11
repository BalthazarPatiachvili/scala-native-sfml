package sfml
package system

import math.Numeric.Implicits.infixNumericOps

import scala.annotation.targetName
import scala.language.implicitConversions
import scalanative.unsafe.*

import internal.system.Vector2.*

final case class Vector2[T: Numeric](val x: T = 0, val y: T = 0):

    @targetName("toNative_sfVector2f")
    private[sfml] inline def toNativeVector2(using Zone)(implicit ev: T =:= Float): Ptr[sfVector2f] =
        val vector2 = alloc[sfVector2f]()

        vector2._1 = x
        vector2._2 = y
        vector2

    @targetName("toNative_sfVector2i")
    private[sfml] inline def toNativeVector2(using Zone)(implicit ev: T =:= Int): Ptr[sfVector2i] =
        val vector2 = alloc[sfVector2i]()

        vector2._1 = x
        vector2._2 = y
        vector2

    def +(rhs: Vector2[T]) =
        Vector2(x + rhs.x, y + rhs.y)

    def *(rhs: Vector2[T]) =
        Vector2(x * rhs.x, y * rhs.y)

    def *(rhs: T) =
        Vector2(x * rhs, y * rhs)

object Vector2:
    extension (vector2: Ptr[sfVector2i])
        private[sfml] def toVector2Int(): Vector2[Int] =
            Vector2(vector2._1, vector2._2)

    extension (vector2: Ptr[sfVector2f])
        private[sfml] def toVector2Float(): Vector2[Float] =
            Vector2(vector2._1, vector2._2)

    extension (vector2: internal.Type.sfSplit[sfVector2i])
        private[sfml] def toVector2Int(): Vector2[Int] =
            val high = (vector2 >> 32).toInt
            val low = vector2.toInt
            Vector2(low, high)

    implicit def tupleToVector2Float[T: Numeric](tuple: (T, T)): Vector2[Float] =
        Vector2(tuple._1.toFloat, tuple._2.toFloat)

    implicit def tupleToVector2Int[T: Numeric](tuple: (T, T)): Vector2[Int] =
        Vector2(tuple._1.toInt, tuple._2.toInt)
