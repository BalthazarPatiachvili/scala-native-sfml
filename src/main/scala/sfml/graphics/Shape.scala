package sfml
package graphics

import scalanative.unsafe.*
import scalanative.unsafe.CFuncPtr0.fromScalaFunction
import scalanative.unsafe.CFuncPtr1.fromScalaFunction
import scalanative.unsafe.CFuncPtr2.fromScalaFunction
import scalanative.unsigned.UnsignedRichLong

import internal.Type.booleanToSfBool
import internal.graphics.Shape.*

import system.Vector2

import sfml.internal.system.Vector2.sfVector2f

private val vtable = AbstractResourceBuffer.createVTable(
    CFuncPtr.toPtr((ptr: Ptr[CStruct2[sfShape, Shape]]) => ptr._2.close()),
    CFuncPtr.toPtr(() => println("Copy constructor not implemented for Shape, please report this in lafeychine/scala-native-sfml")),
    CFuncPtr.toPtr(() => println("Move constructor not implemented for Shape, please report this in lafeychine/scala-native-sfml")),
    CFuncPtr.toPtr((ptr: Ptr[CStruct2[sfShape, Shape]]) => ptr._2.pointCount.toULong),
    CFuncPtr.toPtr((ptr: Ptr[CStruct2[sfShape, Shape]], index: CSize) => {
        Zone { implicit z => ptr._2.point(index.toLong).toNativeVector2: sfVector2f }
    })
)

@SuppressWarnings(Array("org.wartremover.warts.Null"))
abstract class Shape private[sfml] (private val shape: AbstractResourceBuffer[sfShape])
    extends Transformable(ResourceBuffer(shape.ptr(null).at2))
    with Drawable
    with Resource:

    private[sfml] inline def toNativeShape: Ptr[sfShape] = shape.ptr(this)

    protected def this() =
        this(AbstractResourceBuffer(vtable) { (r: Ptr[sfShape]) =>
            ctor(r)
        })

        /* NOTE: Added by this binding, as it is more revelant */
        update()

    override def close(): Unit =
        Shape.close(toNativeShape)()

    override final def draw(target: RenderTarget, states: RenderStates): Unit =
        Zone { implicit z => RenderTarget.patch_draw(toNativeShape.at1, target, states) }

    final def globalBounds: Rect[Float] =
        Zone { implicit z =>
            Rect.toRectFloat(toNativeShape) { (data: Ptr[CStruct1[Ptr[sfShape]]]) =>
                sfShape_getGlobalBounds(data._1)
            }
        }

    final def localBounds: Rect[Float] =
        Zone { implicit z =>
            Rect.toRectFloat(toNativeShape) { (data: Ptr[CStruct1[Ptr[sfShape]]]) =>
                sfShape_getLocalBounds(data._1)
            }
        }

    def point(index: Long): Vector2[Float]

    def pointCount: Long

    final protected def update(): Unit =
        sfShape_update(toNativeShape)

    /* Getter / Setter */

    final def fillColor: Color =
        Color.toColor(sfShape_getFillColor(toNativeShape))()

    final def fillColor_=(color: Color): Unit =
        Zone { implicit z => sfShape_setFillColor(toNativeShape, color.toNativeColor) }

    final def outlineColor: Color =
        Color.toColor(sfShape_getOutlineColor(toNativeShape))()

    final def outlineColor_=(color: Color): Unit =
        Zone { implicit z => sfShape_setOutlineColor(toNativeShape, color.toNativeColor) }

    final def outlineThickness: Float =
        sfShape_getOutlineThickness(toNativeShape)

    final def outlineThickness_=(thickness: Float): Unit =
        sfShape_setOutlineThickness(toNativeShape, thickness)

    final def texture: Option[Immutable[Texture]] =
        val ptr = sfShape_getTexture(toNativeShape)

        if ptr == null then None else Option(Immutable(Texture(ResourceBuffer(ptr))))

    final def texture_=(texture: Texture, resetRect: Boolean = false) =
        Zone { implicit z => sfShape_setTexture(toNativeShape, texture.toNativeTexture, resetRect) }

    final def textureRect: Rect[Int] =
        Rect.toRectInt(sfShape_getTextureRect(toNativeShape))()

    final def textureRect_=(rect: Rect[Int]): Unit =
        Zone { implicit z => sfShape_setTextureRect(toNativeShape, rect.toNativeRect) }

object Shape:

    extension (shape: Immutable[Shape])
        def point(index: Long): Vector2[Float] = shape.get.point(index)

        def pointCount: Long = shape.get.pointCount

    extension (shape: Ptr[sfShape])
        private[sfml] def close(): Unit =
            dtor(shape)
