package com.cs2212.math;

/**
 * Provides utility methods for basic mathematical operations such as addition,
 * subtraction, multiplication, and division.
 * <p>
 * Designed for integer-based calculations, this class offers a clear demonstration
 * of fundamental arithmetic operations for educational purposes.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 */
public class Math {

    public Math() {

    }

    public int add(int x, int y) {
        return x + y;
    }

    public int subtract(int x, int y) {
        return x - y;
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    /**
     * Divides one integer by another and returns a double.
     * Throws an exception if the divisor is zero.
     *
     * @param x the numerator
     * @param y the denominator; cannot be zero
     * @return the result of division as a double
     * @throws ArithmeticException if y is zero, as division by zero is not permitted
     */
    public double divide(int x, int y) {
        if (y == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return x / (double) y;
    }
}
