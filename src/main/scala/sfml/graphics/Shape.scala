package sfml
package graphics

import scalanative.unsafe.*

import internal.Type.booleanToSfBool
import internal.graphics.Shape.*

import system.Vector2

abstract class Shape private[sfml] (private val shape: ResourceBuffer[sfShape])
    extends Transformable(ResourceBuffer(shape.ptr.at2))
    with Drawable
    with Resource:

    private[sfml] inline def toNativeShape: Ptr[sfShape] = shape.ptr

    override def close(): Unit =
        Shape.close(toNativeShape)()

    override final def draw(target: RenderTarget, states: RenderStates): Unit =
        Zone { implicit z => RenderTarget.patch_draw(toNativeShape.at1, target, states) }

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

    // NOTE: To be able to use [`texture_=`]
    final def texture = ()

    final def texture_=(texture: Texture, resetRect: Boolean = false) =
        Zone { implicit z => sfShape_setTexture(toNativeShape, texture.toNativeTexture, resetRect) }

    final def textureRect: Rect[Int] =
        Rect.toRectInt(sfShape_getTextureRect(toNativeShape))()

    final def textureRect_=(rect: Rect[Int]): Unit =
        Zone { implicit z => sfShape_setTextureRect(toNativeShape, rect.toNativeRect) }

object Shape:
    extension (shape: Ptr[sfShape])
        private[sfml] def close(): Unit =
            dtor(shape)
