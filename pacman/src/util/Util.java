package util;

public class Util {
	
	public final static int ONE_BLOCK = 50;
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
