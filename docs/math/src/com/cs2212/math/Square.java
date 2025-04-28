package com.cs2212.math;

/**
 * Represents a square, which is a special type of rectangle where all sides are equal.
 * <p>
 * This class extends {@link Rectangle} and ensures that any change to one dimension
 * (width or height) will update both to keep the square's equal sides property intact.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 * @see Rectangle
 */
public class Square extends Rectangle {

    /**
     * Constructs a {@code Square} with equal side lengths.
     *
     * @param side the length of each side of the square; must be positive and nonzero
     * @throws IllegalArgumentException if the side length is less than or equal to zero
     */
    public Square(double side) {
        super(side, side);
    }

    @Override
    public void setWidth(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be positive and nonzero!");
        }
        super.setWidth(side);
        super.setHeight(side);
    }

    @Override
    public void setHeight(double side) {
        setWidth(side);  //Delegate to setWidth for consistency
    }
}
