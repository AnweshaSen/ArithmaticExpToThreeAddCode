//Dummy Code Just to test if the tree can lead to the exact arithmatic expression.

package ParseTree.ArithmaticExp;

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

public class ParseTreeToExp {
	
	Node root; 
	  
	ParseTreeToExp() 
    { 
        root = null; 
    }

	String Exp = "";
	
	void printInorder(Node node) 
    { 
        if (node == null) 
            return; 
  
        /* first recur on left child */
        printInorder(node.left); 
  
        /* then print the data of node */
//        System.out.print(node.key + " "); 
        Exp += node.key;
  
        /* now recur on right child */
        printInorder(node.right); 
    }
	
	void printInorder()    {     printInorder(root);   }
	
	String returnArithmeticExp() {
		System.out.println(Exp);
		return Exp;
	}

	
	public static void main(String[] args) 
    { 
		ParseTreeToExp tree = new ParseTreeToExp(); 
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

        System.out.println("\nInorder traversal of binary tree is "); 
        tree.printInorder();
        
        tree.returnArithmeticExp();

    } 

}
