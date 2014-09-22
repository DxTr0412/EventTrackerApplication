package app.keepworks.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import app.keepworks.eventtracker.R;
import app.keepworks.model.EventObject;
import app.keepworks.model.UserObject;
import app.keepworks.model.UsersEventsObject;
import app.keepworks.util.DBUtil;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static List<Class> tableClasses = new ArrayList<Class>();

	private void initializeTableClasses(Context context) {
		addToTableClasses(EventObject.class);
		addToTableClasses(UserObject.class);
		addToTableClasses(UsersEventsObject.class);
	}

	public static void addToTableClasses(Class cl) {
		try {
			if (!tableClasses.contains(cl)) {
				tableClasses.add(cl);
			}
		} catch (Exception e) {
		}
	}

	public DatabaseHelper(Context context) {
		super(context,
				context.getResources().getString(R.string.DATABASE_NAME), null,
				Integer.parseInt(context.getResources().getString(
						R.string.DATABASE_VERSION)));
		initializeTableClasses(context);

	}

	private Map<Class, Dao> daoMap = new HashMap<Class, Dao>();

	private static Map<Class, DBUtil> dbUtilMap = new HashMap<Class, DBUtil>();

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			for (Class cl : tableClasses) {
				TableUtils.createTableIfNotExists(connectionSource, cl);
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(),
					"Can't create database" + e.toString());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			// Put commands to properly upgrade the tables
			for (Class cl : tableClasses) {
				TableUtils.dropTable(connectionSource, cl, true);
			}

			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(),
					"Can't drop databases" + e.toString());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

	public Dao getDaoFromClass(Class cl) {
		Dao dao = daoMap.get(cl);
		if (dao == null) {
			try {
				dao = getDao(cl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			daoMap.put(cl, dao);
		}
		return dao;
	}

	public void initDBUtils(Context context) {
		for (Class className : tableClasses) {
			getDBUtil(context, className);
		}
	}

	public static DBUtil getDBUtil(Context ctx, Class cl) {
		DBUtil dbUtil = dbUtilMap.get(cl);
		if (dbUtil == null) {
			dbUtil = new DBUtil(ctx, cl);
			dbUtilMap.put(cl, dbUtil);
		}
		return dbUtil;
	}

	public static DBUtil getDBUtil(Class cl) {
		return dbUtilMap.get(cl);
	}

	private void addColumns(String columnName, Class className, String type) {
		Dao dao = getDaoFromClass(className);
		try {
			dao.executeRaw("ALTER TABLE '" + className + "' ADD COLUMN "
					+ columnName + " " + type + ";");
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't alter " + e.toString());
		}
	}
}
