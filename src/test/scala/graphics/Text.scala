import org.junit.Assert.*
import org.junit.{After, AfterClass, Before, Test}

import scala.util.Using

import sfml.graphics.*
import sfml.window.*

class TextTest:
    @Test def getGlobalBounds(): Unit =
        Using(Font()) { font =>
            font.loadFromFile("src/test/resources/tuffy.ttf")

            Using(Text("Hello, world!", font, 50)) { text =>
                assertEquals(Rect[Float](3, 13, 244, 43), text.globalBounds)
            }
        }

class TextGraphicalTest extends GraphicalTest:
    @Test def graphicalTest(): Unit =
        snTestScreen.testName = "Text"

        Using.Manager { use =>
            // Setup
            val window = use(RenderWindow(VideoMode(1024, 768), "Test"))

            val font = use(Font())
            font.loadFromFile("src/test/resources/tuffy.ttf")

            val text = use(Text("Hello World", font, 50))

            window.isOpen()

            // Control test
            window.clear()
            window.draw(text)
            window.display()
            snTestScreen.takeScreenshot()

            // sf::Text::setPosition
            text.position = (100, 100)

            window.clear()
            window.draw(text)
            window.display()
            snTestScreen.takeScreenshot()

            // Teardown
            window.close()
        }
