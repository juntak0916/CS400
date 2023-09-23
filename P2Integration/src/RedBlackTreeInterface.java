import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface RedBlackTreeInterface<T extends Comparable<T>> extends Iterable<T>, SortedCollectionInterface<T>{
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;
    public boolean contains(T data);
    public int size();
    public boolean isEmpty();
    public String toInOrderString();
    public String toLevelOrderString();
    public String toString();
    public Iterator<T> iterator();
    public List<MovieInterface> get(String item);


}
