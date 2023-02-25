package sfml
package stdlib

import scalanative.libc.stdlib.free
import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichInt

import internal.stdlib.Vector.*

private[sfml] object Vector:

    extension (vector: Ptr[stdVector])
        private[sfml] def close(): Unit =
            if vector._1 != 0.toULong then free(vector._1)
