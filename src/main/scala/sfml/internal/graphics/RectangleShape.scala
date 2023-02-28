package sfml
package internal
package graphics

import scalanative.unsafe.*

import system.Vector2

@link("sfml-graphics")
@extern private[sfml] object RectangleShape:
    type sfRectangleShape = CStruct2[
        Shape.sfShape,
        CArray[Byte, Nat._8]
    ]

    @name("_ZN2sf14RectangleShapeC2ERKNS_7Vector2IfEE")
    def ctor(self: Ptr[sfRectangleShape], size: Ptr[Vector2.sfVector2f]): Unit = extern

    @name("_ZN2sf14RectangleShapeD2Ev")
    def dtor(self: Ptr[sfRectangleShape]): Unit = extern

    @name("_ZNK2sf14RectangleShape8getPointEm")
    def sfRectangleShape_getPoint(self: Ptr[sfRectangleShape], index: CSize): Vector2.sfVector2f = extern

    @name("_ZNK2sf14RectangleShape13getPointCountEv")
    def sfRectangleShape_getPointCount(self: Ptr[sfRectangleShape]): CSize = extern

    @name("_ZNK2sf14RectangleShape7getSizeEv")
    def sfRectangleShape_getSize(self: Ptr[sfRectangleShape]): Ptr[Vector2.sfVector2f] = extern

    @name("_ZN2sf14RectangleShape7setSizeERKNS_7Vector2IfEE")
    def sfRectangleShape_setSize(self: Ptr[sfRectangleShape], size: Ptr[Vector2.sfVector2f]): Unit = extern
