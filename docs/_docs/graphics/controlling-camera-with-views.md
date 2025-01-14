---
title: Controlling the 2D camera with views
layout: sfml
refurl: https://www.sfml-dev.org/tutorials/2.5/graphics-view.php
---

## What is a view?

In games, it is not uncommon to have levels which are much bigger than the
window itself. You only see is a small part of them. This is typically the case
in RPGs, platform games, and many other genres. What developers might tend to
forget is that they define entities in a 2D world, not directly in the window.
The window is just a view, it shows a specific area of the whole world. It is
perfectly fine to draw several views of the same world in parallel, or draw the
world to a texture rather than to a window. The world itself remains unchanged,
what changes is just the way it is seen.

Since what is seen in the window is just a small part of the entire 2D world,
you need a way to specify which part of the world is shown in the window.
Additionally, you may also want to define where/how this area will be shown
within the window. These are the two main features of SFML views.

To summarize, views are what you need if you want to scroll, rotate or zoom your
world. They are also the key to creating split screens and mini-maps.


## Defining what the view views

The class which encapsulates views in SFML is [View](sfml.graphics.View). It can
be constructed directly with a definition of the area to view:
```scala
//{
import sfml.graphics.{Rect, View}
import sfml.system.Vector2

//}
// create a view with the rectangular area of the 2D world to show
val view1 = View((200, 200, 300, 200))

// create a view with its center and size
val view2 = View((350, 300), (300, 200))
```

These two definitions are equivalent: Both views will show the same area of the
2D world, a 300x200 rectangle centered on the point (350, 300).

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-initial.png" />

If you don't want to define the view upon construction or want to modify it
later, you can use the equivalent setters:
```scala
//{
import sfml.graphics.{Rect, View}
import sfml.system.Vector2

//}
val view1 = View()
view1.reset((200, 200, 300, 200))

val view2 = View()
view2.center = (350, 300)
view2.size = (200, 200)
```

Once your view is defined, you can transform it to make it show a
translated/rotated/scaled version of your 2D world.


## Moving (scrolling) the view

Unlike drawable entities, such as sprites or shapes whose positions are defined
by their top-left corner (and can be changed to any other point), views are
always manipulated by their center -- this is simply more convenient. That's why
the setter to change the position of a view is named
[`center`](sfml.graphics.View.center_=), and not `position`.

```scala
//{
import sfml.graphics.View
import sfml.system.Vector2

val view = View()

//}
// move the view at point (200, 200)
view.center = (200, 200)

// move the view by an offset of (100, 100) (so its final position is (300, 300))
view.move(100, 100)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-translated.png"/>


## Rotating the view

To rotate a view, use the [`rotation`](sfml.graphics.View.rotation_=) setter.
```scala
//{
import sfml.graphics.View

val view = View()

//}
// rotate the view at 20 degrees
view.rotation = 20

// rotate the view by 5 degrees relatively to its current orientation (so its final orientation is 25 degrees)
view.rotate(5)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-rotated.png"/>


## Zooming (scaling) the view

Zooming in (or out) a view is done through to resizing it, so the setter to use
is [`size`](sfml.graphics.View.size_=).
```scala
//{
import sfml.graphics.View
import sfml.system.Vector2

val view = View()

//}
// resize the view to show a 1200x800 area (we see a bigger area, so this is a zoom out)
view.size = (1200, 800)

// zoom the view relatively to its current size (apply a factor 0.5, so its final size is 600x400)
view.zoom(0.5)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-scaled.png"/>


## Defining how the view is viewed

Now that you've defined which part of the 2D world is seen in the window, let's
define where it is shown. By default, the viewed contents occupy the full
window. If the view has the same size as the window, everything is rendered 1:1.
If the view is smaller or larger than the window, everything is scaled to fit in
the window.

This default behavior is suitable for most situations, but it might need to be
changed sometimes. For example, to split the screen in a multiplayer game, you
may want to use two views which each only occupy half of the window. You can
also implement a minimap by drawing your entire world to a view which is
rendered in a small area in a corner of the window. The area in which the
contents of the view is shown is called the viewport.

To set the viewport of a view, you can use the
[`viewport`](sfml.graphics.View.viewport_=) setter.
```scala
//{
import sfml.graphics.{Rect, View}

val view = View()

//}
// define a centered viewport, with half the size of the window
view.viewport = (0.25, 0.25, 0.5, 0.5)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-viewport.png"/>

