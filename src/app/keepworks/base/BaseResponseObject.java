package app.keepworks.base;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseResponseObject implements Parcelable {

	public BaseResponseObject() {

	}

	public BaseResponseObject(Parcel in) {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {

	}

	public long readLong(Parcel in, long defaultValue) {
		try {
			return in.readLong();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public int readInt(Parcel in, int defaultValue) {
		try {
			return in.readInt();
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public boolean readBoolean(Parcel in, boolean val) {
		try {
			return (Boolean) in.readValue(Boolean.class.getClassLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

}
