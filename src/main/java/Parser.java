public class Parser
{
    private int position;
    private final String input;
//...
    public Parser(String input)
    {
        this.input = input;
        this.position = 0;
    }

    private char getCurrentChar()
    {
        return input.charAt(position);
    }
//...
    private void match(char symbol)
    {
        if ((input == null) || ("".equals(input)))
        {
            throw new RuntimeException("Syntax error !");
        }
        if (position >= input.length())
        {
            throw new RuntimeException("End of input reached !");
        }
        if (getCurrentChar() != symbol)
        {
            throw new RuntimeException("Syntax error !");
        }

        position++;
    }

    public Visitable run() {
        return startState();
    }

    private Visitable startState() {
        if(getCurrentChar() == '#') {
            match('#');
            return new OperandNode("#");
        }
        else if(getCurrentChar() == '(') {
            match('(');

            Visitable leafLeft = RegExpState();

            match(')');

            match('#');
            OperandNode leafRight = new OperandNode("#");

            return new BinOpNode("°", leafLeft, leafRight);
        }
        else
        {
            throw new RuntimeException("Syntax error !");
        }
    };
    private Visitable RegExpState() {
            Visitable termNode = TermState();
            return RegExp1State(termNode);
    };
    private Visitable TermState() {
        return TermState(null);
    }
    private Visitable TermState(Visitable node) {
        //first
        if(Character.isLetterOrDigit(getCurrentChar()) || getCurrentChar() == '(')
        {
            Visitable factorNode = FactorState();
            Visitable termParamNode = null;
            if(node != null)
            {
                termParamNode = new BinOpNode("°", node, factorNode);
            }
            else
            {
                termParamNode = factorNode;
            }
            return TermState(termParamNode);
        }
        else
        {
            return node;
        }
    };
    private Visitable RegExp1State(Visitable node) {
        if(getCurrentChar() == '|')
        {
            match('|');
            Visitable termNode = TermState();
            BinOpNode rootNode = new BinOpNode("|", node, termNode);

            return RegExp1State(rootNode);
        }
        else {
            return node;
        }
    };
    private Visitable FactorState() {
        Visitable node = ElemState();
        return H0pState(node);
    };
    private Visitable H0pState(Visitable child) {
        if(getCurrentChar() == '*') {
            match('*');
            return new UnaryOpNode("*", child);
        }
        else if(getCurrentChar() == '+') {
            match('+');
            return new UnaryOpNode("+", child);

        }
        else if(getCurrentChar() == '?') {
            match('?');
            return new UnaryOpNode("?", child);

        }
        else {
            return child;
        }
    };
    private Visitable ElemState() {
        if(Character.isLetterOrDigit(getCurrentChar()))
        {
            OperandNode node = AlphaNumState();
            return node;
        }
        else if(getCurrentChar() == '(') {
            match('(');
            Visitable node = RegExpState();
            match(')');
            return node;
        }
        else {
            throw new RuntimeException("Syntax error !");
        }
    };
    private OperandNode AlphaNumState() {
        if(Character.isLetterOrDigit(getCurrentChar()))
        {
            OperandNode node = new OperandNode(String.valueOf(getCurrentChar()));
            match(getCurrentChar());
            return node;
        }
        else
        {
            throw new RuntimeException("Syntax error !");
        }
    };


//------------------------------------------------------------------
// 1. wird benoetigt bei der Regel Start -> '(' RegExp ')''#'
// 2. wird benoetigt bei der Regel Start -> '#'
// 3. wird sonst bei keiner anderen Regel benoetigt
//------------------------------------------------------------------
    private void assertEndOfInput()
    {
        if (position < input.length())
        {
            throw new RuntimeException(" No end of input reached !");
        }
    }
}