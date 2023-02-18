package sfml

import scalanative.unsafe.*

private[sfml] object ReturnTypeHandler:
    opaque type callback = Ptr[Byte]
    opaque type args = Ptr[Byte]

    inline def apply[T1: Tag, T2: Tag, T3: Tag, T: Tag](
        inline typeHandler: (Ptr[T], callback, args) => Unit
    )(buffer: Ptr[T], arg1: T1, arg2: T2, arg3: T3)(
        callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], T]
    )(using Zone): Unit =
        val args = alloc[CStruct3[T1, T2, T3]]()
        args._1 = arg1
        args._2 = arg2
        args._3 = arg3

        typeHandler(buffer, CFuncPtr.toPtr(callback).asInstanceOf[callback], args.asInstanceOf[args])
