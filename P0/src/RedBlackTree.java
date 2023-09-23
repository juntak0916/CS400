// --== CS400 Spring 2023 File Header Information ==--
// Name: Juntak Lee
// Email: lee2322@wisc.edu
// Team: AP
// TA: <name of your team's ta>
// Lecturer:  Gray Dahl
// Notes to Grader:
import java.util.LinkedList;
import java.util.Stack;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;
        //0 = red, 1 = black, and 2 = double-black
        public int blackHeight;
        // The context array stores the context of the node in the tree:
        // - context[0] is the parent reference of the node,
        // - context[1] is the left child reference of the node,
        // - context[2] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is used to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] context = (Node<T>[])new Node[3];
        public Node(T data) { 
        	this.data = data; 
        	blackHeight=0;
        }
        
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return context[0] != null && context[0].context[2] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is already contained in the tree
     */
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data);
        if (this.root == null) {
            // add first node to an empty tree
            root = newNode; size++; return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                    throw new IllegalArgumentException("This RedBlackTree already contains value " + data.toString());
                } else if (compare < 0) {
                    // insert in left subtree
                    if (current.context[1] == null) {
                        // empty space to insert into
                        current.context[1] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[1];
                    }
                } else {
                    // insert in right subtree
                    if (current.context[2] == null) {
                        // empty space to insert into
                        current.context[2] = newNode;
                        newNode.context[0] = current;
                        this.size++;
                        enforceRBTreePropertiesAfterInsert(newNode);
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.context[2]; 
                    }
                }
            }
        }
    }
    protected void enforceRBTreePropertiesAfterInsert(Node<T> C) {
    	Node<T> P = C.context[0];
    	//if C is the root
    	if(P==null) {
    		C.blackHeight=1;
    		return;
    	}
    	if(P.blackHeight==1) {
    		//there's no violation
    		return;
    	}
    	Node<T> PP = C.context[0].context[0];
    	//when there's no GrandParent Parent is the root
    	if(PP==null) {
    		P.blackHeight=1;
    		return;
    	}
    	//check sides
    	boolean ParentIsLeft= P.equals(PP.context[1]);
    	boolean ChildIsLeft= C.equals(P.context[1]);
    	Node<T> PS = (ParentIsLeft)? PP.context[2] : PP.context[1];
    	//check Sibling color
    	boolean SiblingIsBlack = PS==null || PS.blackHeight==1;
    	
    	//case1 parent's sibling is black && parent and child is on same side
    	if(SiblingIsBlack && ParentIsLeft==ChildIsLeft) {
    		rotate(P,PP);
    		//swap color(P,PP)
    		int tmp=P.blackHeight;
    		P.blackHeight=PP.blackHeight;
    		PP.blackHeight=tmp;
    	}
    	
    	//case2 parent's sibling is black && parent and child is not on same side
    	else if(SiblingIsBlack && ParentIsLeft!=ChildIsLeft) {
    		rotate(C,P);
    		//case1 (now child is top of parent)
    		Node<T> node=C;
    		C=P;
    		P=node;
    		
    		rotate(P,PP);
    		//swap color(P,PP)
    		int tmp=P.blackHeight;
    		P.blackHeight=PP.blackHeight;
    		PP.blackHeight=tmp;   		
    		
    	}
    	//case 3 parent's sibling is red
    	else {
    		//change P PP PS color
    		P.blackHeight=P.blackHeight==1? 0:1;
    		PP.blackHeight=PP.blackHeight==1? 0:1;
    		PS.blackHeight=PS.blackHeight==1? 0:1;
    		//check for PP's violation
    		enforceRBTreePropertiesAfterInsert(PP);
    	}
    	
    	//set root node to black
    	this.root.blackHeight=1;
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
    	//check valid input
    	if(!child.context[0].data.equals(parent.data))
    		throw new IllegalArgumentException("not a child parent relationship");
    	
    	//right rotation
    	if(!child.isRightChild()) {
    		Node<T> A=parent;
    		Node<T> P=A.context[0];
    		Node<T> B=A.context[1];
    		//check middle value of child and parent
    		A.context[1]=B.context[2];
    		if(B.context[2]!=null) {
    			B.context[2].context[0]=A;
    		}
    		
    		//change two input nodes relation and check input parent's parent
    		B.context[2]=A;
    		A.context[0]=B;
    		B.context[0]=P;
    		if(P!=null) {
    			if(P.context[1]!=null && P.context[1].data.equals(A.data))
    				P.context[1]=B;
    			else P.context[2]=B;
    		}else {
    			this.root=B;
    		}
    	}
    	//left rotation
    	else {
    		Node<T> A=parent;
    		Node<T> P=A.context[0];
    		Node<T> B=A.context[2];
    		//check middle value of child and parent
    		A.context[2]=B.context[1];
    		if(B.context[1]!=null) {
    			B.context[1].context[0]=A;
    		}
    		//change two input nodes relation and check input parent's parent
    		B.context[1]=A;
    		A.context[0]=B;
    		B.context[0]=P;
    		if(P!=null) {
    			if(P.context[1]!=null && P.context[1].data.equals(A.data))
    				P.context[1]=B;
    			else P.context[2]=B;
    		}else {
    			this.root=B;
    		}
    	}
    }

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Removes the value data from the tree if the tree contains the value.
     * This method will not attempt to rebalance the tree after the removal and
     * should be updated once the tree uses Red-Black Tree insertion.
     * @return true if the value was remove, false if it didn't exist
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when data is not stored in the tree
     */
    public boolean remove(T data) throws NullPointerException, IllegalArgumentException {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // throw exception if node with data does not exist
            if (nodeWithData == null) {
                throw new IllegalArgumentException("The following value is not in the tree and cannot be deleted: " + data.toString());
            }  
            boolean hasRightChild = (nodeWithData.context[2] != null);
            boolean hasLeftChild = (nodeWithData.context[1] != null);
            if (hasRightChild && hasLeftChild) {
                // has 2 children
                Node<T> successorNode = this.findMinOfRightSubtree(nodeWithData);
                // replace value of node with value of successor node
                nodeWithData.data = successorNode.data;
                // remove successor node
                if (successorNode.context[2] == null) {
                    // successor has no children, replace with null
                    this.replaceNode(successorNode, null);
                } else {
                    // successor has a right child, replace successor with its child
                    this.replaceNode(successorNode, successorNode.context[2]);
                }
            } else if (hasRightChild) {
                // only right child, replace with right child
                this.replaceNode(nodeWithData, nodeWithData.context[2]);
            } else if (hasLeftChild) {
                // only left child, replace with left child
                this.replaceNode(nodeWithData, nodeWithData.context[1]);
            } else {
                // no children, replace node with a null node
                this.replaceNode(nodeWithData, null);
            }
            this.size--;
            return true;
        } 
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This RedBlackTree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNodeWithData(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Helper method that will replace a node with a replacement node. The replacement
     * node may be null to remove the node from the tree.
     * @param nodeToReplace the node to replace
     * @param replacementNode the replacement for the node (may be null)
     */
    protected void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode) {
        if (nodeToReplace == null) {
            throw new NullPointerException("Cannot replace null node.");
        }
        if (nodeToReplace.context[0] == null) {
            // we are replacing the root
            if (replacementNode != null)
                replacementNode.context[0] = null;
            this.root = replacementNode;
        } else {
            // set the parent of the replacement node
            if (replacementNode != null)
                replacementNode.context[0] = nodeToReplace.context[0];
            // do we have to attach a new left or right child to our parent?
            if (nodeToReplace.isRightChild()) {
                nodeToReplace.context[0].context[2] = replacementNode;
            } else {
                nodeToReplace.context[0].context[1] = replacementNode;
            }
        }
    }

    /**
     * Helper method that will return the inorder successor of a node with two children.
     * @param node the node to find the successor for
     * @return the node that is the inorder successor of node
     */
    protected Node<T> findMinOfRightSubtree(Node<T> node) {
        if (node.context[1] == null && node.context[2] == null) {
            throw new IllegalArgumentException("Node must have two children");
        }
        // take a steop to the right
        Node<T> current = node.context[2];
        while (true) {
            // then go left as often as possible to find the successor
            if (current.context[1] == null) {
                // we found the successor
                return current;
            } else {
                current = current.context[1];
            }
        }
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * value. Returns null if there is no node that contains the value.
     * @return the node that contains the data, or null of no such node exists
     */
    protected Node<T> findNodeWithData(T data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                // keep looking in the left subtree
                current = current.context[1];
            } else {
                // keep looking in the right subtree
                current = current.context[2];
            }
        }
        // we're at a null node and did not find data, so it's not in the tree
        return null; 
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.context[2] != null) sb.append(", ");
                    current = popped.context[2];
                } else {
                    nodeStack.add(current);
                    current = current.context[1];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.context[1] != null) q.add(next.context[1]);
                if(next.context[2] != null) q.add(next.context[2]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

    /**
	 * This method tests case 1 we discussed in the lecture
	 * This method will add 3,2,1 successively
	 * without enforcing RBT -> enforcing RBT
	 *     3(1)                2(1)  
	 *    /                	  /  \
	 *   2(0)         ->   1(0)   3(0)
	 *  /
	 * 1(0)  
	 * 
	 * */
	@Test
	void test1() {
		RedBlackTree<Integer> RBT=new RedBlackTree<>();
		try {
			RBT.insert(3);
			RBT.insert(2);
			RBT.insert(1);
		}catch(Exception e) {
			assertEquals(false,true);
		}
		boolean CorrectValues=RBT.root.data== 2 && RBT.root.context[1].data==1 &&RBT.root.context[2].data==3;
		assertEquals(true, CorrectValues);
		boolean CorrectColors=RBT.root.blackHeight== 1 && RBT.root.context[1].blackHeight==0 &&RBT.root.context[2].blackHeight==0;
		assertEquals(true, CorrectColors);
		
	}
	/**
	 * This method tests case 2
	 * This method will add 3,1,2 successively
	 * without enforcing RBT -> enforcing RBT
	 *     3(1)               3(1)         2(1)
	 *    /                	  /            /  \
	 *   1(0)         ->   2(0)     ->  1(0)  3(0)
	 *    \                 /
	 *     2(0)           1(0)
	 * 
	 * */
	@Test
	void test2() {
		RedBlackTree<Integer> RBT=new RedBlackTree<>();
		try {
			RBT.insert(3);
			RBT.insert(1);
			RBT.insert(2);
		}catch(Exception e) {
			assertEquals(false,true);
		}

		boolean CorrectValues=RBT.root.data== 2 && RBT.root.context[1].data==1 &&RBT.root.context[2].data==3;
		assertEquals(true, CorrectValues);
		boolean CorrectColors=RBT.root.blackHeight== 1 && RBT.root.context[1].blackHeight==0 &&RBT.root.context[2].blackHeight==0;
		assertEquals(true, CorrectColors);
	}
	/**
	 * This method tests case 3
	 * This method will add 3,1,4,2 successively
	 * without enforcing RBT -> enforcing RBT
	 *     3(1)                 3(1)
	 *    /    \            	/  \
	 *   1(0)   4(0)      ->  1(1) 4(1)
	 *    \                     \
	 *     2(0)                 2(0) 
	 * 
	 * */
	@Test
	void test3() {
		RedBlackTree<Integer> RBT=new RedBlackTree<>();
		try {
			RBT.insert(3);
			RBT.insert(1);
			RBT.insert(4);
			RBT.insert(2);			
		}catch(Exception e) {
			assertEquals(false,true);
		}
		boolean CorrectValues=RBT.root.data== 3 && RBT.root.context[1].data==1 && RBT.root.context[2].data==4 && RBT.root.context[1].context[2].data==2;
		assertEquals(true, CorrectValues);
		boolean CorrectColors=RBT.root.blackHeight== 1 && RBT.root.context[1].blackHeight==1 &&RBT.root.context[2].blackHeight==1 && RBT.root.context[1].context[2].blackHeight==0;
		assertEquals(true, CorrectColors);
	}

}
