
public class Event implements Comparable<Event> {
	public int start;
	public int end;
	
	public Event(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public int compareTo(Event e) {
		if (this.start < e.start || (this.start == e.start && this.end < e.end)) {
			return -1;
		} else if (this.start == e.start && this.end == e.end) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public boolean intersect(Event e) {
		return (this.start >= e.start && this.start <= e.end) || (this.end >= e.start && this.end <= e.end);
	}
	
	public String toString() {
		return "(" + start + "," + end + ")";
	}
}
