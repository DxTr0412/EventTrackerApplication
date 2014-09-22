package app.keepworks.base.activity.usersevents;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import app.keepworks.base.activity.events.EventsActivity;
import app.keepworks.eventtracker.R;
import app.keepworks.helper.UserInformation;
import app.keepworks.model.EventObject;
import app.keepworks.model.UsersEventsObject;
import app.keepworks.util.ListUtil;
import app.keepworks.util.UiUtillities;

public class UserEventsActivity extends EventsActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UiUtillities.showToast(this,
				"Long press on an event to remove it from your list.",
				Toast.LENGTH_LONG);
		getActionBar()
				.setTitle(UserInformation.getUserName(this) + "'s Events");

		lvEvents.setOnItemLongClickListener(removeClickListener);
	}

	OnItemLongClickListener removeClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			EventObject eo = (EventObject) parent.getItemAtPosition(position);
			UsersEventsObject.removeEvent(view.getContext(),
					UserInformation.getUserId(view.getContext()),
					eo.getEventId());
			resetSavedList();
			return false;
		}

	};

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.findItem(R.id.trackEvents).setVisible(false);
		return true;

	};

	private void resetSavedList() {
		hideNoEventMessage(false);
		mAdapter.clear();
		eventsList = EventObject.getUsersEvents(this,
				UserInformation.getUserId(this));
		if (ListUtil.isNullOrEmpty(eventsList)) {
			hideNoEventMessage(true);
			return;
		}
		mAdapter.addAll(eventsList);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public List<EventObject> getEventsList() {
		return EventObject
				.getUsersEvents(this, UserInformation.getUserId(this));
	}

	public static void loadUsersEvents(Context context) {
		Intent intent = new Intent(context, UserEventsActivity.class);
		context.startActivity(intent);
	}

}
