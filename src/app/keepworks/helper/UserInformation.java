package app.keepworks.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserInformation {

	public static int getUserId(Context context) {
		return getPreference(context).getInt(Constants.USER_ID, -1);
	}

	public static String getUserName(Context context) {
		return getPreference(context).getString(Constants.USER_NAME, "N/A");
	}

	public static void saveToPreferences(Context context, String key, int value) {
		SharedPreferences.Editor editor = getPreference(context).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void saveToPreferences(Context context, String key,
			String value) {
		SharedPreferences.Editor editor = getPreference(context).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void saveToPreferences(Context context, String key, long value) {
		SharedPreferences.Editor editor = getPreference(context).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void saveToPreferences(Context context, String key,
			boolean value) {
		SharedPreferences.Editor editor = getPreference(context).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static SharedPreferences getPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}
