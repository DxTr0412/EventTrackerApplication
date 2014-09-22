package app.keepworks.base.activity.events;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import app.keepworks.base.BaseDefaultActivity;
import app.keepworks.base.activity.eventdetail.EventDetailActivity;
import app.keepworks.eventtracker.R;
import app.keepworks.helper.Constants;
import app.keepworks.model.EventObject;
import app.keepworks.util.ListUtil;

public class EventsActivity extends BaseDefaultActivity implements
		IEventsActivity {

	protected EventsActivityHandler mHandler;
	protected EventsAdapter mAdapter;
	protected ListView lvEvents;
	protected TextView tvNoEventsAvailable;
	protected List<EventObject> eventsList = new ArrayList<EventObject>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		getActionBar().setTitle(R.string.events_activity_title);

		mHandler = new EventsActivityHandler(this);
		mAdapter = new EventsAdapter(this, R.layout.event_item);

		lvEvents = (ListView) findViewById(R.id.lvEventsList);
		lvEvents.setAdapter(mAdapter);
		lvEvents.setOnItemClickListener(eventClickListener);
		tvNoEventsAvailable = (TextView) findViewById(R.id.tvNoEventsSaved);

		populateEvents();

	}

	private void populateEvents() {

		eventsList = getEventsList();
		hideNoEventMessage(true);
		if (!ListUtil.isNullOrEmpty(eventsList)) {
			hideNoEventMessage(false);
			mAdapter.addAll(eventsList);
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initDataFromIntent() {

	}

	OnItemClickListener eventClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			EventObject eo = (EventObject) parent.getItemAtPosition(position);
			loadEventDetailsActivity(eo);
		}
	};

	private void loadEventDetailsActivity(EventObject eo) {
		Intent intent = new Intent(this, EventDetailActivity.class);
		intent.putExtra(Constants.EVENT_OBJECT, eo);
		startActivity(intent);
	}

	@Override
	public List<EventObject> getEventsList() {
		return null;
	}

	protected void hideNoEventMessage(boolean showMessage) {
		lvEvents.setVisibility(View.VISIBLE);
		tvNoEventsAvailable.setVisibility(View.GONE);
		if (showMessage) {
			lvEvents.setVisibility(View.GONE);
			tvNoEventsAvailable.setVisibility(View.VISIBLE);
		}
	}
}
