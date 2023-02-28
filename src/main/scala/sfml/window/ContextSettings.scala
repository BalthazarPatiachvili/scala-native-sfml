package sfml
package window

import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichInt

import internal.Type.booleanToSfBool
import internal.window.ContextSettings.*

class ContextSettings(
    val depthBits: Int = 0,
    val stencilBits: Int = 0,
    val antialiasingLevel: Int = 0,
    val majorVersion: Int = 1,
    val minorVersion: Int = 1,
    val attributeFlags: Int = 0,
    val sRgbCapable: Boolean = false
):

    private[sfml] inline def toNativeContextSettings(using Zone): Ptr[sfContextSettings] =
        val contextSettings = alloc[sfContextSettings]()

        contextSettings._1 = depthBits.toUInt
        contextSettings._2 = stencilBits.toUInt
        contextSettings._3 = antialiasingLevel.toUInt
        contextSettings._4 = majorVersion.toUInt
        contextSettings._5 = minorVersion.toUInt
        contextSettings._6 = attributeFlags.toUInt
        contextSettings._7 = sRgbCapable
        contextSettings
