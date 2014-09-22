package app.keepworks.base.activity.allevents;

import java.util.List;

import app.keepworks.base.activity.events.EventsActivity;
import app.keepworks.helper.UserInformation;
import app.keepworks.model.EventObject;

public class AllEventsActivity extends EventsActivity {

	@Override
	public List<EventObject> getEventsList() {
		return EventObject.getAllEvents(this, UserInformation.getUserId(this),
				0, 1000);
	}

}
