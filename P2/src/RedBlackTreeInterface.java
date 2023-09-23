public interface RedBlackTreeInterface<T extends Comparable<T>> extends SortedCollectionInterface<T>, Iterable<T> {
    boolean insert(T data) throws NullPointerException, IllegalArgumentException;
    void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException;
    boolean remove(T data) throws NullPointerException, IllegalArgumentException;
    void replaceNode(Node<T> nodeToReplace, Node<T> replacementNode);
    Node<T> findMinOfRightSubtree(Node<T> node);
    Node<T> findNodeWithData(T data);
    String toInOrderString();
    String toLevelOrderString();
    String toString();
    T get(String title);
}

