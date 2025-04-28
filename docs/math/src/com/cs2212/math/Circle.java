package com.cs2212.math;

import static java.lang.Math.PI;

/**
 * Represents a circle which is a round shape with all points equidistant from the center.
 * <p>
 * This class implements the {@link Shape} interface and utilizes {@link Math#PI} for calculations.
 * Use {@code Circle} objects to perform standard geometrical operations.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 * @see Shape
 */
public class Circle implements Shape {
    private double radius;

    /**
     * Constructs a {@code Circle} with the specified radius.
     * <p>Throws {@code IllegalArgumentException} if the radius is not valid.</p>
     *
     * @param radius the radius of the circle; must be positive
     * @throws IllegalArgumentException if the radius is less than or equal to zero
     */
    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive and nonzero");
        }
        this.radius = radius;
    }

    /**
     * Returns the radius of the circle.
     *
     * @return the radius, as a {@code double}.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public double calculateArea() {
        return PI * radius * radius;  //Area = π * r^2
    }

    @Override
    public double calculatePerimeter() {
        return 2 * PI * radius;  //Perimeter = 2π * r
    }
}
