---
title: Shapes
layout: sfml
refurl: https://www.sfml-dev.org/tutorials/2.5/graphics-shape.php
---

## Introduction

SFML provides a set of classes that represent simple shape entities. Each type
of shape is a separate class, but they all derive from the same base class so
that they have access to the same subset of common features. Each class then
adds its own specifics: a radius property for the circle class, a size for the
rectangle class, points for the polygon class, etc.


## Common shape properties

### Transformation (position, rotation, scale)

These properties are common to all the SFML graphical classes, so they are
explained in a separate tutorial: [Transforming
entities](transforming-entities.html).


### Color

One of the basic properties of a shape is its color. You can change with the
[fillColor](sfml.graphics.Shape.fillColor_=) setter.
```scala
//{
import sfml.graphics.{CircleShape, Color}

//}
val shape = CircleShape(50)

shape.fillColor = Color(100, 250, 50)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-color.png"/>


### Outline

Shapes can have an outline. You can set the thickness and color of the outline
with the [outlineThickness](sfml.graphics.Shape.outlineThickness_=) and
[outlineColor](sfml.graphics.Shape.outlineColor_=) setters.
```scala
//{
import sfml.graphics.{CircleShape, Color}

//}
val shape = CircleShape(50)
shape.fillColor = Color(150, 50, 250)

// set a 10-pixel wide orange outline
shape.outlineThickness = 10
shape.outlineColor = Color(250, 150, 100)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-outline.png"/>

By default, the outline is extruded outwards from the shape (e.g. if you have a
circle with a radius of 10 and an outline thickness of 5, the total radius of
the circle will be 15). You can make it extrude towards the center of the shape
instead, by setting a negative thickness.

To disable the outline, set its thickness to 0. If you only want the outline,
you can set the fill color to
[`Color.Transparent()`](sfml.graphics.Color.Transparent).


### Texture

Shapes can also be textured, just like sprites. To specify a part of the texture
to be mapped to the shape, you must use the
[`textureRect`](`sfml.graphics.Shape.textureRect_=`) setter. It takes the
texture rectangle to map to the bounding rectangle of the shape. This method
doesn't offer maximum flexibility, but it is much easier to use than
individually setting the texture coordinates of each point of the shape.
```scala
//{
import sfml.Immutable
import sfml.graphics.{CircleShape, Rect, Texture}

val texture = Texture()
//}
val shape = CircleShape(50)

// map a 100x100 textured rectangle to the shape
shape.texture = Some(Immutable(texture))  // texture is a sfml.graphics.Texture
shape.textureRect = (10, 10, 100, 100)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-texture.png"/>

Note that the outline is not textured.

It is important to know that the texture is modulated (multiplied) with the
shape's fill color. If its fill color is `Color.White()`, the texture will
appear unmodified.

To disable texturing, use `texture = None`.


## Drawing a shape

Drawing a shape is as simple as drawing any other SFML entity:
```scala
//{
import sfml.graphics.{CircleShape, RenderWindow}
import sfml.window.VideoMode

val window = RenderWindow(VideoMode(800, 600), "My window")
val shape = CircleShape()

//}
window.draw(shape)
```


## Built-in shape types

### Rectangles

To draw rectangles, you can use the
[`RectangleShape`](sfml.graphics.RectangleShape) class. It has a single
attribute: The size of the rectangle.
```scala
//{
import sfml.graphics.RectangleShape

//}
// define a 120x50 rectangle
val rectangle = RectangleShape((120, 50))

// change the size to 100x100
rectangle.size = (100, 100)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-rectangle.png"/>


### Circles

Circles are represented by the [`CircleShape`](sfml.graphics.CircleShape) class.
It has two attributes: The radius and the number of sides. The number of sides
is an optional attribute, it allows you to adjust the "quality" of the circle:
Circles have to be approximated by polygons with many sides (the graphics card
is unable to draw a perfect circle directly), and this attribute defines how
many sides your circle approximation will have. If you draw small circles,
you'll probably only need a few sides. If you draw big circles, or zoom on
regular circles, you'll most likely need more sides.
```scala
//{
import sfml.graphics.CircleShape

//}
// define a circle with radius = 200
val circle = CircleShape(200)

// change the radius to 40
circle.radius = 40

// change the number of sides (points) to 100
circle.pointCount = 100
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-circle.png"/>


### Regular polygons

There's no dedicated class for regular polygons, in fact you can represent a
regular polygon with any number of sides using the
[`CircleShape`](sfml.graphics.CircleShape) class: Since circles are approximated
by polygons with many sides, you just have to play with the number of sides to
get the desired polygons. A `CircleShape` with 3 points is a triangle, with 4
points it's a square, etc.
```scala
//{
import sfml.graphics.CircleShape

//}
// define a triangle
val triangle = CircleShape(80, 3)

// define a square
val square = CircleShape(80, 4)

// define an octagon
val octagon = CircleShape(80, 8)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-regular.png"/>


<!-- TODO: Convex shapes section -->

### Lines

There's no shape class for lines. The reason is simple: If your line has a
thickness, it is a rectangle. If it doesn't, it can be drawn with a line
primitive.

Line with thickness:
```
//{
import sfml.graphics.RectangleShape

//}
val line = RectangleShape((150, 5))

line.rotate(45)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-line-rectangle.png"/>


<!-- TODO: Line without thickness -->

To learn more about vertices and primitives, you can read the tutorial on vertex
arrays (feature not ported yet).


## Custom shape types

You can extend the set of shape classes with your own shape types. To do so, you
must derive from [`Shape`](sfml.graphics.Shape) and override two functions:
 - `getPointCount`: return the number of points in the shape
 - `getPoint`: return a point of the shape

You must also call the `update()` protected function whenever any point in your
shape changes, so that the base class is informed and can update its internal
geometry.

Here is a complete example of a custom shape class: EllipseShape.
```scala
//{
import sfml.graphics.Shape
import sfml.system.Vector2

//}
class EllipseShape(private var ellipseRadius: Vector2[Float]) extends Shape():

    def radius: Vector2[Float] =
        ellipseRadius

    def radius_=(radius: Vector2[Float]): Unit =
        ellipseRadius = radius
        update()

    override def pointCount: Long =
        30 // fixed, but could be an attribute of the class if needed

    override def point(index: Long): Vector2[Float] =
        val angle = index * 2 * math.Pi / pointCount - math.Pi / 2
        val x = radius.x * math.cos(angle).toFloat
        val y = radius.y * math.sin(angle).toFloat

        Vector2(radius.x + x, radius.y + y)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-ellipse.png"/>


## Antialiased shapes

There's no option to anti-alias a single shape. To get anti-aliased shapes (i.e.
shapes with smoothed edges), you have to enable anti-aliasing globally when you
create the window, with the corresponding attribute of the
[`ContextSettings`](sfml.window.ContextSettings) structure. 
```scala
//{
import sfml.graphics.RenderWindow
import sfml.window.{ContextSettings, Style, VideoMode}

//}
val settings = ContextSettings(antialiasingLevel = 8)

val window = RenderWindow(VideoMode(800, 600), "SFML shapes", Style.Default, settings)
```

<img src="https://www.sfml-dev.org/tutorials/2.5/images/graphics-shape-antialiasing.png"/>

Remember that anti-aliasing availability depends on the graphics card: It might
not support it, or have it forced to disabled in the driver settings. 
