package app.keepworks.base.activity.main;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import app.keepworks.base.activity.allevents.AllEventsActivity;
import app.keepworks.eventtracker.R;
import app.keepworks.helper.Constants;
import app.keepworks.helper.UserInformation;
import app.keepworks.model.EventListObject;
import app.keepworks.model.EventObject;
import app.keepworks.model.UserObject;
import app.keepworks.util.ReflectionParser;
import app.keepworks.util.StringUtil;
import app.keepworks.util.UiUtillities;
import app.keepworks.util.Util;

public class MainScreenHandler implements OnClickListener {

	private MainScreenAcitivity activity;
	private UserObject user = null;

	public MainScreenHandler(MainScreenAcitivity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		if (isNameValid()) {
			loadEventsActivity();
		}
	}

	private boolean isNameValid() {
		EditText etName = (EditText) activity.findViewById(R.id.etName);
		String userName = etName.getText().toString();
		String toastMessage = activity.getResources().getString(
				R.string.welcome_back);

		if (StringUtil.isNullOrEmpty(userName)) {
			UiUtillities.showToast(activity, R.string.error_empty_name,
					Toast.LENGTH_SHORT);
			return false;
		}

		user = UserObject.getExistingUser(userName, activity);
		if (user == null) {
			toastMessage = activity.getResources().getString(R.string.welcome);
			user = new UserObject(userName);
			user.save(activity);
		}
		saveCurrentUserInformation();

		UiUtillities.showToast(activity,
				toastMessage + ", " + user.getUserName() + "!",
				Toast.LENGTH_LONG);
		return true;
	}

	private void saveCurrentUserInformation() {
		UserInformation.saveToPreferences(activity, Constants.USER_ID,
				user.getUserId());
		UserInformation.saveToPreferences(activity, Constants.USER_NAME,
				user.getUserName());
	}

	private void loadEventsActivity() {
		Intent intent = new Intent(activity, AllEventsActivity.class);
		activity.startActivity(intent);
	}

	public void loadEventsFromAssets() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				String eventsData = Util.getJsonStringFromAssets(activity,
						"event_details.json");
				EventListObject elo = (new ReflectionParser<EventListObject>())
						.getResponseObject(EventListObject.class, eventsData);
				EventObject.getEventDBUtil(activity).callBatchInsertUpdates(
						elo.eventsList);
				return null;
			}
		}.execute();
	}

}
