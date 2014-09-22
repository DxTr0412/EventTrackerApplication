package app.keepworks.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class Util {

	public static String getJsonStringFromAssets(Context context, String path) {

		StringBuilder buf = new StringBuilder();
		InputStream json;
		String str = null;
		try {
			json = context.getAssets().open(path);
			BufferedReader in = new BufferedReader(new InputStreamReader(json,
					"UTF-8"));

			while ((str = in.readLine()) != null) {
				buf.append(str);
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

}
