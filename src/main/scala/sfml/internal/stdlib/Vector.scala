package sfml
package internal
package stdlib

import scalanative.unsafe.*

@extern object Vector:
    type stdVector = CStruct3[Ptr[Byte], CSize, CSize]
