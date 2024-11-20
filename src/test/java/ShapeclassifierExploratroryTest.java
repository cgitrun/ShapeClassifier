import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeClassifierExploratoryTest {

    private ShapeClassifier classifier;

    @BeforeEach
    public void setUp() {
        classifier = new ShapeClassifier();
    }

    @Test
    public void testCorrectGuesses() {
        // Test for an equilateral triangle with correct guesses
        String result = classifier.evaluateGuess("Equilateral,Large,Yes,100,100,100");
        assertEquals("Yes", result);

        // Test for a circle with correct guesses
        result = classifier.evaluateGuess("Circle,Small,Yes,10");
        assertEquals("Yes", result);

        // Test for a rectangle with correct guesses
        result = classifier.evaluateGuess("Rectangle,Large,Yes,10,20,10,20");
        assertEquals("Yes", result);
    }

    @Test
    public void testWrongShapeGuess() {
        // Test wrong shape guess (Rectangle guessed for a Square)
        String result = classifier.evaluateGuess("Rectangle,Large,Yes,10,10,10,10");
        assertEquals("No: Suggestion=Square", result);

        // Test wrong shape guess (Circle guessed for an Ellipse)
        result = classifier.evaluateGuess("Circle,Small,Yes,10,20");
        assertEquals("No: Suggestion=Ellipse", result);
    }

    @Test
    public void testWrongSizeGuess() {
        // Test wrong size guess (Large guessed for a small shape)
        String result = classifier.evaluateGuess("Circle,Large,Yes,5");
        assertEquals("No: Suggestion=Small", result);

        // Test wrong size guess (Small guessed for a large shape)
        result = classifier.evaluateGuess("Circle,Small,Yes,15");
        assertEquals("No: Suggestion=Large", result);
    }

    @Test
    public void testWrongEvenOddGuess() {
        // Test wrong even/odd guess (Even guessed for an odd perimeter)
        String result = classifier.evaluateGuess("Circle,Large,Yes,15");
        assertEquals("No: Suggestion=Odd", result);

        // Test wrong even/odd guess (Odd guessed for an even perimeter)
        result = classifier.evaluateGuess("Circle,Large,No,20");
        assertEquals("No: Suggestion=Even", result);
    }

    @Test
    public void testIncorrectGuessesExceedLimit() {
        // Test case where bad guesses exceed the limit (3 wrong guesses)
        String result = classifier.evaluateGuess("Circle,Large,Yes,15");
        assertEquals("No: Suggestion=Large", result);

        result = classifier.evaluateGuess("Rectangle,Large,Yes,10,10,10,10");
        assertEquals("No: Suggestion=Square", result);

        result = classifier.evaluateGuess("Equilateral,Large,Yes,100,100,100");
        assertEquals("No: Suggestion=Not A Triangle", result);

        // At this point, the number of incorrect guesses should exceed 3
        // So, the program should print an error message and terminate (which can't be caught in a standard test).
    }

    @Test
    public void testValidTriangleGuesses() {
        // Equilateral triangle guess
        String result = classifier.evaluateGuess("Equilateral,Large,Yes,100,100,100");
        assertEquals("Yes", result);

        // Isosceles triangle guess
        result = classifier.evaluateGuess("Isosceles,Large,Yes,50,50,100");
        assertEquals("Yes", result);

        // Scalene triangle guess
        result = classifier.evaluateGuess("Scalene,Large,Yes,30,40,50");
        assertEquals("Yes", result);
    }

    @Test
    public void testInvalidTriangleGuesses() {
        // Invalid triangle (e.g., sides do not form a valid triangle)
        String result = classifier.evaluateGuess("Equilateral,Large,Yes,100,100,300");
        assertEquals("No: Suggestion=Not A Triangle", result);
    }

    @Test
    public void testEdgeCases() {
        // Test with very small dimensions (size small, even perimeter)
        String result = classifier.evaluateGuess("Circle,Small,Yes,2");
        assertEquals("Yes", result);

        // Test with very large dimensions (size large, even perimeter)
        result = classifier.evaluateGuess("Circle,Large,Yes,1000");
        assertEquals("Yes", result);

        // Test with max value for sides
        result = classifier.evaluateGuess("Rectangle,Large,Yes,4095,4095,4095,4095");
        assertEquals("Yes", result);
    }

    @Test
    public void testEdgeCaseForShapeWithNoParams() {
        // Shape with no dimensions (Line)
        String result = classifier.evaluateGuess("Line,Small,Yes,0");
        assertEquals("Yes", result);
    }
}
