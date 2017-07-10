
public class EndPoint<TimeType extends Comparable> implements Comparable<EndPoint<TimeType>>{
	public TimeType time;
	public boolean isStart;
	
	@Override
	public int compareTo(EndPoint<TimeType> e) {
		return time != e.time ? (time.compareTo(e.time) < 0 ? -1 : 1) : (isStart && !e.isStart ? -1 : 0);		
	}
	
	public EndPoint(TimeType time, boolean isStart) {
		this.time = time;
		this.isStart = isStart;
	}
}
