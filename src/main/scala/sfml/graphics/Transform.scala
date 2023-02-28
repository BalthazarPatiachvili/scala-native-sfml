package sfml
package graphics

import scala.collection.immutable.ArraySeq

import scalanative.unsafe.*

import internal.Type.sfBoolToBoolean
import internal.graphics.Transform.*

import system.Vector2

final class Transform private[sfml] (private val transform: ResourceBuffer[sfTransform]):

    private[sfml] inline def toNativeTransform: Ptr[sfTransform] = transform.ptr

    def this() =
        this(ResourceBuffer { (r: Ptr[sfTransform]) => ctor(r) })

    def this(a00: Float, a01: Float, a02: Float, a10: Float, a11: Float, a12: Float, a20: Float, a21: Float, a22: Float) =
        this(ResourceBuffer { (r: Ptr[sfTransform]) =>
            ctor(
                r,
                a00,
                a01,
                a02,
                a10,
                a11,
                a12,
                a20,
                a21,
                a22
            )
        })

    final def combine(rhs: Transform): Transform =
        Transform.toTransform(sfTransform_combine(toNativeTransform, rhs.toNativeTransform))()

    final def matrix: ArraySeq[Float] =
        val matrix = !toNativeTransform

        ArraySeq.tabulate(16)(matrix(_))

    final def rotate(angle: Float): Transform =
        Transform.toTransform(sfTransform_rotate(toNativeTransform, angle))()

    final def rotate(angle: Float, centerX: Float, centerY: Float): Transform =
        Transform.toTransform(sfTransform_rotate(toNativeTransform, angle, centerX, centerY))()

    final def rotate(angle: Float, center: Vector2[Float]): Transform =
        Zone { implicit z => Transform.toTransform(sfTransform_rotate(toNativeTransform, angle, center.toNativeVector2))() }

    final def scale(scaleX: Float, scaleY: Float): Transform =
        Transform.toTransform(sfTransform_scale(toNativeTransform, scaleX, scaleY))()

    final def scale(scaleX: Float, scaleY: Float, centerX: Float, centerY: Float): Transform =
        Transform.toTransform(sfTransform_scale(toNativeTransform, scaleX, scaleY, centerX, centerY))()

    final def scale(factors: Vector2[Float]): Transform =
        Zone { implicit z => Transform.toTransform(sfTransform_scale(toNativeTransform, factors.toNativeVector2))() }

    final def scale(factors: Vector2[Float], center: Vector2[Float]): Transform =
        import internal.system.Vector2.sfVector2f

        Zone { implicit z =>
            val sfFactors: Ptr[sfVector2f] = factors.toNativeVector2
            val sfCenter: Ptr[sfVector2f] = center.toNativeVector2

            Transform.toTransform(sfTransform_scale(toNativeTransform, sfFactors, sfCenter))()
        }

    final def transformPoint(x: Float, y: Float): Vector2[Float] =
        Zone { implicit z =>
            Vector2.toVector2Float(toNativeTransform, x, y) { (data: Ptr[CStruct3[Ptr[sfTransform], CFloat, CFloat]]) =>
                sfTransform_transformPoint(data._1, data._2, data._3)
            }
        }

    final def transformPoint(point: Vector2[Float]): Vector2[Float] =
        import internal.system.Vector2.sfVector2f

        Zone { implicit z =>
            val sfPoint: Ptr[sfVector2f] = point.toNativeVector2

            Vector2.toVector2Float(toNativeTransform, sfPoint) { (data: Ptr[CStruct2[Ptr[sfTransform], Ptr[sfVector2f]]]) =>
                sfTransform_transformPoint(data._1, data._2)
            }
        }

    final def transformRect(rectangle: Rect[Float]): Rect[Float] =
        import internal.graphics.Rect.sfFloatRect

        Zone { implicit z =>
            val sfRectangle: Ptr[sfFloatRect] = rectangle.toNativeRect

            Rect.toRectFloat(toNativeTransform, sfRectangle) { (data: Ptr[CStruct2[Ptr[sfTransform], Ptr[sfFloatRect]]]) =>
                sfTransform_transformRect(data._1, data._2)
            }
        }

    final def translate(x: Float, y: Float): Transform =
        Transform.toTransform(sfTransform_translate(toNativeTransform, x, y))()

    final def translate(offset: Vector2[Float]): Transform =
        Zone { implicit z => Transform.toTransform(sfTransform_translate(toNativeTransform, offset.toNativeVector2))() }

    /* Operators */

    override def equals(other: Any): Boolean =
        other match
            case other: Transform => sfTransform_eq_sfTransform(toNativeTransform, other.toNativeTransform)
            case _                => false

    final def *(rhs: Transform): Transform =
        val transform = Transform()

        for i <- 0 until 16 do transform.toNativeTransform(i) = this.matrix(i)

        transform.combine(rhs)

    final def *=(rhs: Transform): Transform =
        combine(rhs)

    final def *(rhs: Vector2[Float]): Vector2[Float] =
        import internal.system.Vector2.sfVector2f

        Zone { implicit z =>
            val sfVector: Ptr[sfVector2f] = rhs.toNativeVector2

            Vector2.toVector2Float(toNativeTransform, sfVector) { (data: Ptr[CStruct2[Ptr[sfTransform], Ptr[sfVector2f]]]) =>
                sfTransform_ml_sfVector2f(data._1, data._2)
            }
        }

object Transform:

    def Identity(): Transform =
        Transform()

    extension (transform: Immutable[Transform])
        def matrix: ArraySeq[Float] = transform.get.matrix

        def transformPoint(x: Float, y: Float): Vector2[Float] =
            transform.get.transformPoint(x, y)

        def transformPoint(point: Vector2[Float]): Vector2[Float] =
            transform.get.transformPoint(point)

        def transformRect(rectangle: Rect[Float]): Rect[Float] =
            transform.get.transformRect(rectangle)

    extension (transform: sfTransform)
        private[sfml] def toTransform(): Transform =
            Transform(ResourceBuffer { (r: Ptr[sfTransform]) =>
                for i <- 0 until 16 do r(i) = transform(i)
            })

    extension (transform: Ptr[sfTransform])
        private[sfml] def toTransform(): Transform =
            Transform(ResourceBuffer(transform))
