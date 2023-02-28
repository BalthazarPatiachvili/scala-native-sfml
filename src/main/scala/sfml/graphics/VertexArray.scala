package sfml
package graphics

import scalanative.unsafe.*

import internal.graphics.VertexArray.*

import stdlib.Vector

object VertexArray:
    extension (vertexArray: Ptr[sfVertexArray])
        private[sfml] def close(): Unit =
            Vector.close(vertexArray.at2)()
