package app.keepworks.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

public class UiUtillities {
	public static void showToast(Context context, String message, int length) {
		if (StringUtil.isNullOrEmpty(message)) {
			return;
		}
		try {
			Toast.makeText(context, message, length).show();
		} catch (Exception e) {
		}

	}

	public static void showToast(Context context, int messageId, int length) {
		try {
			Toast.makeText(context, messageId, length).show();
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("deprecation")
	public static void setBackGround(Context context, View view, int background) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			view.setBackgroundDrawable(context.getResources().getDrawable(
					background));
		} else {
			view.setBackground(context.getResources().getDrawable(background));
		}
	}

	@SuppressWarnings("deprecation")
	public static void setBackGround(Context context, View view,
			Drawable drawable) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			view.setBackgroundDrawable(drawable);
		} else {
			view.setBackground(drawable);
		}
	}
}
