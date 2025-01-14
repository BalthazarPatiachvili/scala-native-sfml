package sfml
package graphics

import scalanative.unsafe.*

import internal.Type.{booleanToSfBool, sfBoolToBoolean}
import internal.graphics.Texture.*

import graphics.Rect
import stdlib.String.toNativeStdString

class Texture private[sfml] (private val texture: ResourceBuffer[sfTexture]) extends Resource:

    private[sfml] inline def toNativeTexture: Ptr[sfTexture] = texture.ptr

    override def close(): Unit =
        Texture.close(toNativeTexture)()

    def this() =
        this(ResourceBuffer { (r: Ptr[sfTexture]) => ctor(r) })

    final def loadFromFile(filename: String, area: Rect[Int] = Rect()): Boolean =
        Zone { implicit z => sfTexture_loadFromFile(toNativeTexture, filename.toNativeStdString, area.toNativeRect) }

    final def smooth: Boolean =
        sfTexture_isSmooth(toNativeTexture)

    final def smooth_=(smooth: Boolean) =
        sfTexture_setSmooth(toNativeTexture, smooth)

    final def repeated: Boolean =
        sfTexture_isRepeated(toNativeTexture)

    final def repeated_=(repeated: Boolean) =
        sfTexture_setRepeated(toNativeTexture, repeated)

object Texture:

    extension (texture: Immutable[Texture])
        private[sfml] inline def toNativeTexture: Ptr[sfTexture] = texture.get.toNativeTexture

        def smooth: Boolean = texture.get.smooth

        def repeated: Boolean = texture.get.repeated

    extension (texture: Ptr[sfTexture])
        private[sfml] def close(): Unit =
            dtor(texture)

        private[sfml] def toTexture: Texture =
            Texture(ResourceBuffer(texture))
