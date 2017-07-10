
public class Interval<TimeType extends Comparable> {
	public TimeType start, finish;
	public Interval(TimeType start, TimeType finish) {
		this.start = start;
		this.finish = finish;
	}
	public String toString() {
		return "(" + start + "," + finish + ")";
	}
}
