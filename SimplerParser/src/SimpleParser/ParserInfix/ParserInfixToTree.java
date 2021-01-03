//Dummy code to evaluate a parsertree and returns the result of the expression

package SimpleParser.ParserInfix;

import SimpleParser.ParserBase;
import SimpleParser.ParserException;
import SimpleParser.ParserTreeNode;

public class ParserInfixToTree extends ParserBase {

    private ParserTreeNode getNextNumber(StringBuffer s) throws ParserException
    {
        int pos = s.length() - 1;
        double x;

        if (s.charAt(pos) == ')')
        {

            s.deleteCharAt(s.length() - 1);
            ParserTreeNode nodetmp = parseAddSub(s);
            if (s.charAt(s.length() - 1) == '(')
            {

                s.deleteCharAt(s.length() - 1);
                return nodetmp;
            }
            else
                throw new ParserException("Brackets do not match");
        }

        for (pos = s.length() - 1; pos >= 0; pos--)
        {
            if ((s.charAt(pos) < '0' || s.charAt(pos) > '9') && s.charAt(pos) != '.')
                break;
        }

        if (pos == s.length() - 1)
            return null;

        String sub = s.substring(pos+1, s.length());
        try {
            x = Double.parseDouble(sub);
        } catch (NumberFormatException ex) {
            throw new ParserException("String to number parsing exception: " + s);
        }

        s.delete(pos+1, s.length());
        return new ParserTreeNode(x);
    }

    private ParserTreeNode parseMulDiv(StringBuffer s) throws ParserException
    {
        ParserTreeNode first = getNextNumber(s);
        if (first == null)
            return null;

        if (s.length() >= 1 && s.charAt(s.length() - 1) == '*')
        {
            s.deleteCharAt(s.length() - 1);
            ParserTreeNode ptntmp = parseMulDiv(s);
            return new ParserTreeNode(ptntmp, first,'*');
        }
        else if (s.length() >= 1 && s.charAt(s.length() - 1) == '/')
        {
            s.deleteCharAt(s.length() - 1);
            ParserTreeNode ptntmp = parseMulDiv(s);
            return new ParserTreeNode(ptntmp, first,'/');
        }
        else
            return first;
    }

    private ParserTreeNode parseAddSub(StringBuffer s) throws ParserException
    {
        ParserTreeNode first = parseMulDiv(s);
        if (first == null)
            return null;

        if (s.length() >= 1 && s.charAt(s.length() - 1) == '+')
        {
            s.deleteCharAt(s.length() - 1);

            ParserTreeNode ptntmp = parseAddSub(s);
            return new ParserTreeNode(ptntmp, first,'+');
        }
        else if (s.length() >= 1 && s.charAt(s.length() - 1) == '-')
        {
            s.deleteCharAt(s.length() - 1);

            ParserTreeNode ptntmp = parseAddSub(s);
            return new ParserTreeNode(ptntmp, first, '-');
        }
        else
            return first;

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

        
    @Override
    public double evaluate(String s) throws ParserException
    {
       StringBuffer sBuf = new StringBuffer(s);
       sBuf = deleteSpaces(sBuf);
        
       ParserTreeNode ptn = parseAddSub(sBuf);
     
       return evaluateParserTree(ptn);
    }

}
