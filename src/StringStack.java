
public class StringStack {
	public int maxCapacity=100;
	public String arr[]= new String[maxCapacity];
	public int top = -1;
	public boolean isFull() {
		return top ==maxCapacity-1;
	}
	public boolean isEmpty() {
		return top ==-1;
	}
	public String peek() {
		if (!isEmpty()) {
			return arr[top];
		}
		return null;
		
	}
	public String pop() {
		if (!isEmpty()) {
			String str = arr[top];
			top--;
			return str;
		}
		return null;
		
	}
	public void add(String s) {
		if (!isFull()) {
			top++;
			arr[top]= s;
		}
		
	}
	public void print() {
		for (int i =0; i<=top; i++) {
			System.out.print(arr[i]+" ");
		}
		System.out.println();
	}
	
	public void clear() {
		arr = new String[maxCapacity];
		top = -1;
	}
}

