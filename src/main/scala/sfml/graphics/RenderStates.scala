package sfml
package graphics

import scalanative.unsafe.*

import internal.graphics.RenderStates.*

final case class RenderStates(val blendMode: BlendMode, val transform: Transform):

    private[sfml] inline def toNativeRenderStates(using Zone): Ptr[sfRenderStates] =
        val renderStates = alloc[sfRenderStates]()

        renderStates._1 = blendMode.toNativeBlendMode
        renderStates._2 = transform.toNativeTransform
        renderStates

object RenderStates:
    def apply(): RenderStates =
        RenderStates(BlendMode.Alpha(), Transform())

    def apply(blendMode: BlendMode): RenderStates =
        RenderStates(blendMode, Transform())

    def apply(transform: Transform): RenderStates =
        RenderStates(BlendMode.Alpha(), transform)
