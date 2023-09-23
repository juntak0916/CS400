import java.util.Iterator;

public interface MovieIteratorInterface<T> extends Iterator<T>{
    //public MovieIterator(Iterable<T> t);
    public boolean hasNext();
    public T next();
}