
public class DoubleStack {
	public int maxCapacity=100;
	public double arr[]= new double[maxCapacity];
	public int top = -1;
	public boolean isFull() {
		return top ==maxCapacity-1;
	}
	public boolean isEmpty() {
		return top ==-1;
	}
	public double peek() {
		if (!isEmpty()) {
			return arr[top];
		}
		return (Double) null;
		
	}
	public double pop() {
		if (!isEmpty()) {
			double x = arr[top];
			top--;
			return x;
		}
		return (Double) null;
		
	}
	public void add(double x) {
		if (!isFull()) {
			top++;
			arr[top]= x;
		}
		
	}
	public int size() {
		return top+1;
	}
}

