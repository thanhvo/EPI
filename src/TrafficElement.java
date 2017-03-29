public class TrafficElement implements Comparable<TrafficElement>{
	public int time, volume;
	
	public TrafficElement(int __time, int __volume) {
		time = __time;
		volume = __volume;
	}
	
	public int compareTo(TrafficElement that) {
		if (volume > that.volume)
			return 1;
		else if (volume == that.volume)
			return 0;
		else 
			return -1;		
	}		
}
