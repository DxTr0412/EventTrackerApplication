package app.keepworks.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import app.keepworks.base.activity.usersevents.UserEventsActivity;
import app.keepworks.eventtracker.R;

public abstract class BaseDefaultActivity extends Activity implements
		IBaseAcitivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.app_title);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor(getResources().getString(
						R.color.black))));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.trackEvents) {
			UserEventsActivity.loadUsersEvents(this);
		} else if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
