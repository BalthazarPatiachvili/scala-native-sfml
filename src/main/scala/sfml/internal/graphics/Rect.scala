package sfml
package internal
package graphics

import scalanative.unsafe.*

@extern object Rect:
    type sfFloatRect = CStruct4[CFloat, CFloat, CFloat, CFloat]
    type sfIntRect = CStruct4[CInt, CInt, CInt, CInt]

    @name("_Z22glue_returnTypeHandlerRN2sf4RectIfEEPFS1_PvES3_")
    def sfFloatRect_typeHandler(self: Ptr[sfFloatRect], callback: ReturnTypeHandler.callback, args: ReturnTypeHandler.args): Unit = extern

    @name("_Z22glue_returnTypeHandlerRN2sf4RectIiEEPFS1_PvES3_")
    def sfIntRect_typeHandler(self: Ptr[sfIntRect], callback: ReturnTypeHandler.callback, args: ReturnTypeHandler.args): Unit = extern
