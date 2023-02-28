import scala.util.Using

import sfml.graphics.*
import sfml.window.*

@main def start = main

@SuppressWarnings(Array("org.wartremover.warts.All"))
def main =
    Using.Manager { use =>
        val window = use(RenderWindow(VideoMode(1024, 768), "Hello world"))
        window.framerateLimit = 30

        val texture = use(Texture())
        texture.loadFromFile("cat.png")

        val sprite = Sprite(texture)

        while window.isOpen() do
            for event <- window.pollEvent() do
                event match {
                    case _: Event.Closed => window.close()
                    case _               => ()
                }

            window.clear(Color.Black())

            window.draw(sprite)

            window.mouseCursorVisible = false

            window.display()
    }
