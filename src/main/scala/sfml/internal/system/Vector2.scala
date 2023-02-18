package sfml
package internal
package system

import scalanative.unsafe.*

import sfml.ReturnTypeHandler

@extern object Vector2:
    type sfVector2f = CStruct2[CFloat, CFloat]
    type sfVector2i = CStruct2[CInt, CInt]
    type sfVector2u = CStruct2[CUnsignedInt, CUnsignedInt]

    @name("sfVector2f_returnTypeHandler")
    def sfVector2f_typeHandler(self: Ptr[sfVector2f], callback: ReturnTypeHandler.callback, args: ReturnTypeHandler.args): Unit = extern
