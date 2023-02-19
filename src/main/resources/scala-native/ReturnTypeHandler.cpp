#include <SFML/Graphics.hpp>

void glue_returnTypeHandler(sf::Vector2i & vector, sf::Vector2i (*callback)(void *), void * data)
{
    vector = callback(data);
}

void glue_returnTypeHandler(sf::Vector2f & vector, sf::Vector2f (*callback)(void *), void * data)
{
    vector = callback(data);
}

void glue_returnTypeHandler(sf::IntRect & rect, sf::IntRect (*callback)(void *), void * data)
{
    rect = callback(data);
}

void glue_returnTypeHandler(sf::FloatRect & rect, sf::FloatRect (*callback)(void *), void * data)
{
    rect = callback(data);
}
