package app.keepworks.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import app.keepworks.base.BaseResponseObject;
import app.keepworks.util.ListUtil;

import com.google.gson.annotations.SerializedName;

public class EventListObject extends BaseResponseObject {

	@SerializedName("event_list")
	public List<EventObject> eventsList = new ArrayList<EventObject>();

	public EventListObject(Parcel in) {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

	public static final Parcelable.Creator<EventListObject> CREATOR = new Parcelable.Creator<EventListObject>() {
		public EventListObject createFromParcel(Parcel in) {
			return new EventListObject(in);
		}

		public EventListObject[] newArray(int size) {
			return new EventListObject[size];
		}
	};

	public List<EventObject> getEventsList() {
		if (ListUtil.isNullOrEmpty(eventsList)) {
			return null;
		}
		return eventsList;
	}

	public EventListObject(List<EventObject> events) {
		this.eventsList = events;
	}

	public EventListObject() {
	}
}
