import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class VisitorNFLTest {

    @TestFactory
    Stream<DynamicTest> visitorFactoryTest() {

        var testData = new TestData();

        return testData.getTestCases().stream()
                .map(testcase -> DynamicTest.dynamicTest("visiting: " + testcase.getInput(),
                        () -> {
                            var visitor = new VisitorNFL();
                            var actual = testcase.getParserExpected();
                            DepthFirstIterator.traverse(actual, visitor);
                            var expected = testcase.getVisitorNFLExpected();

                            Assert.assertTrue(equals(actual,expected));
                        }));
    }


    private boolean equals(Visitable expected, Visitable visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;
        if (expected.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) expected;
            BinOpNode op2 = (BinOpNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos()) &&
                    equals(op1.getLeft(), op2.getLeft()) &&
                    equals(op1.getRight(), op2.getRight());
        }
        if (expected.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) expected;
            UnaryOpNode op2 = (UnaryOpNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos()) &&
                    equals(op1.getSubNode(), op2.getSubNode());
        }
        if (expected.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) expected;
            OperandNode op2 = (OperandNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos());
        }
        throw new IllegalStateException(
                String.format( "Beide Wurzelknoten sind Instanzen der Klasse %1$s !" + " Dies ist nicht erlaubt!", expected.getClass().getSimpleName())
);
    }
}
