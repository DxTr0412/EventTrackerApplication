package app.keepworks.base.activity.main;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import app.keepworks.base.BaseDefaultActivity;
import app.keepworks.eventtracker.R;

public class MainScreenAcitivity extends BaseDefaultActivity {

	private MainScreenHandler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getActionBar().hide();

		mHandler = new MainScreenHandler(this);
		mHandler.loadEventsFromAssets();

		((Button) findViewById(R.id.bContinue)).setOnClickListener(mHandler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.findItem(R.id.trackEvents).setVisible(false);
		return true;
	}

	@Override
	public void initDataFromIntent() {
	}

}
