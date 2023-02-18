package sfml
package internal
package graphics

import scalanative.unsafe.*

@link("sfml-graphics")
@extern private[sfml] object Shape:
    type sfShape = CStruct3[
        Drawable.sfDrawable,
        Transformable.sfTransformable,
        CArray[Byte, Nat.Digit3[Nat._1, Nat._5, Nat._2]]
    ]

    @name("_ZN2sf5ShapeC2Ev")
    def ctor(self: Ptr[sfShape]): Unit = extern

    @name("_ZN2sf5ShapeD2Ev")
    def dtor(self: Ptr[sfShape]): Unit = extern

    @name("_ZN2sf5Shape6updateEv")
    def sfShape_update(self: Ptr[sfShape]): Unit = extern

    /* Getter / Setter */

    @name("_ZNK2sf5Shape12getFillColorEv")
    def sfShape_getFillColor(self: Ptr[sfShape]): Ptr[Color.sfColor] = extern

    @name("_ZN2sf5Shape12setFillColorERKNS_5ColorE")
    def sfShape_setFillColor(self: Ptr[sfShape], color: Ptr[Color.sfColor]): Unit = extern

    @name("_ZNK2sf5Shape15getOutlineColorEv")
    def sfShape_getOutlineColor(self: Ptr[sfShape]): Ptr[Color.sfColor] = extern

    @name("_ZN2sf5Shape15setOutlineColorERKNS_5ColorE")
    def sfShape_setOutlineColor(self: Ptr[sfShape], color: Ptr[Color.sfColor]): Unit = extern

    @name("_ZNK2sf5Shape19getOutlineThicknessEv")
    def sfShape_getOutlineThickness(self: Ptr[sfShape]): Float = extern

    @name("_ZN2sf5Shape19setOutlineThicknessEf")
    def sfShape_setOutlineThickness(self: Ptr[sfShape], thickness: Float): Unit = extern

    @name("_ZN2sf5Shape10setTextureEPKNS_7TextureEb")
    def sfShape_setTexture(self: Ptr[sfShape], texture: Ptr[Texture.sfTexture], resetRect: Type.sfBool): Unit = extern

    @name("_ZNK2sf5Shape14getTextureRectEv")
    def sfShape_getTextureRect(self: Ptr[sfShape]): Ptr[Rect.sfIntRect] = extern

    @name("_ZN2sf5Shape14setTextureRectERKNS_4RectIiEE")
    def sfShape_setTextureRect(self: Ptr[sfShape], rect: Ptr[Rect.sfIntRect]): Unit = extern
