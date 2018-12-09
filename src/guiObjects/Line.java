package guiObjects;

public class Line {

	Pixel head;
	Pixel tail;
	
	public Line(Pixel head, Pixel tail) {
		this.head = head;
		this.tail = tail;
	}

	public Pixel getHead() {
		return head;
	}


	public Pixel getTail() {
		return tail;
	}
	
	
}
