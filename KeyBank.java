import java.util.ArrayList;

public class KeyBank {
	public ArrayList<String> keys = new ArrayList<String>();
	private int count=0;
	public KeyBank() {
		
	}

	public void add(String string) {
		this.keys.add(string);
		count++;
	}
	public void remove(String string) {
		this.keys.remove(string);
		count--;
	}
	public boolean contains(String string) {
		return this.keys.contains(string);
	}
	public void clear() {
		this.keys.clear();
	}
}