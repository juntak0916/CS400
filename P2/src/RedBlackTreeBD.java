import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class RedBlackTreeBD<T extends Comparable<T>> implements RedBlackTreeInterface<T>{
	private TreeMap<String,MovieInterface> m_tmpRBT=new TreeMap<>();//using for testing will be erased after integration
	@Override
	public boolean contains(Comparable data) {
		return m_tmpRBT.containsKey(((MovieInterface) data));
	}

	@Override
	public int size() {
		return m_tmpRBT.size();
	}

	@Override
	public boolean isEmpty() {
		return m_tmpRBT.isEmpty();
	}

	@Override
	public Iterator iterator() {
		return null;
	}

	@Override
	public boolean insert(Comparable data) throws NullPointerException, IllegalArgumentException {
		try{
			m_tmpRBT.put(((MovieInterface) data).getTitle(),((MovieInterface) data));
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void rotate(Node child, Node parent) throws IllegalArgumentException {}

	@Override
	public boolean remove(Comparable data) throws NullPointerException, IllegalArgumentException {
		return true;
	}

	@Override
	public void replaceNode(Node nodeToReplace, Node replacementNode) {}

	@Override
	public Node findMinOfRightSubtree(Node node) {
		return null;
	}

	@Override
	public Node findNodeWithData(Comparable data) {
		return null;
	}

	@Override
	public String toInOrderString() {
		return null;
	}

	@Override
	public String toLevelOrderString() {
		return null;
	}

	@Override
	public T get(String title) {
		return (T) this.m_tmpRBT.get(title);
	}

}
