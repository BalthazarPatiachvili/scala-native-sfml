package sfml
package graphics

import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichInt

import internal.Type.split
import internal.graphics.RenderWindow.*
import internal.window.Window.sfWindow

import system.String.toNativeString
import system.Vector2
import window.{ContextSettings, Style, VideoMode, Window}

class RenderWindow private[sfml] (private val renderWindow: ResourceBuffer[sfRenderWindow])
    extends Window(ResourceBuffer(renderWindow.ptr.at1))
    with RenderTarget(ResourceBuffer(renderWindow.ptr.at2)):

    private[sfml] inline def toNativeRenderWindow: Ptr[sfRenderWindow] = renderWindow.ptr

    def this(mode: VideoMode, title: String, style: Style, settings: ContextSettings) =
        this(ResourceBuffer { (r: Ptr[sfRenderWindow]) =>
            Zone { implicit z =>
                val modeSplit = split(mode.toNativeVideoMode)

                ctor(r, modeSplit(0), modeSplit(1), toNativeString(title), style.value.toUInt, settings.toNativeContextSettings)
            }
        })

    def this(mode: VideoMode, title: String, style: Style) =
        this(mode, title, style, ContextSettings())

    def this(mode: VideoMode, title: String) =
        this(mode, title, Style.Default)

    override def size: Vector2[Int] =
        Zone { implicit z =>
            Vector2.toVector2Int(toNativeRenderWindow) { (data: Ptr[CStruct1[Ptr[sfRenderWindow]]]) =>
                sfRenderWindow_getSize(data._1)
            }
        }
