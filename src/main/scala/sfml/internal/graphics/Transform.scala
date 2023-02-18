package sfml
package internal
package graphics

import scalanative.unsafe.*

import system.Vector2

@link("sfml-graphics")
@extern object Transform:
    type sfTransform = CArray[CFloat, Nat.Digit2[Nat._1, Nat._6]]

    @name("_ZNK2sf9Transform14transformPointEff")
    def sfTransform_transformPoint(self: sfTransform, x: CFloat, y: CFloat): Vector2.sfVector2f = extern
