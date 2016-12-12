public class so {
	public static String prefix;
	public static long sleep;
	static {
		prefix="";
		sleep=0;
	}
	public static void p(Object object) {
		System.out.print("\n"+prefix+": "+object);
	}
}