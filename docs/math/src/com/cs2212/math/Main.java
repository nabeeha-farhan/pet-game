package com.cs2212.math;

/**
 * The {@code Main} class serves as the entry point for testing shapes and mathematical operations.
 * <p>
 * It demonstrates the usage of {@link Shape} implementations such as {@link Rectangle},
 * {@link Circle}, and {@link Square}. It also uses the {@link Math} class to perform basic
 * arithmetic operations.
 * </p>
 *
 * @author Negar Dehghaneian
 * @version 1.0
 * @see Shape
 * @see Rectangle
 * @see Circle
 * @see Square
 * @see Math
 */
public class Main {

    /**
     * Default constructor for the {@code Main} class.
     * Initializes the program for testing shapes and math operations.
     */
    public Main() {

    }

    /**
     * Prints the area and perimeter of a given shape.
     * <p>Uses the {@link Shape} interface to calculate and print these values.</p>
     *
     * @param s The {@link Shape} object whose area and perimeter are to be printed.
     */
    public static void printShape(Shape s) {
        System.out.println("Shape Area: " + s.calculateArea());
        System.out.println("Shape Perimeter: " + s.calculatePerimeter());
    }

    /**
     * Tests basic mathematical operations using the {@link Math} class.
     * <p>Performs addition, subtraction, multiplication, and division.</p>
     *
     * @param m The {@link Math} object used to perform operations.
     * @param x The first integer operand.
     * @param y The second integer operand.
     */
    public static void mathTest(Math m, int x, int y) {
        System.out.println(x + " + " + y + " = " + m.add(x, y));
        System.out.println(x + " - " + y + " = " + m.subtract(x, y));
        System.out.println(x + " * " + y + " = " + m.multiply(x, y));
        System.out.println(x + " / " + y + " = " + m.divide(x, y));
    }

    /**
     * The main method serves as the entry point for the application.
     * It creates and tests shapes, and demonstrates the usage of the {@link Math} class.
     * <p>Initializes shapes and conducts a series of mathematical tests.</p>
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        System.out.println("Making shapes!");
        Rectangle rect = new Rectangle(10, 5);
        Circle circ = new Circle(7);
        Square squa = new Square(4);

        System.out.println("\nRectangle (" + rect.getWidth() + ", " + rect.getHeight() + "): ");
        printShape(rect);

        System.out.println("\nCircle (" + circ.getRadius() + "): ");
        printShape(circ);

        System.out.println("\nSquare (" + squa.getWidth() + "): ");
        printShape(squa);

        System.out.println("\nMath test for x = 5 and y = 7");
        Math m = new Math();
        mathTest(m, 5, 7);

        System.out.println("\nAll done!");
    }
}
