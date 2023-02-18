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
    implicit def tupleToVector2Float[T: Numeric](tuple: (T, T)): Vector2[Float] =
        Vector2(tuple._1.toFloat, tuple._2.toFloat)

    implicit def tupleToVector2Int[T: Numeric](tuple: (T, T)): Vector2[Int] =
        Vector2(tuple._1.toInt, tuple._2.toInt)

    extension (vector2: Ptr[sfVector2i])
        private[sfml] def toVector2Int(): Vector2[Int] =
            Vector2(vector2._1, vector2._2)

    extension (vector2: Ptr[sfVector2f])
        private[sfml] def toVector2Float(): Vector2[Float] =
            Vector2(vector2._1, vector2._2)

    private[sfml] def toVector2Int()(
        callback: CFuncPtr0[sfVector2i]
    )(using Zone): Vector2[Int] =
        ReturnTypeHandler(sfVector2i_typeHandler, callback)().toVector2Int()

    private[sfml] def toVector2Int[T1: Tag](arg1: T1)(
        callback: CFuncPtr1[Ptr[CStruct1[T1]], sfVector2i]
    )(using Zone): Vector2[Int] =
        ReturnTypeHandler(sfVector2i_typeHandler, callback)(arg1).toVector2Int()

    private[sfml] def toVector2Int[T1: Tag, T2: Tag](arg1: T1, arg2: T2)(
        callback: CFuncPtr1[Ptr[CStruct2[T1, T2]], sfVector2i]
    )(using Zone): Vector2[Int] =
        ReturnTypeHandler(sfVector2i_typeHandler, callback)(arg1, arg2).toVector2Int()

    private[sfml] def toVector2Int[T1: Tag, T2: Tag, T3: Tag](arg1: T1, arg2: T2, arg3: T3)(
        callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], sfVector2i]
    )(using Zone): Vector2[Int] =
        ReturnTypeHandler(sfVector2i_typeHandler, callback)(arg1, arg2, arg3).toVector2Int()

    private[sfml] def toVector2Float()(
        callback: CFuncPtr0[sfVector2f]
    )(using Zone): Vector2[Float] =
        ReturnTypeHandler(sfVector2f_typeHandler, callback)().toVector2Float()

    private[sfml] def toVector2Float[T1: Tag](arg1: T1)(
        callback: CFuncPtr1[Ptr[CStruct1[T1]], sfVector2f]
    )(using Zone): Vector2[Float] =
        ReturnTypeHandler(sfVector2f_typeHandler, callback)(arg1).toVector2Float()

    private[sfml] def toVector2Float[T1: Tag, T2: Tag](arg1: T1, arg2: T2)(
        callback: CFuncPtr1[Ptr[CStruct2[T1, T2]], sfVector2f]
    )(using Zone): Vector2[Float] =
        ReturnTypeHandler(sfVector2f_typeHandler, callback)(arg1, arg2).toVector2Float()

    private[sfml] def toVector2Float[T1: Tag, T2: Tag, T3: Tag](arg1: T1, arg2: T2, arg3: T3)(
        callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], sfVector2f]
    )(using Zone): Vector2[Float] =
        ReturnTypeHandler(sfVector2f_typeHandler, callback)(arg1, arg2, arg3).toVector2Float()
