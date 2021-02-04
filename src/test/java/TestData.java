import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    private List<TestCase> cases;

    public TestData() {

        cases = new ArrayList<TestCase>();

        cases.add(createTestCase01());
        cases.add(createTestCase02());
        cases.add(createTestCase03());
        cases.add(createTestCase04());
        cases.add(createTestCase05());
        cases.add(createTestCase06());
        cases.add(createTestCase07());
        cases.add(createTestCase08());
        cases.add(createTestCase09());
        cases.add(createTestCase10());
    }

    public TestCase createTestCase01() {
        //#
        var parserExpected01 = new OperandNode("#");
        return new TestCase("#",parserExpected01,null,null);
    }
    public TestCase createTestCase02() {
        //(A)#
        var parserExpected02 = new BinOpNode("°",
                new OperandNode("A"),
                new OperandNode("#"));
        return new TestCase("(A)#",parserExpected02,null,null);
    }
    public TestCase createTestCase03() {
        //(A*)#
        var parserExpected03 = new BinOpNode("°",
                new UnaryOpNode("*", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase("(A*)#",parserExpected03,null,null);
    }
    public TestCase createTestCase04() {
        //(A?)#
        var parserExpected04 = new BinOpNode("°",
                new UnaryOpNode("?", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase("(A?)#",parserExpected04,null,null);

    }
    public TestCase createTestCase05() {
        //(A+)#
        var parserExpected05 = new BinOpNode("°",
                new UnaryOpNode("+", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase("(A+)#",parserExpected05,null,null);
    }
    public TestCase createTestCase06() {
        //(AB)#
        var parserExpected06 = new BinOpNode("°",
                new BinOpNode("°",
                        new OperandNode("A"),
                        new OperandNode("B")),
                new OperandNode("#"));
        return new TestCase("(AB)#",parserExpected06,null,null);
    }
    public TestCase createTestCase07() {
        //(A*B+)#
        var parserExpected07 = new BinOpNode("°",
                new BinOpNode("°",
                        new UnaryOpNode("*",
                                new OperandNode("A")),
                        new UnaryOpNode("+",
                                new OperandNode("B"))),
                new OperandNode("#"));
        return new TestCase("(A*B+)#",parserExpected07,null,null);
    }
    public TestCase createTestCase08() {
        //((AB)*)#
        var parserExpected08 = new BinOpNode("°",
                new UnaryOpNode("*",
                        new BinOpNode("°",
                                new OperandNode("A"),
                                new OperandNode("B"))),
                new OperandNode("#"));
        return new TestCase("((AB)*)#",parserExpected08,null,null);
    }
    public TestCase createTestCase09() {
        //(A|B)#
        var parserExpected09 = new BinOpNode("°",
                new BinOpNode("|",
                        new OperandNode("A"),
                        new OperandNode("B")),
                new OperandNode("#"));
        return new TestCase("(A|B)#",parserExpected09,null,null);
    }
    public TestCase createTestCase10() {
        //(123)#
        var parserExpected10 = new BinOpNode("°",
                new BinOpNode("°",
                        new BinOpNode("°",
                                new OperandNode("1"),
                                new OperandNode("2")),
                        new OperandNode("3")),
                new OperandNode("#"));
        return new TestCase("(123)#",parserExpected10,null,null);
    }

    public List<TestCase> getTestCases() {
        return cases;
    }
}
