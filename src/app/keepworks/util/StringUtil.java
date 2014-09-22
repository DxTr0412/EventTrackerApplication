package app.keepworks.util;

public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null");
	}

	public static String stringFromArray(String[] array, String delimeter) {
		String st = "-1";
		for (String str : array) {
			st += delimeter + str;
		}
		return st;
	}

}
