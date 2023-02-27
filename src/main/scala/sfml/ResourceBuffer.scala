package sfml

import scalanative.libc.string.memcpy
import scalanative.runtime.*
import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichInt

private[sfml] class ResourceBuffer[T: Tag] protected (buffer: Either[Ptr[T], ByteArray]):

    def this(ctor: Ptr[T] => Unit) =
        this(Right[Ptr[T], ByteArray](ByteArray.alloc(sizeof[T].toInt)))
        ctor(ptr)

    def this(inner: Ptr[T]) =
        this(Left[Ptr[T], ByteArray](inner))

    inline def ptr: Ptr[T] =
        buffer match
            case Left(ptr)    => ptr
            case Right(array) => fromRawPtr(array.atRaw(0))

private[sfml] object ResourceBuffer:

    def shallow_copy[T: Tag](ptr: Ptr[T]): ResourceBuffer[T] =
        ResourceBuffer[T]((r: Ptr[T]) => {
            memcpy(r.asInstanceOf[Ptr[Byte]], ptr.asInstanceOf[Ptr[Byte]], sizeof[T])
            ()
        })

private[sfml] class AbstractResourceBuffer[T: Tag] protected (buffer: Either[Ptr[T], ByteArray]):

    @SuppressWarnings(scala.Array("org.wartremover.warts.Null"))
    def this(vtable: LongArray)(ctor: Ptr[T] => Unit) =
        this(Right[Ptr[T], ByteArray](ByteArray.alloc(sizeof[CStruct2[T, Object]].toInt)))
        ctor(ptr(null))
        !ptr(null).asInstanceOf[Ptr[Ptr[T]]] = fromRawPtr(vtable.atRaw(0))

    def this(inner: Ptr[T]) =
        this(Left[Ptr[T], ByteArray](inner))

    inline def ptr(self: Object): Ptr[T] =
        buffer match
            case Left(ptr) => ptr
            case Right(array) =>
                val rawPtr = fromRawPtr(array.atRaw(0))
                rawPtr.asInstanceOf[Ptr[CStruct2[T, Object]]]._2 = self
                rawPtr.asInstanceOf[Ptr[T]]

private[sfml] object AbstractResourceBuffer:

    type VTable = Ptr[Ptr[Byte]]

    def createVTable(fptrs: Ptr[Byte]*): LongArray =
        val array = LongArray.alloc(fptrs.length)

        for i <- 0 until fptrs.length do array(i) = fptrs(i).toLong

        array
