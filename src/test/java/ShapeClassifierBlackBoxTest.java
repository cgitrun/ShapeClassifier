import org.junit.jupiter.api.Test;

public class ShapeClassifierBlackBoxTest {

    @Test
    public void testCorrectShapeSizeEvenOddGuess() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,Yes,10";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testCorrectShapeIncorrectSizeGuess() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Small,Yes,10";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Wrong Size") : "Test failed: Expected 'Wrong Size', got '" + result + "'";
    }

    @Test
    public void testCorrectShapeIncorrectEvenOddGuess() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,No,10";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Wrong Even/Odd") : "Test failed: Expected 'Wrong Even/Odd', got '" + result + "'";
    }

    @Test
    public void testIncorrectShapeGuess() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Square,Large,Yes,10,10,10,10";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("No: Suggestion=Rectangle") : "Test failed: Expected 'No: Suggestion=Rectangle', got '" + result + "'";
    }

    @Test
    public void testMultipleIncorrectGuesses() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Equilateral,Small,No,5,5,5";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("No: Suggestion=Scalene") : "Test failed: Expected 'No: Suggestion=Scalene', got '" + result + "'";
    }

    @Test
    public void testSmallShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Small,No,3";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testLargeShape() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,Yes,50";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testInvalidInput() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,Yes,invalid";
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("No") : "Test failed: Expected 'No', got '" + result + "'";
    }

    // --- New Tests for Equivalence Partitioning and Boundary Testing ---

    @Test
    public void testEquivalencePartitioningForCircle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,Yes,50"; // Expect "Yes" since perimeter = 314
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testEquivalencePartitioningForSquare() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Square,Large,Yes,10,10,10,10"; // Perimeter = 40
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testEquivalencePartitioningForTriangle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Scalene,Large,Yes,5,7,10"; // Perimeter = 22
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testBoundaryValueTestSmallPerimeter() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Small,Yes,3"; // Perimeter is 18.85, so this is still large, needs to adjust for boundary test.
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testBoundaryValueTestLargePerimeter() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Circle,Large,Yes,55"; // Perimeter = 344.32 which is large
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testBoundaryValueTestMaxSides() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Rectangle,Large,Yes,4095,4095,4095,4095"; // Max side length value
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }

    @Test
    public void testBoundaryValueTestMinSides() {
        ShapeClassifier classifier = new ShapeClassifier();
        String testInput = "Rectangle,Small,Yes,1,1,1,1"; // Min side length value
        String result = classifier.evaluateGuess(testInput);
        assert result.equals("Yes") : "Test failed: Expected 'Yes', got '" + result + "'";
    }
}
