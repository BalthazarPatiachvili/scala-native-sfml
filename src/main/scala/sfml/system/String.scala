package sfml
package system

import scalanative.libc.stdlib.free
import scalanative.unsafe.*
import scalanative.unsigned.UnsignedRichInt

import internal.system.String.*

private[sfml] object String:

    extension (string: Ptr[sfString])
        private[sfml] def close(): Unit =
            stdlib.String.close(string)()

    extension (string: java.lang.String)
        private[sfml] def toNativeString(using Zone): Ptr[sfString] =
            val utf32Bytes = string.toCharArray.foldLeft(Array[Char]())((x, y) => x :+ y :+ 0.toChar :+ 0.toChar :+ 0.toChar)

            stdlib.String.toNativeWideStdString(utf32Bytes.mkString)
