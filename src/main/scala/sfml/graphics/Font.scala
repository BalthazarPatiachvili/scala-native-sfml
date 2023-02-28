package sfml
package graphics

import scalanative.unsafe.*

import internal.Type.{booleanToSfBool, sfBoolToBoolean}
import internal.graphics.Font.*

import graphics.Rect
import stdlib.String.toNativeStdString

class Font private[sfml] (private val font: ResourceBuffer[sfFont]) extends Resource:

    private[sfml] inline def toNativeFont: Ptr[sfFont] = font.ptr

    override def close(): Unit =
        Font.close(toNativeFont)()

    def this() =
        this(ResourceBuffer { (r: Ptr[sfFont]) => ctor(r) })

    final def loadFromFile(filename: String): Boolean =
        Zone { implicit z => sfFont_loadFromFile(toNativeFont, filename.toNativeStdString) }

object Font:
    extension (font: Ptr[sfFont])
        private[sfml] def close(): Unit =
            dtor(font)
