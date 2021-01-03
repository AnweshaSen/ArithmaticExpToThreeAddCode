//Main Code where the arithmetic expression is being represented by Three Address Code(quadruple form).

package ParseTree.ThreeAddressCode;

import java.io.*;

//Structure of the Abstract Syntax Tree
class Node 
{ 
    String key; 
    Node left, right; 
  
    public Node(String item) 
    { 
        key = item; 
        left = right = null; 
    } 
}

public class ThreeAddressCode {
	
	Node root; 
	  
	ThreeAddressCode() 
    { 
        root = null; 
    }

	String exp = "";
	
	//Tree to arithmetic expression.
	void printInorder(Node node) 
    { 
        if (node == null) 
            return; 

        printInorder(node.left); 
        exp += node.key;
        printInorder(node.right); 
    }
	
	void printInorder()    {     printInorder(root);   }
	
	String returnArithmeticExp() {
		System.out.println(exp);
		return exp;
	}

	
	private static final char[][] precedence = {
			{'/', '1'},
			{'*', '1'},
			{'+', '2'},
			{'-', '2'}
		};
		
		private static int precedenceOf(String t)
		{
			char token = t.charAt(0);
			for (int i=0; i < precedence.length; i++)
			{
				if (token == precedence[i][0])
				{
					return Integer.parseInt(precedence[i][1]+"");
				}
			}
			return -1;
		}
		
		public static void main(String[] args) throws Exception
		{
			//Forming the tree.
			ThreeAddressCode tree = new ThreeAddressCode(); 
	        tree.root = new Node("+"); 
	        tree.root.left = new Node("-"); 
	        tree.root.right = new Node("*"); 
	        tree.root.left.left = new Node("+"); 
	        tree.root.left.right = new Node("d");
	        tree.root.left.left.left = new Node("*");
	        tree.root.left.left.right = new Node("a");
	        tree.root.left.left.left.left = new Node("b");
	        tree.root.left.left.left.right = new Node("c");
	        tree.root.right.left = new Node("/");
	        tree.root.right.right = new Node("+");
	        tree.root.right.left.left = new Node("e");
	        tree.root.right.left.right = new Node("f");
	        tree.root.right.right.left = new Node("a");
	        tree.root.right.right.right = new Node("b");

	        System.out.println("\nThe arithmetic expression given: "); 
	        tree.printInorder();
	        
			int i, j, opc=0;
			char token;
			boolean processed[];
			String[][] operators = new String[10][2];
			String temp;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//			System.out.print("\nEnter an expression: ");
//			expr = in.readLine();
			String expr = tree.returnArithmeticExp();
			processed = new boolean[expr.length()];
			for (i=0; i < processed.length; i++)
			{
				processed[i] = false;
			}
			for (i=0; i < expr.length(); i++)
			{
				token = expr.charAt(i);
				for (j=0; j < precedence.length; j++)
				{
					if (token==precedence[j][0])
					{
						operators[opc][0] = token+"";
						operators[opc][1] = i+"";
						opc++;
						break;
					}
				}
			}
			System.out.println("\nOperators:\nOperator\tLocation");
			for (i=0; i < opc; i++)
			{
				System.out.println(operators[i][0] + "\t\t" + operators[i][1]);
			}
			//sort
			for (i=opc-1; i >= 0; i--)
			{
				for (j=0; j < i; j++)
				{
					if (precedenceOf(operators[j][0]) > precedenceOf(operators[j+1][0]))
					{
						temp = operators[j][0];
						operators[j][0] = operators[j+1][0];
						operators[j+1][0] = temp;
						temp = operators[j][1];
						operators[j][1] = operators[j+1][1];
						operators[j+1][1] = temp;
					}				
				}
			}
			System.out.println("\nOperators sorted in their precedence:\nOperator\tLocation");
			for (i=0; i < opc; i++)
			{
				System.out.println(operators[i][0] + "\t\t" + operators[i][1]);
			}
			System.out.println("\nQuadruple representation of the above Three Address Code is:\n");
			System.out.println("\nOperator\tOperand1\tOperand2\tResult\n");
			System.out.println("--------------------------------------------------------------");
			System.out.println("--------------------------------------------------------------\n");
			for (i=0; i < opc; i++)
			{
				j = Integer.parseInt(operators[i][1]+"");
				String op1="", op2="";
				if (processed[j-1]==true)
				{
					if (precedenceOf(operators[i-1][0]) == precedenceOf(operators[i][0]))
					{
						op1 = "t"+i;
					}
					else
					{
						for (int x=0; x < opc; x++)
						{
							if ((j-2) == Integer.parseInt(operators[x][1]))
							{
								op1 = "t"+(x+1)+"";
							}
						}
					}
				}
				else
				{
					op1 = expr.charAt(j-1)+"";
				}
				if (processed[j+1]==true)
				{
					for (int x=0; x < opc; x++)
					{
						if ((j+2) == Integer.parseInt(operators[x][1]))
						{
							op2 = "t"+(x+1)+"";
						}
					}
				}
				else
				{
					op2 = expr.charAt(j+1)+"";
				}
				System.out.println("   "+operators[i][0]+"\t\t   "+op1+"\t\t   "+op2+"\t\t   "+"t"+(i+1));
				System.out.println("--------------------------------------------------------------\n");
				processed[j] = processed[j-1] = processed[j+1] = true;
			}
			System.out.println("   =\t\t   "+"t"+i+"\t\t   \t\t   "+"t"+(i+1));
			System.out.println("--------------------------------------------------------------");
			System.out.println("--------------------------------------------------------------\n");
			
		}
}
