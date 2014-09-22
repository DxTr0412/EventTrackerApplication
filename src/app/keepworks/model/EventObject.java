package app.keepworks.model;

import java.util.List;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import app.keepworks.base.BaseResponseObject;
import app.keepworks.helper.DatabaseHelper;
import app.keepworks.util.DBUtil;
import app.keepworks.util.StringUtil;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class EventObject extends BaseResponseObject {

	@DatabaseField(generatedId = true, unique = true)
	private int id;

	@DatabaseField(uniqueIndexName = "nameplaceunique")
	@SerializedName("name")
	private String name;

	@DatabaseField(uniqueIndexName = "nameplaceunique")
	@SerializedName("entry_type")
	private String entryType;

	@DatabaseField(uniqueIndexName = "nameplaceunique")
	@SerializedName("place")
	private String place;

	@DatabaseField
	@SerializedName("thumbnail")
	private String thumbnailUrl;

	@DatabaseField
	@SerializedName("event_image")
	private String eventImage;

	public EventObject(Parcel in) {
		id = in.readInt();
		name = in.readString();
		entryType = in.readString();
		place = in.readString();
		thumbnailUrl = in.readString();
		eventImage = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(entryType);
		dest.writeString(place);
		dest.writeString(thumbnailUrl);
		dest.writeString(eventImage);
	}

	public static final Parcelable.Creator<EventObject> CREATOR = new Parcelable.Creator<EventObject>() {
		public EventObject createFromParcel(Parcel in) {
			return new EventObject(in);
		}

		public EventObject[] newArray(int size) {
			return new EventObject[size];
		}
	};

	public EventObject(String eventName, String entryType, String eventPlace,
			String thumbnail) {
		this.name = eventName;
		this.place = eventPlace;
		this.entryType = entryType;
		this.thumbnailUrl = thumbnail;
	}

	public int getEventId() {
		return id;
	}

	public String getEventName() {
		if (StringUtil.isNullOrEmpty(name)) {
			return "N/A";
		}
		return name;
	}

	public String getEventEntryType() {
		if (StringUtil.isNullOrEmpty(entryType)) {
			return "N/A";
		}
		return entryType;
	}

	public String getEventPlace() {
		if (StringUtil.isNullOrEmpty(place)) {
			return "N/A";
		}
		return place;
	}

	public String getEventThumbnail() {
		if (StringUtil.isNullOrEmpty(thumbnailUrl)) {
			return "";
		}
		return thumbnailUrl;
	}

	public String getEventImage() {
		if (StringUtil.isNullOrEmpty(eventImage)) {
			return "";
		}
		return eventImage;
	}

	@SuppressWarnings("unchecked")
	public static DBUtil<EventObject> getEventDBUtil(Context context) {
		return DatabaseHelper.getDBUtil(context, EventObject.class);
	}

	public void save(Context context) {
		getEventDBUtil(context).add(this);
	}

	public static List<EventObject> getUsersEvents(Context context, int userId) {
		return getEventDBUtil(context).fetch(
				0,
				1000,
				null,
				"id in (" + UsersEventsObject.getUsersEventIds(context, userId)
						+ ")");

	}

	public static List<EventObject> getAllEvents(Context context, int userId,
			int offset, int limit) {
		return getEventDBUtil(context).fetch(offset, limit, null, "1=1");
	}

	public EventObject() {
	}
}
