package app.keepworks.model;

import java.util.List;

import android.content.Context;
import app.keepworks.helper.DatabaseHelper;
import app.keepworks.util.DBUtil;
import app.keepworks.util.ListUtil;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class UsersEventsObject {

	@DatabaseField(generatedId = true, unique = true)
	private int id;

	@DatabaseField(uniqueIndexName = "userToEvent")
	private int userId;

	@DatabaseField(uniqueIndexName = "userToEvent")
	private int eventId;

	@SuppressWarnings("unchecked")
	public static DBUtil<UsersEventsObject> getDBUtil(Context activity) {
		return DatabaseHelper.getDBUtil(activity, UsersEventsObject.class);
	}

	public void save(Context context) {
		getDBUtil(context).add(this);
	}

	public static void removeEvent(Context context, int userId, int eventID) {
		getDBUtil(context).remove(
				getEventfromUserandEvent(context, userId, eventID));
	}

	public static UsersEventsObject getEventfromUserandEvent(Context context,
			int userId, int eventId) {
		List<UsersEventsObject> eventsList = getDBUtil(context).fetch(0, 1,
				null, "userId = " + userId + " and eventId = " + eventId);

		if (ListUtil.isNullOrEmpty(eventsList)) {
			return null;
		}

		return eventsList.get(0);
	}

	public static String getUsersEventIds(Context context, int userID) {
		String eventIds = "-1";
		List<UsersEventsObject> events = getDBUtil(context).fetch(0, 1000,
				null, "userId = " + userID);
		if (ListUtil.isNullOrEmpty(events)) {
			return eventIds;
		}

		for (UsersEventsObject object : events) {
			eventIds += ", " + object.eventId;
		}
		return eventIds;

	}

	public UsersEventsObject(int userId, int eventId) {
		this.userId = userId;
		this.eventId = eventId;
	}

	public UsersEventsObject() {
	}

}
