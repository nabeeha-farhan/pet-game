package com.cs2212.math;

/**
 * Represents a rectangle with specific width and height.
 * Implements the {@link Shape} interface to calculate area and perimeter.
 * <p>
 * This class provides methods to get and set the dimensions of the rectangle,
 * ensuring that they are positive and nonzero.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 * @see Shape
 */
public class Rectangle implements Shape {
    private double width;
    private double height;

    /**
     * Constructs a {@code Rectangle} with specified width and height.
     * Ensures dimensions are positive and throws an exception if they are not.
     *
     * @param width  the width of the rectangle; must be positive and nonzero
     * @param height the height of the rectangle; must be positive and nonzero
     * @throws IllegalArgumentException if the width or height is less than or equal to zero
     */
    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive and nonzero!");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive and nonzero!");
        }
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive and nonzero!");
        }
        this.width = width;
    }
}
