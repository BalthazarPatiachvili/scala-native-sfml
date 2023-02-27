package sfml
package graphics

import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichLong

import internal.graphics.CircleShape.*

import system.Vector2

class CircleShape private[sfml] (private val circleShape: ResourceBuffer[sfCircleShape])
    extends Shape(AbstractResourceBuffer(circleShape.ptr.at1)):

    private[sfml] inline def toNativeCircleShape = circleShape.ptr

    def this(radius: Float = 0, pointCount: Long = 30) =
        this(ResourceBuffer { (r: Ptr[sfCircleShape]) => ctor(r, radius, pointCount.toULong) })

    def this() =
        this(0, 30)

    override final def point(index: Long): Vector2[Float] =
        Zone { implicit z =>
            Vector2.toVector2Float(toNativeCircleShape, index.toULong) { (data: Ptr[CStruct2[Ptr[sfCircleShape], CSize]]) =>
                sfCircleShape_getPoint(data._1, data._2)
            }
        }

    override final def pointCount: Long =
        sfCircleShape_getPointCount(toNativeCircleShape).toLong

    final def pointCount_=(count: Long) =
        sfCircleShape_setPointCount(toNativeCircleShape, count.toULong)

    final def radius: Float =
        sfCircleShape_getRadius(toNativeCircleShape)

    final def radius_=(radius: Float): Unit =
        sfCircleShape_setRadius(toNativeCircleShape, radius)
