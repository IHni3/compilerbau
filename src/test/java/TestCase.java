public class TestCase<InputType,ExpectedType>{

    private final InputType input;
    private final ExpectedType expected;

    public TestCase(InputType input, ExpectedType expected) {
        this.input = input;
        this.expected = expected;
    }
    public InputType getInput() {
        return input;
    }
    public ExpectedType getExpected() {
        return expected;
    }
}
