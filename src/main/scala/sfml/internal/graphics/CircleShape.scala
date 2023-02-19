package sfml
package internal
package graphics

import scalanative.unsafe.*

import system.Vector2

@link("sfml-graphics")
@extern private[sfml] object CircleShape:
    type sfCircleShape = CStruct2[
        Shape.sfShape,
        CArray[Byte, Nat.Digit2[Nat._1, Nat._6]]
    ]

    @name("_ZN2sf11CircleShapeC2Efm")
    def ctor(self: Ptr[sfCircleShape], radius: CFloat, pointCount: CSize): Unit = extern

    @name("_ZN2sf11CircleShapeD2Ev")
    def dtor(self: Ptr[sfCircleShape]): Unit = extern

    @name("_ZNK2sf11CircleShape8getPointEm")
    def sfCircleShape_getPoint(self: Ptr[sfCircleShape], index: CSize): Vector2.sfVector2f = extern

    @name("_ZNK2sf11CircleShape13getPointCountEv")
    def sfCircleShape_getPointCount(self: Ptr[sfCircleShape]): CSize = extern
