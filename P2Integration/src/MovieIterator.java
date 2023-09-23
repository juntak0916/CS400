public class MovieIterator<T extends Comparable<T>> implements MovieIteratorInterface<T>{
    RedBlackTree<T> tree; // a valid RBT to iterate through
    RedBlackTree.Node<T> currNode; // a starting node within the RBT to iterate from

    public MovieIterator (RedBlackTree<T> rbt){
        tree = rbt;
        currNode =  rbt.getHead();
    }
    //return if the current node is not null
    public boolean hasNext() {
        return currNode != null;
    }

    @Override
    //return the successor of the current node
    public T next() {
        T temp = currNode.data;
        RedBlackTree.Node<T> rightChild = currNode.context[2];
        if(!currNode.isRightChild()){
            if(rightChild != null){
                currNode = rightChild;
                while(currNode.context[1] != null){
                    currNode = currNode.context[1];
                }
            }
            else{
                currNode = currNode.context[0];
            }
        }
        else{
            if(rightChild != null){
                currNode = rightChild;
                while(currNode.context[1] != null){
                    currNode = currNode.context[1];
                }
            }
            else{
                RedBlackTree.Node<T> holder = currNode.context[0];
                while(holder.isRightChild()){
                    holder = holder.context[0];
                }
                if(holder.equals(tree.root)){
                    currNode = null;
                }
                else{
                    currNode = holder.context[0];
                }
            }
        }
        return temp;
    }
}