public class Node<T extends Comparable<T>> {
        public T data;
        //0 = red, 1 = black, and 2 = double-black
        public int blackHeight;
        @SuppressWarnings("unchecked")
        public Node<T>[] context = (Node<T>[])new Node[3];
        public Node(T data) { 
        	this.data = data; 
        }
}