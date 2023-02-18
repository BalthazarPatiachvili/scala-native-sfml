#include <SFML/Graphics.hpp>

extern "C" void sfVector2f_returnTypeHandler(
    sf::Vector2f & vector,
    sf::Vector2f (*callback)(void *),
    void * data)
{
    vector = callback(data);
}
