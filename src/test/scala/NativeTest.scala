import scalanative.unsafe.*

import org.junit.Assert.assertTrue
import org.junit.After

@extern object LSAN:
    @name("__lsan_do_recoverable_leak_check")
    def leak_check(): CInt = extern

trait NativeTest:
    @After
    def __teardown(): Unit =
        assertTrue("memory leaked", LSAN.leak_check() == 0)
