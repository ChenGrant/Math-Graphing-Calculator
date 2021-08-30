
public class Node {
	Function data;
	Node next = null;

	public Node(Function data) {
		this.data = data;
	}
	public boolean hasNext() {
		if (next==null)
			return false;
		return true;
	}
}

