package sfml
package graphics

import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichByte

import internal.graphics.Color.*

final case class Color(val r: Byte, val g: Byte, val b: Byte, val a: Byte = 255.toByte):

    private[sfml] inline def toNativeColor(using Zone): Ptr[sfColor] =
        val color = alloc[sfColor]()

        color._1 = r.toUByte
        color._2 = g.toUByte
        color._3 = b.toUByte
        color._4 = a.toUByte
        color

object Color:
    extension (color: Ptr[sfColor])
        private[sfml] def toColor(): Color =
            Color(color._1.toByte, color._2.toByte, color._3.toByte, color._4.toByte)

    def apply(): Color = Color.Black()

    def apply(r: Int, g: Int, b: Int): Color =
        Color(r.toByte, g.toByte, b.toByte, 255.toByte)

    def apply(r: Int, g: Int, b: Int, a: Int): Color =
        Color(r.toByte, g.toByte, b.toByte, a.toByte)

    def Black(): Color = Color(0, 0, 0)
    def White(): Color = Color(255, 255, 255)
    def Red(): Color = Color(255, 0, 0)
    def Green(): Color = Color(0, 255, 0)
    def Blue(): Color = Color(0, 0, 255)
    def Yellow(): Color = Color(255, 255, 0)
    def Magenta(): Color = Color(255, 0, 255)
    def Cyan(): Color = Color(0, 255, 255)
    def Transparent(): Color = Color(0, 0, 0, 0)
