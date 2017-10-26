
public class SpellTest {
	static private final int TASK_NUM = 10; 
	public static void main(String[] args) throws Exception {
		SpellService.createDictionary();
		for (int i = 0; i < TASK_NUM; i++) {
			SpellCheckClient checker = new SpellCheckClient();
			checker.run();
		}
	}
}
