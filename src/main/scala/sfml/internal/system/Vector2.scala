package sfml
package internal
package system

import scalanative.unsafe.*

import sfml.ReturnTypeHandler

@extern object Vector2:
    type sfVector2f = CStruct2[CFloat, CFloat]
    type sfVector2i = CStruct2[CInt, CInt]
    type sfVector2u = CStruct2[CUnsignedInt, CUnsignedInt]

    @name("_Z22glue_returnTypeHandlerRN2sf7Vector2IfEEPFS1_PvES3_")
    def sfVector2f_typeHandler(self: Ptr[sfVector2f], callback: ReturnTypeHandler.callback, args: ReturnTypeHandler.args): Unit = extern

    @name("_Z22glue_returnTypeHandlerRN2sf7Vector2IiEEPFS1_PvES3_")
    def sfVector2i_typeHandler(self: Ptr[sfVector2i], callback: ReturnTypeHandler.callback, args: ReturnTypeHandler.args): Unit = extern

    @name("_Z21glue_inlinesfVector2fRN2sf7Vector2IfEE")
    def sfVector2f_inlineHandler(self: Ptr[sfVector2f]): sfVector2f = extern

    @name("_Z21glue_inlinesfVector2iRN2sf7Vector2IiEE")
    def sfVector2i_inlineHandler(self: Ptr[sfVector2i]): sfVector2i = extern
