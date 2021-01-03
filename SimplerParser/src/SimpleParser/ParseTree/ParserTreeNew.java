//Dummy code to evaluate a parsertree and returns the result of the expression

package SimpleParser.ParseTree;

import SimpleParser.ParserBase;
import SimpleParser.ParserException;
import SimpleParser.ParserTreeNode;

public class ParserTreeNew extends ParserBase {
	
	private StringBuffer term;		
	private int currentTermPos;		
	
	private enum TokenType {
		NUMBER, PLUS, MINUS, MULTIPY, DIVIDE, OPENING_BRACKET, CLOSING_BRACKET, EOT;
	}
	
	@Override
	public double evaluate(String s) throws ParserException {
		currentTermPos = 0;
		term = new StringBuffer(s);
		term = deleteSpaces(term);
	    	       
		ParserTreeNode rootNode = parseAddSub();
		
		return evaluateParserTree(rootNode);
	}

	private TokenType getNextTokenType() throws ParserException
	{
		if (currentTermPos == term.length())
		{
			return TokenType.EOT;
		}
		else if (term.charAt(currentTermPos) >= '0' && term.charAt(currentTermPos) <= '9')
		{
			return TokenType.NUMBER;
		}
		else if (term.charAt(currentTermPos) == '+')
		{
			currentTermPos++;
			return TokenType.PLUS;
		}
		else if (term.charAt(currentTermPos) == '-')
		{
			currentTermPos++;
			return TokenType.MINUS;
		} 
		else if (term.charAt(currentTermPos) == '*')
		{
			currentTermPos++;
			return TokenType.MULTIPY;
		}
		else if (term.charAt(currentTermPos) == '/')
		{
			currentTermPos++;
			return TokenType.DIVIDE;	
		}
		else if (term.charAt(currentTermPos) == '(')
		{
			currentTermPos++;
			return TokenType.OPENING_BRACKET;
		}
		else if (term.charAt(currentTermPos) == ')')
		{
			currentTermPos++;
			return TokenType.CLOSING_BRACKET;
		}
		else
		{
			throw new ParserException(term.charAt(currentTermPos) + " is not a valid token");
		}
	}

	private void restoreLastTokenType()
	{
		currentTermPos--;
	}
    

    private ParserTreeNode parseMulDiv() throws ParserException
    {
    	ParserTreeNode rootNode = parseSimpleTerm();
    	TokenType nextToken = getNextTokenType();
    	
    	while (nextToken == TokenType.MULTIPY || nextToken == TokenType.DIVIDE)
    	{
    		ParserTreeNode newRootNode;
    		if (nextToken == TokenType.MULTIPY)
    		{
    			newRootNode = new ParserTreeNode('*');
    		}
    		else
    		{
    			newRootNode = new ParserTreeNode('/');
    		}
    		

    		newRootNode.leftTree = rootNode;
    		newRootNode.rightTree = parseSimpleTerm();

    		rootNode = newRootNode;
    		
    		nextToken = getNextTokenType();
    	}
    	restoreLastTokenType();
    	return rootNode;
    }
    

    private ParserTreeNode parseAddSub() throws ParserException 
    {
    	ParserTreeNode rootNode = parseMulDiv();
    	TokenType nextToken = getNextTokenType();
    	
    	while (nextToken == TokenType.PLUS || nextToken == TokenType.MINUS)
    	{
    		ParserTreeNode newRootNode;
    		if (nextToken == TokenType.PLUS)
    		{
    			newRootNode = new ParserTreeNode('+');
    		}
    		else
    		{
    			newRootNode = new ParserTreeNode('-');
    		}

    		newRootNode.leftTree = rootNode;
    		newRootNode.rightTree = parseMulDiv();

    		rootNode = newRootNode;
    		
    		nextToken = getNextTokenType();
    	}
    	restoreLastTokenType();
    	return rootNode;
    }

    private ParserTreeNode parseSimpleTerm() throws ParserException
    {
    	TokenType nextToken = getNextTokenType();
    	ParserTreeNode rootNode = null;
    	if (nextToken == TokenType.NUMBER)
    	{
    		double num = extractNextNumber();
    		rootNode = new ParserTreeNode(num);
    	}
    	else if (nextToken == TokenType.OPENING_BRACKET)
    	{
    		rootNode = parseAddSub();
    		nextToken = getNextTokenType();
    		if (nextToken != TokenType.CLOSING_BRACKET)
    		{
    			throw new ParserException("Missing closing bracket");
    		}
    	}
    	return rootNode;
    }

	private double extractNextNumber() throws ParserException
	{
		int posNumEnd;
        for (posNumEnd = currentTermPos; posNumEnd < term.length(); posNumEnd++)
        {
        	if ((term.charAt(posNumEnd) < '0' || term.charAt(posNumEnd) > '9') && term.charAt(posNumEnd) != '.')
        		break;
        }
                
        if (posNumEnd > term.length())

            throw new ParserException(term + " is not a number.");

        String sub = term.substring(currentTermPos, posNumEnd);

        double x;
        try {
            x = Double.parseDouble(sub);
        } catch (NumberFormatException ex) {
            throw new ParserException("String to number parsing exception: " + sub);
        }

        currentTermPos = currentTermPos + (posNumEnd - currentTermPos);
        return x;
	}

    private double evaluateParserTree(ParserTreeNode tree)
    {
        if (tree == null)
            return Double.NaN;

        if (tree.value.toString().equals("+"))
            return evaluateParserTree(tree.leftTree) + evaluateParserTree(tree.rightTree);
        else if (tree.value.toString().equals("-"))
            return evaluateParserTree(tree.leftTree) - evaluateParserTree(tree.rightTree);
        else if (tree.value.toString().equals("*"))
            return evaluateParserTree(tree.leftTree) * evaluateParserTree(tree.rightTree);
        else if (tree.value.toString().equals("/"))
            return evaluateParserTree(tree.leftTree) / evaluateParserTree(tree.rightTree);
        else
            return Double.valueOf(tree.value.toString()).doubleValue();
    }
}
