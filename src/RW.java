
public class RW {
	public static Object LR, LW; // Read and write locks
	public static int readCount; // Tracks the number of readers
	public static String data;
	public static boolean lastWrite; // The flag indicating that the last operation is a write
}
