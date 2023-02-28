package sfml
package internal
package graphics

import scalanative.unsafe.*

import system.Vector2

@link("sfml-graphics")
@extern object Transform:
    type sfTransform = CArray[CFloat, Nat.Digit2[Nat._1, Nat._6]]

    @name("_ZN2sf9TransformC2Ev")
    def ctor(self: Ptr[sfTransform]): Unit = extern

    @name("_ZN2sf9TransformC1Efffffffff")
    def ctor(self: Ptr[sfTransform], a00: CFloat, a01: CFloat, a02: CFloat, a10: CFloat, a11: CFloat, a12: CFloat, a20: CFloat, a21: CFloat, a22: CFloat): Unit = extern

    @name("_ZN2sf9Transform7combineERKS0_")
    def sfTransform_combine(self: Ptr[sfTransform], rhs: Ptr[sfTransform]): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform6rotateEf")
    def sfTransform_rotate(self: Ptr[sfTransform], angle: CFloat): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform6rotateEfff")
    def sfTransform_rotate(self: Ptr[sfTransform], angle: CFloat, centerX: CFloat, centerY: CFloat): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform6rotateEfRKNS_7Vector2IfEE")
    def sfTransform_rotate(self: Ptr[sfTransform], angle: CFloat, center: Ptr[Vector2.sfVector2f]): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform5scaleEff")
    def sfTransform_scale(self: Ptr[sfTransform], scaleX: CFloat, scaleY: CFloat): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform5scaleEffff")
    def sfTransform_scale(self: Ptr[sfTransform], scaleX: CFloat, scaleY: CFloat, centerX: CFloat, centerY: CFloat): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform5scaleERKNS_7Vector2IfEE")
    def sfTransform_scale(self: Ptr[sfTransform], factors: Ptr[Vector2.sfVector2f]): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform5scaleERKNS_7Vector2IfEES4_")
    def sfTransform_scale(self: Ptr[sfTransform], factors: Ptr[Vector2.sfVector2f], center: Ptr[Vector2.sfVector2f]): Ptr[sfTransform] = extern

    @name("_ZNK2sf9Transform14transformPointEff")
    def sfTransform_transformPoint(self: Ptr[sfTransform], x: CFloat, y: CFloat): Vector2.sfVector2f = extern

    @name("_ZNK2sf9Transform14transformPointERKNS_7Vector2IfEE")
    def sfTransform_transformPoint(self: Ptr[sfTransform], point: Ptr[Vector2.sfVector2f]): Vector2.sfVector2f = extern

    @name("_ZNK2sf9Transform13transformRectERKNS_4RectIfEE")
    def sfTransform_transformRect(self: Ptr[sfTransform], rectangle: Ptr[Rect.sfFloatRect]): Rect.sfFloatRect = extern

    @name("_ZN2sf9Transform9translateEff")
    def sfTransform_translate(self: Ptr[sfTransform], x: CFloat, y: CFloat): Ptr[sfTransform] = extern

    @name("_ZN2sf9Transform9translateERKNS_7Vector2IfEE")
    def sfTransform_translate(self: Ptr[sfTransform], offset: Ptr[Vector2.sfVector2f]): Ptr[sfTransform] = extern

    /* Operators */

    @name("_ZN2sfeqERKNS_9TransformES2_")
    def sfTransform_eq_sfTransform(self: Ptr[sfTransform], rhs: Ptr[sfTransform]): Type.sfBool = extern

    @name("_ZN2sfmlERKNS_9TransformERKNS_7Vector2IfEE")
    def sfTransform_ml_sfVector2f(self: Ptr[sfTransform], rhs: Ptr[Vector2.sfVector2f]): Vector2.sfVector2f = extern
