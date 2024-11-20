import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeClassifierWhiteBoxTest {

    @Test
    public void testEvaluateGuess_LineCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Line,Small,Yes,5");
        assertEquals("Yes", result); // Line, small perimeter (5), even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_LineIncorrectShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Small,Yes,5");
        assertEquals("No: Suggestion=Line", result); // Wrong shape guess, suggests Line
    }

    @Test
    public void testEvaluateGuess_CircleCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Small,Yes,5,5");
        assertEquals("Yes", result); // Circle, small perimeter, even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_CircleIncorrectSize() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Large,Yes,5,5");
        assertEquals("No: Wrong Size", result); // Incorrect size guess, correct shape
    }

    @Test
    public void testEvaluateGuess_EllipseCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Ellipse,Large,Yes,5,10");
        assertEquals("Yes", result); // Ellipse, large perimeter, even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_EllipseIncorrectShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Large,Yes,5,10");
        assertEquals("No: Suggestion=Ellipse", result); // Wrong shape guess, suggests Ellipse
    }

    @Test
    public void testEvaluateGuess_SquareCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Square,Large,Yes,5,5,5,5");
        assertEquals("Yes", result); // Square, large perimeter, even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_SquareIncorrectSize() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Square,Small,Yes,5,5,5,5");
        assertEquals("No: Wrong Size", result); // Incorrect size guess, correct shape
    }

    @Test
    public void testEvaluateGuess_RectangleCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Rectangle,Large,Yes,5,10,5,10");
        assertEquals("Yes", result); // Rectangle, large perimeter, even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_RectangleIncorrectShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Square,Large,Yes,5,10,5,10");
        assertEquals("No: Suggestion=Rectangle", result); // Wrong shape guess, suggests Rectangle
    }

    @Test
    public void testEvaluateGuess_EquilateralCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Equilateral,Small,No,3,3,3");
        assertEquals("Yes", result); // Equilateral triangle, small perimeter, odd guess (No)
    }

    @Test
    public void testEvaluateGuess_EquilateralIncorrectShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Scalene,Small,No,3,3,3");
        assertEquals("No: Suggestion=Equilateral", result); // Incorrect shape guess, suggests Equilateral
    }

    @Test
    public void testEvaluateGuess_IsoscelesCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Isosceles,Large,Yes,3,3,5");
        assertEquals("Yes", result); // Isosceles triangle, large perimeter, even guess (Yes)
    }

    @Test
    public void testEvaluateGuess_IsoscelesIncorrectSize() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Isosceles,Small,Yes,3,3,5");
        assertEquals("No: Wrong Size", result); // Incorrect size guess, correct shape
    }

    @Test
    public void testEvaluateGuess_ScaleneCorrect() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Scalene,Large,No,5,5,7");
        assertEquals("Yes", result); // Scalene triangle, large perimeter, odd guess (No)
    }

    @Test
    public void testEvaluateGuess_ScaleneIncorrectEvenOdd() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Scalene,Large,Yes,5,5,7");
        assertEquals("No: Wrong Even/Odd", result); // Incorrect even/odd guess, correct shape and size
    }

    @Test
    public void testEvaluateGuess_TooManyBadGuesses() {
        ShapeClassifier classifier = new ShapeClassifier();
        classifier.evaluateGuess("Circle,Small,Yes,5,5"); // First incorrect guess
        classifier.evaluateGuess("Rectangle,Large,No,5,5,5,5"); // Second incorrect guess
        String result = classifier.evaluateGuess("Scalene,Large,Yes,5,5,7"); // Third incorrect guess
        assertThrows(SystemExitException.class, () -> classifier.evaluateGuess("Scalene,Large,Yes,5,5,7"));
        // Should trigger System.exit() after the third bad guess
    }

    @Test
    public void testEvaluateGuess_ExceptionHandling() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("InvalidInput");
        assertEquals("No", result); // Malformed input should return "No"
    }

    // Additional helper class for handling System.exit() calls during tests
    public static class SystemExitException extends SecurityException { }
}
