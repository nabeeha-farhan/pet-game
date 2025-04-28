package com.cs2212.math;

/**
 * An interface representing geometric shapes with methods to calculate
 * area and perimeter. This interface provides a common protocol for shapes
 * so they can be handled uniformly in mathematical computations.
 * <p>
 * Classes implementing this interface must provide concrete implementations
 * of these methods to represent specific types of shapes.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 */
public interface Shape {

    /**
     * Calculates the area of the shape.
     * <p>This method should return the surface area of the shape.</p>
     *
     * @return the area of the shape, as a {@code double}.
     */
    double calculateArea();

    /**
     * Calculates the perimeter of the shape.
     * <p>This method should return the total length around the shape.</p>
     *
     * @return the perimeter of the shape, as a {@code double}.
     */
    double calculatePerimeter();
}
