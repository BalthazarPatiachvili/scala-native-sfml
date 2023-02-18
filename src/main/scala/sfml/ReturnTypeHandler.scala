package sfml

import scalanative.unsafe.*

private[sfml] object ReturnTypeHandler:
    opaque type callback = Ptr[Byte]
    opaque type args = Ptr[Byte]

    inline def apply[T1: Tag, T2: Tag, T3: Tag, R: Tag](
        inline typeHandler: (Ptr[R], callback, args) => Unit,
        inline callback: CFuncPtr1[Ptr[CStruct3[T1, T2, T3]], R]
    )(inline arg1: T1, inline arg2: T2, inline arg3: T3)(using Zone): Ptr[R] =
        val buffer = alloc[R]()

        val args = alloc[CStruct3[T1, T2, T3]]()
        args._1 = arg1
        args._2 = arg2
        args._3 = arg3

        typeHandler(buffer, CFuncPtr.toPtr(callback).asInstanceOf[callback], args.asInstanceOf[args])
        buffer
