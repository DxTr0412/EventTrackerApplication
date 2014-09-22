package app.keepworks.base.activity.events;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.keepworks.eventtracker.R;
import app.keepworks.model.EventObject;
import app.keepworks.util.StringUtil;
import app.keepworks.util.UiUtillities;

public class EventsAdapter extends ArrayAdapter<EventObject> {
	private Activity activity;
	LayoutInflater minflater;
	Context mContext;

	public EventsAdapter(Activity activity, int resource) {
		super(activity, resource);
		this.activity = activity;
		minflater = LayoutInflater.from(activity);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		EventObject eo = getItem(position);
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.event_item, null);
			holder = new ViewHolder(convertView, activity, eo);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setFields(eo);
		return convertView;
	}

	static class ViewHolder {
		private ImageView ivThumbnail;
		private TextView tvEventName;
		private TextView tvEventPlace;

		private Activity activity;
		private View convertView;

		public ViewHolder(View convertView, Activity activity, EventObject eo) {
			this.activity = activity;
			this.convertView = convertView;
			this.convertView.setTag(this);

			tvEventName = (TextView) convertView.findViewById(R.id.tvEventName);
			tvEventPlace = (TextView) convertView
					.findViewById(R.id.tvEventPlace);
			ivThumbnail = (ImageView) convertView
					.findViewById(R.id.ivThumbnailEvent);
		}

		public void setFields(EventObject eo) {
			tvEventName.setText(eo.getEventName());
			tvEventPlace.setText(eo.getEventPlace() + " ("
					+ eo.getEventEntryType() + ")");
			UiUtillities.setBackGround(activity, ivThumbnail,
					R.drawable.noimage);
			if (!StringUtil.isNullOrEmpty(eo.getEventThumbnail())) {
				/*
				 * Add image code line depending upon wether it's url or from
				 * drawable
				 */
			}
		}
	}
}