You might have noticed something very important: The viewport is not defined in
pixels, but instead as a ratio of the window size. This is more convenient: It
allows you to not have to track resize events in order to update the size of the
viewport every time the size of the window changes. It is also more intuitive:
You would probably define your viewport as a fraction of the entire window area
anyway, not as a fixed-size rectangle.

Using a viewport, it is straightforward to split the screen for multiplayer games:
```scala
//{
import sfml.graphics.{Rect, View}

val player1View = View()
val player2View = View()

//}
// player 1 (left side of the screen)
player1View.viewport = (0.0, 0.0, 0.5, 1.0)

// player 2 (right side of the screen)
player2View.viewport = (0.5, 0.0, 0.5, 1.0)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-split-screen.png"/>

... or a mini-map:
```scala
//{
import sfml.graphics.{Rect, View}

val gameView = View()
val minimapView = View()

//}
// the game view (full window)
gameView.viewport = (0, 0, 1, 1)

// mini-map (upper-right corner)
minimapView.viewport = (0.75, 0.0, 0.25, 0.25)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-view-minimap.png"/>


## Using a view

To draw something using a view, you must draw it after updating `view` setter of
the target to which you are drawing
([`RenderWindow`](sfml.graphics.RenderWindow.view_=)).
<!-- TODO: Add_sf::RenderTexture -->
```scala
//{
import sfml.Immutable
import sfml.graphics.{Rect, RenderWindow, Sprite, View}
import sfml.window.VideoMode

val window = RenderWindow(VideoMode(800, 600), "My window")
val some_sprite = Sprite()

//}
// let's define a view
val view = View((0, 0, 1000, 600))

// activate it
window.view = Immutable(view)

// draw something to that view
window.draw(some_sprite);

// want to do visibility checks? retrieve the view
val currentView = window.view
```

The view remains active until you set another one. This means that there is
always a view which defines what appears in the target, and where it is drawn.
If you don't explicitly set any view, the render-target uses its own default
view, which matches its size 1:1. You can get the default view of a
render-target with the [`defaultView`](sfml.graphics.RenderTarget.defaultView)
getter. This can be useful if you want to define your own view based on it, or
restore it to draw fixed entities (like a GUI) on top of your scene.
```scala
//{
import sfml.Immutable
import sfml.graphics.{Rect, RenderWindow, Sprite, View}
import sfml.window.VideoMode

val window = RenderWindow(VideoMode(800, 600), "My window")

//}
// create a view half the size of the default view
val view = window.defaultView.clone()
view.zoom(0.5)
window.view = Immutable(view)

// restore the default view
window.view = window.defaultView
```

<div class="warning">
When you use <code>view</code> setter, the render-target makes a copy of the
view, and doesn't store a pointer to the one that is passed. This means that
whenever you update your view, you need to use <code>view</code> setter again to
apply the modifications. Don't be afraid to copy views or create them on the
fly, they aren't expensive objects (they just hold a few floats).
</div>


## Showing more when the window is resized

Since the default view never changes after the window is created, the viewed
contents are always the same. So when the window is resized, everything is
squeezed/stretched to the new size.

If, instead of this default behavior, you'd like to show more/less stuff
depending on the new size of the window, all you have to do is update the size
of the view with the size of the window.
```scala
//{
import sfml.Immutable
import sfml.graphics.{Rect, RenderWindow, View}
import sfml.window.{Event, VideoMode}

val window = RenderWindow(VideoMode(800, 600), "My window")

//}
// the event loop
for event <- window.pollEvent() do
    event match
        case Event.Resized(width, height) =>
            window.view = Immutable(View((0, 0, width, height)))
        case _ => ()
```


## Coordinates conversions

When you use a custom view, or when you resize the window without using the code
above, pixels displayed on the target no longer match units in the 2D world. For
example, clicking on pixel (10, 50) may hit the point (26.5, -84) of your world.
You end up having to use a conversion function to map your pixel coordinates to
world coordinates:
[`mapPixelToCoords`](sfml.graphics.RenderTarget.mapPixelToCoords).
```scala
//{
import sfml.graphics.RenderWindow
import sfml.window.{Mouse, VideoMode}

val window = RenderWindow(VideoMode(800, 600), "My window")

//}
// get the current mouse position in the window
val pixelPos = Mouse.position(window)

// convert it to world coordinates
val worldPos = window.mapPixelToCoords(pixelPos)
```

By default, [`mapPixelToCoords`](sfml.graphics.RenderTarget.mapPixelToCoords)
uses the current view. If you want to convert the coordinates using view which
is not the active one, you can pass it as an additional argument to the
function.

<!-- TODO: mapCoordsToPixel -->
