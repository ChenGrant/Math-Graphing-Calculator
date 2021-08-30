
public class SinglyLinkedList {
	private Node head = null;
	private Node curr = null;
	private int size = 0;

	public void add(Function f) {
		Node n = new Node(f);
		if (head == null) {
			head = n;
			curr = n;
		} else {
			curr.next = n;
			curr = n;

		}
		size++;
	}

	public int size() {
		return size;
	}

	public Function get(int index) {
	        Node n = head;
	        int i=0;
	        while(i<index && n!=null) {
	            n = n.next;
	            i++;
	        }
	        return n.data;
	    
	}

	public void remove(int index) {
		if(index==0) {
			if (size!=1)
				head = head.next;
			else {
				head = null;
				curr = null;
			}
	    }
		else if (index==size-1) {
			Node n = head;
			for (int i =0; i<index-1; i++) {
				n = n.next;
			}
			curr = n;
			curr.next=null;
		}
		else {
	        Node n = head;
	        for (int i =0; i<index-1; i++) {
	        	n= n.next;
	        }
	        n.next= n.next.next;
		}
		size--;
	}

	public void display() {
		Node current = head;
		while (current != null) {
			System.out.println(current.data);
			current = current.next;
		}
		System.out.println();
	}
}

