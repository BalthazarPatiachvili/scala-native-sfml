import scala.io.Source

import org.junit.Assert.*
import org.junit.{After, Assume, Before}

trait GraphicalTest extends NativeTest:
    val snTestScreen = TestScreen()

    private def cleanup() =
        execute(Array("rm", "-rf", s"${snTestScreen.folderPath}/cxx", s"${snTestScreen.folderPath}/scala"))

    @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
    private def execute(command: Array[String]): Unit =
        val process = Runtime.getRuntime.exec(command)
        val exitCode = process.waitFor()

        assertEquals(Source.fromInputStream(process.getErrorStream).mkString(""), 0, process.waitFor())

    @Before
    @SuppressWarnings(Array("org.wartremover.warts.Return"))
    def __init(): Unit =
        // NOTE: Currently, cannot pass arguments to JUnit -> Use environment variable instead
        // Source: junit-runtime/src/main/scala/scala/scalanative/junit/JUnitFramework.scala
        sys.env.get("SNSFML_SCREENSHOT_FOLDER_PATH") match
            case Some(path) => snTestScreen.folderPath = path
            case None       => return Assume.assumeTrue("graphical test skipped", false)

        cleanup()

        execute(Array("mkdir", "-p", s"${snTestScreen.folderPath}/cxx", s"${snTestScreen.folderPath}/scala"))

    @After
    @SuppressWarnings(Array("org.wartremover.warts.Return"))
    override def __teardown(): Unit =
        super.__teardown()

        if sys.env.get("SNSFML_SCREENSHOT_FOLDER_PATH").isEmpty then return ()

        // NOTE: Currently, cannot use JUnit's rules to retrieve the test name -> Use of `testName` variable
        assertTrue("test name not set", snTestScreen.testName != "")

        execute(Array("./target/cxx/scala-native-sfml-test-out", s"${snTestScreen.folderPath}/cxx", snTestScreen.testName))

        execute(Array("diff", "-aB", s"${snTestScreen.folderPath}/cxx", s"${snTestScreen.folderPath}/scala"))

        cleanup()
