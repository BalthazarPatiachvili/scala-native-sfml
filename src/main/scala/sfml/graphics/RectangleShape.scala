package sfml
package graphics

import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichLong

import internal.graphics.RectangleShape.*

import system.Vector2

class RectangleShape private[sfml] (private val circleShape: ResourceBuffer[sfRectangleShape])
    extends Shape(AbstractResourceBuffer(circleShape.ptr.at1)):

    private[sfml] inline def toNativeRectangleShape = circleShape.ptr

    def this(size: Vector2[Float]) =
        this(ResourceBuffer { (r: Ptr[sfRectangleShape]) =>
            Zone { implicit z => ctor(r, size.toNativeVector2) }
        })

    def this() =
        this(Vector2[Float]())

    override final def point(index: Long): Vector2[Float] =
        Zone { implicit z =>
            Vector2.toVector2Float(toNativeRectangleShape, index.toULong) { (data: Ptr[CStruct2[Ptr[sfRectangleShape], CSize]]) =>
                sfRectangleShape_getPoint(data._1, data._2)
            }
        }

    override final def pointCount: Long =
        sfRectangleShape_getPointCount(toNativeRectangleShape).toLong

    final def size: Vector2[Float] =
        Vector2.toVector2Float(sfRectangleShape_getSize(toNativeRectangleShape))()

    final def size_=(size: Vector2[Float]): Unit =
        Zone { implicit z => sfRectangleShape_setSize(toNativeRectangleShape, size.toNativeVector2) }
