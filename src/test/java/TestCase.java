public class TestCase {
    private String input;
    private Visitable parserExpected;
    private Visitable visitorNFLExpected;
    private Visitable visitorFollowposExpected;

    public TestCase(String input, Visitable parserExpected, Visitable visitorNFLExpected, Visitable visitorFollowposExpected) {
        this.input = input;
        this.parserExpected = parserExpected;
        this.visitorNFLExpected = visitorNFLExpected;
        this.visitorFollowposExpected = visitorFollowposExpected;
    }

    public String getInput() {
        return input;
    }

    public Visitable getParserExpected() {
        return parserExpected;
    }

    public Visitable getVisitorNFLExpected() {
        return visitorNFLExpected;
    }

    public Visitable getVisitorFollowposExpected() {
        return visitorFollowposExpected;
    }
}
