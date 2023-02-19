package sfml
package graphics

import math.Numeric.Implicits.infixNumericOps

import scala.annotation.targetName
import scala.language.implicitConversions
import scalanative.unsafe.*

import internal.graphics.Rect.*

import system.Vector2

final case class Rect[T: Numeric](val left: T = 0, val top: T = 0, val width: T = 0, val height: T = 0):

    @targetName("toNativeFloatRect")
    private[sfml] inline def toNativeRect(using Zone)(implicit ev: T =:= Float): Ptr[sfFloatRect] =
        val rect = alloc[sfFloatRect]()

        rect._1 = left
        rect._2 = top
        rect._3 = width
        rect._4 = height
        rect

    @targetName("toNativeIntRect")
    private[sfml] inline def toNativeRect(using Zone)(implicit ev: T =:= Int): Ptr[sfIntRect] =
        val rect = alloc[sfIntRect]()

        rect._1 = left
        rect._2 = top
        rect._3 = width
        rect._4 = height
        rect

    def contains(x: T, y: T)(implicit num: Numeric[T]): Boolean =
        import num.*

        val minX = min(left, left + width)
        val maxX = max(left, left + width)
        val minY = min(top, top + height)
        val maxY = max(top, top + height)

        x >= minX && x < maxX && y >= minY && y < maxY

    def contains(point: Vector2[T])(implicit num: Numeric[T]): Boolean =
        contains(point.x, point.y)

    def intersects(other: Rect[T])(implicit num: Numeric[T]): Boolean =
        import num.*

        val r1MinX = min(left, left + width)
        val r1MaxX = max(left, left + width)
        val r1MinY = min(top, top + height)
        val r1MaxY = max(top, top + height)

        val r2MinX = min(other.left, other.left + other.width)
        val r2MaxX = max(other.left, other.left + other.width)
        val r2MinY = min(other.top, other.top + other.height)
        val r2MaxY = max(other.top, other.top + other.height)

        val interLeft = max(r1MinX, r2MinX)
        val interTop = max(r1MinY, r2MinY)
        val interRight = min(r1MaxX, r2MaxX)
        val interBottom = min(r1MaxY, r2MaxY)

        interLeft < interRight && interTop < interBottom

object Rect:
    extension (rect: Ptr[sfIntRect])
        private[sfml] def toRectInt(): Rect[Int] =
            Rect(rect._1, rect._2, rect._3, rect._4)

    extension (rect: Ptr[sfFloatRect])
        private[sfml] def toRectFloat(): Rect[Float] =
            Rect(rect._1, rect._2, rect._3, rect._4)

    implicit def tupleToRectFloat[T: Numeric](tuple: (T, T, T, T)): Rect[Float] =
        Rect(tuple._1.toFloat, tuple._2.toFloat, tuple._3.toFloat, tuple._4.toFloat)

    implicit def tupleToRectInt[T: Numeric](tuple: (T, T, T, T)): Rect[Int] =
        Rect(tuple._1.toInt, tuple._2.toInt, tuple._3.toInt, tuple._4.toInt)

    private[sfml] def toRectInt()(
        callback: CFuncPtr0[sfIntRect]
    )(using Zone): Rect[Int] =
        ReturnTypeHandler(sfIntRect_typeHandler, callback)().toRectInt()

    private[sfml] def toRectInt[T1: Tag](arg1: T1)(
        callback: CFuncPtr1[Ptr[CStruct1[T1]], sfIntRect]
    )(using Zone): Rect[Int] =
        ReturnTypeHandler(sfIntRect_typeHandler, callback)(arg1).toRectInt()

    private[sfml] def toRectInt[T1: Tag, T2: Tag](arg1: T1, arg2: T2)(
        callback: CFuncPtr1[Ptr[CStruct2[T1, T2]], sfIntRect]
    )(using Zone): Rect[Int] =
        ReturnTypeHandler(sfIntRect_typeHandler, callback)(arg1, arg2).toRectInt()

    private[sfml] def toRectInt[T1: Tag, T2: Tag, T3: Tag](arg1: T1, arg2: T2, arg3: T3)(
        callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], sfIntRect]
    )(using Zone): Rect[Int] =
        ReturnTypeHandler(sfIntRect_typeHandler, callback)(arg1, arg2, arg3).toRectInt()

    private[sfml] def toRectFloat()(
        callback: CFuncPtr0[sfFloatRect]
    )(using Zone): Rect[Float] =
        ReturnTypeHandler(sfFloatRect_typeHandler, callback)().toRectFloat()

    private[sfml] def toRectFloat[T1: Tag](arg1: T1)(
        callback: CFuncPtr1[Ptr[CStruct1[T1]], sfFloatRect]
    )(using Zone): Rect[Float] =
        ReturnTypeHandler(sfFloatRect_typeHandler, callback)(arg1).toRectFloat()

    private[sfml] def toRectFloat[T1: Tag, T2: Tag](arg1: T1, arg2: T2)(
        callback: CFuncPtr1[Ptr[CStruct2[T1, T2]], sfFloatRect]
    )(using Zone): Rect[Float] =
        ReturnTypeHandler(sfFloatRect_typeHandler, callback)(arg1, arg2).toRectFloat()

    private[sfml] def toRectFloat[T1: Tag, T2: Tag, T3: Tag](arg1: T1, arg2: T2, arg3: T3)(
        callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], sfFloatRect]
    )(using Zone): Rect[Float] =
        ReturnTypeHandler(sfFloatRect_typeHandler, callback)(arg1, arg2, arg3).toRectFloat()
