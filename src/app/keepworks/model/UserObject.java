package app.keepworks.model;

import java.util.List;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import app.keepworks.base.BaseResponseObject;
import app.keepworks.helper.DatabaseHelper;
import app.keepworks.util.DBUtil;
import app.keepworks.util.ListUtil;
import app.keepworks.util.StringUtil;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class UserObject extends BaseResponseObject {

	@DatabaseField(generatedId = true, unique = true)
	private int id;

	@DatabaseField(unique = true)
	private String userName;

	public UserObject(String userName) {

		this.userName = userName;
	}

	public UserObject(Parcel in) {
		id = in.readInt();
		userName = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(id);
		dest.writeString(userName);
	}

	public static final Parcelable.Creator<UserObject> CREATOR = new Parcelable.Creator<UserObject>() {
		public UserObject createFromParcel(Parcel in) {
			return new UserObject(in);
		}

		public UserObject[] newArray(int size) {
			return new UserObject[size];
		}
	};

	@SuppressWarnings("unchecked")
	public static DBUtil<UserObject> getUserDBUtil(Context activity) {
		return DatabaseHelper.getDBUtil(activity, UserObject.class);
	}

	public void save(Context context) {
		getUserDBUtil(context).add(this);
	}

	public String getUserName() {
		if (StringUtil.isNullOrEmpty(userName)) {
			return "";
		}
		return userName;
	}

	public int getUserId() {
		return id;
	}

	public static boolean isUserExist(String username, Context context) {
		if (getExistingUser(username, context) == null) {
			return false;
		}
		return true;
	}

	public static UserObject getExistingUser(String username, Context context) {
		List<UserObject> usersList = getUserDBUtil(context).fetch(0, 1, null,
				"upper(userName) = \"" + username.toUpperCase() + "\"");
		if (ListUtil.isNullOrEmpty(usersList)) {
			return null;
		}
		return usersList.get(0);
	}

	public UserObject() {
	}

}
