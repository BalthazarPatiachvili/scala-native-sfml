package sfml
package internal
package graphics

import scalanative.unsafe.*

import stdlib.Vector

@link("sfml-graphics")
@extern private[sfml] object VertexArray:
    type sfVertexArray = CStruct3[
        CArray[Byte, Nat._8],
        Vector.stdVector,
        CArray[Byte, Nat._8]
    ]
