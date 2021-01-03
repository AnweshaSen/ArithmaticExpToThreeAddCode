//Dummy code for exception handling for any wrong result during parsetree o/p generation.

package SimpleParser;

public class ParserException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParserException(String s)
    {
        super(s);
    }
}
