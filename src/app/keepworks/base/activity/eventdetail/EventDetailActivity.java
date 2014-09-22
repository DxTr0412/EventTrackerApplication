package app.keepworks.base.activity.eventdetail;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.keepworks.base.BaseDefaultActivity;
import app.keepworks.eventtracker.R;
import app.keepworks.helper.Constants;
import app.keepworks.helper.UserInformation;
import app.keepworks.model.EventObject;
import app.keepworks.model.UsersEventsObject;
import app.keepworks.util.UiUtillities;

public class EventDetailActivity extends BaseDefaultActivity {

	private EventObject eo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		initDataFromIntent();
		getActionBar().setTitle(eo.getEventName());
		populateEventDetails();
	}

	private void populateEventDetails() {
		TextView tvEventName = (TextView) findViewById(R.id.tvEventName);
		tvEventName.setText(eo.getEventName());
		TextView tvEventPlace = (TextView) findViewById(R.id.tvEventPlace);
		tvEventPlace.setText(eo.getEventPlace());
		TextView tvEntryType = (TextView) findViewById(R.id.tvEntryType);
		tvEntryType.setText(eo.getEventEntryType());
		ImageView ivEventImage = (ImageView) findViewById(R.id.ivEventImage);

		/* replace it with event_image */
		UiUtillities.setBackGround(this, ivEventImage, R.drawable.events_1);

		((Button) findViewById(R.id.bTrackEvent))
				.setOnClickListener(trackEventClickListener);
	}

	OnClickListener trackEventClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			UsersEventsObject ueo = new UsersEventsObject(
					UserInformation.getUserId(v.getContext()), eo.getEventId());
			ueo.save(v.getContext());
			UiUtillities.showToast(v.getContext(),
					"Event, \"" + eo.getEventName()
							+ "\", has been saved in your track list!",
					Toast.LENGTH_SHORT);
		}
	};

	@Override
	public void initDataFromIntent() {
		eo = (EventObject) getIntent().getExtras().get(Constants.EVENT_OBJECT);
		if (eo == null) {
			UiUtillities.showToast(this, "Event details not available!",
					Toast.LENGTH_SHORT);
			finish();
		}
	}

}
