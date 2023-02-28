package sfml

import scalanative.unsafe.*

@extern private[sfml] object Resolver:
    @name("_ZN8Resolver19findFunctionPointerEPKc")
    def findFunctionPointer(name: CString): Ptr[Byte] = extern
