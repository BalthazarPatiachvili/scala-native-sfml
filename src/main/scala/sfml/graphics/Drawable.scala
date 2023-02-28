package sfml
package graphics

import scalanative.unsafe.*

trait Drawable:
    def draw(target: RenderTarget, states: RenderStates): Unit

object Drawable:
    extension (drawable: Immutable[Drawable])
        def draw(target: RenderTarget, states: RenderStates): Unit =
            drawable.get.draw(target, states)
