//Dummy code to check if the parsing algo works perfectly.

package SimpleParser;

import SimpleParser.ParseTree.ParserTreeNew;
import SimpleParser.ParserInfix.ParserInfixToTree;
import SimpleParser.PostfixParser.ParserPostfixStack;
import SimpleParser.RecursiveParser.ParserRecursive;

public class ParserTest {

    class ParserTestcase {
        public String term;
        public double result;

        public ParserTestcase(String term, double result)
        {
            this.term = term;
            this.result = result;
        }
    }

    // Just to test if the parsetree is being created successfully for various expressions.
    ParserTestcase[] tests = {
		new ParserTestcase("2", 2),
		new ParserTestcase("1.2", 1.2),
		new ParserTestcase("2+3", 5),
        new ParserTestcase("1+2+3", 6),
        new ParserTestcase("1+2*3", 7),
        new ParserTestcase("6*2", 12),
        new ParserTestcase("2*3+4", 10),
        new ParserTestcase("3+4+5*6*2+1", 68),
        new ParserTestcase("(1+2)+3", 6),
        new ParserTestcase("1+(2+3)", 6),
        new ParserTestcase("(4+1+2)*3", 21),
        new ParserTestcase("5 * (((9+8) * (4*6)) + 7)", 2075),
        new ParserTestcase("(3*4+16)/7", 4),
        new ParserTestcase("(20-2)/3", 6),
        new ParserTestcase("4*4+2", 18),
        new ParserTestcase("35/7+3", 8),
        new ParserTestcase("(3.5+3.6)*2.3", 16.33),
        new ParserTestcase(" 4 * ( (4+3) * (3*2) + 3)", 180),
        new ParserTestcase("3.1 + 4.5 * 5", 25.6),
        new ParserTestcase("((4+5)*2)*(3+1)", 72),
        new ParserTestcase("2*(3+5)", 16),
        new ParserTestcase("(3+5)*2+(6-3)", 19),
        new ParserTestcase("5/4*5", 6.25),
		new ParserTestcase("(5+8)-5+3", 11),
		new ParserTestcase("5+8-5+3", 11),
		new ParserTestcase("5+(8-5)+3", 11),
		new ParserTestcase("5+8-(5+3)", 5),
		new ParserTestcase("(10*2)/5*4", 16),
		new ParserTestcase("(8-5)+5-3", 5),
		new ParserTestcase("5*8/5-2", 6),
		new ParserTestcase("(5+3)-5+3", 6)
    };

    public void RunTests()
    {
        ParserBase pr = new ParserRecursive();
        ParserBase pps = new ParserPostfixStack();
        ParserBase pit = new ParserInfixToTree();
        ParserBase pt = new ParserTreeNew();

        double resPr = 0, resPps = 0, resPit = 0, resPt = 0;

        for (int testcase = 0; testcase < tests.length; testcase++)
        {
            System.out.println("Term to evaluate: " + tests[testcase].term);

            try {
                resPr = pr.evaluate(tests[testcase].term);
                resPps = pps.evaluate(tests[testcase].term);
                resPit = pit.evaluate(tests[testcase].term);
                resPt = pt.evaluate(tests[testcase].term);
            }
            catch (ParserException pe)
            {
                System.out.println("Parser Exception: " + pe.toString());
            }
            catch (Exception ex)
            {
                System.out.println("General Exception: " + ex.toString());
            }

             System.out.println("Result RecursiveParser: " + resPr);
             System.out.println("Result PostfixStackParser: " + resPps);
             System.out.println("Result InfixToTreeParser: " + resPit);
             System.out.println("Result ParseTree: " + resPt);
             System.out.println("Result Expected: " + tests[testcase].result);

             if (resPr != tests[testcase].result)
                 System.out.println(" --> ERROR in RecursiveParser");
             if (resPps != tests[testcase].result)
                 System.out.println(" --> ERROR in ParserPostfixStack");
             if (resPit != tests[testcase].result)
                 System.out.println(" --> ERROR in ParserInfixToTree");
             if (resPt != tests[testcase].result)
                 System.out.println(" --> ERROR in ParseTree");
             System.out.println("--------------------------------");
        }
    }

    public static void main(String[] args)
    {
        if (args.length >= 1)
        {
            String termStr = args[0];
            ParserBase pr = new ParserRecursive();
            ParserBase pps = new ParserPostfixStack();
            ParserBase pit = new ParserInfixToTree();
            ParserBase pt = new ParserTreeNew();
            double resPr = 0, resPps = 0, resPit = 0, resPt = 0;

            try {
                resPr = pr.evaluate(termStr);
                resPps = pps.evaluate(termStr);
                resPit = pit.evaluate(termStr);
                resPt = pt.evaluate(termStr);

                System.out.println("Result Recursive Parser: " + resPr);
                System.out.println("Result PostFix-Stack Parser: " + resPps);
                System.out.println("Result Infix-Tree Parser: " + resPit);
                System.out.println("Result ParseTree: " + resPt);
            }
            catch (ParserException pe)
            {
                System.out.println("Parser Exception: " + pe.toString());
            }
            catch (Exception ex)
            {
                System.out.println("General Exception: " + ex.toString());
            }
            
        }
        else
        {
            ParserTest pt = new ParserTest();
            pt.RunTests();
        }
    }

}