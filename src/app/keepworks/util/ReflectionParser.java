package app.keepworks.util;

import android.util.Log;
import app.keepworks.base.BaseResponseObject;

import com.google.gson.Gson;

public class ReflectionParser<T extends BaseResponseObject> {

	public T getResponseObject(Class<T> cl, String data) {
		try {
			Gson gson = new Gson();
			return (T) gson.fromJson(data, cl);
		} catch (Exception e) {
			Log.i("Exception", e.getMessage().toString());
		}
		return null;
	}
}
