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
//...
    private void match(char symbol)
    {
        if ((input == null) || ("".equals(input)))
        {throw new RuntimeException("Syntax error !");
        }
        if (position >= input.length())
        {
            throw new RuntimeException("End of input reached !");
        }
        if (input.charAt(position) != symbol)
        {
            throw new RuntimeException("Syntax error !");
        }
        position++;
    }
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