package app.keepworks.util;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import app.keepworks.helper.DatabaseHelper;
import app.keepworks.helper.FPair;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class DBUtil<T> {

	private final static String TAG = DBUtil.class.getSimpleName();

	public DatabaseHelper helper = null;

	public Dao<T, Integer> dao;

	private T cl;

	public DBUtil(Context ctx, Class cl) {
		helper = (DatabaseHelper) OpenHelperManager.getHelper(ctx,
				DatabaseHelper.class);
		this.dao = helper.getDaoFromClass(cl);
	}

	public void add(T obj) {
		try {
			if (obj == null) {
				return;
			}
			dao.create(obj);
		} catch (Exception e) {
			update(obj);
		}
	}

	public void removeAll() {
		String className = dao.getDataClass().getSimpleName();
		String deleteQuery = "DELETE FROM " + className;
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(deleteQuery);
	}

	public void remove(T t) {
		try {
			dao.delete(t);
		} catch (Exception e) {

		}
	}

	public void removeAll(String where) {
		String className = dao.getDataClass().getSimpleName();
		String deleteQuery = "DELETE FROM " + className + " where " + where;
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(deleteQuery);
	}

	public void addAll(List<T> objList) {
		if (objList == null) {
			return;
		}
		for (T obj : objList) {
			add(obj);
		}
	}

	public List<T> all() {
		return fetch(0, 10000, null, null);
	}

	/**
	 * This method is very specific and hardcoded the ordereing, ractify it
	 * later.
	 * 
	 * @param offset
	 * @param limit
	 * @param orderColumn1
	 * @param orderColumn2
	 * @return
	 */
	public List<T> fetch(int offset, int limit, List<FPair> orderColumns,
			String rawWhere) {
		try {
			QueryBuilder queryBuilder = dao.queryBuilder();
			if (orderColumns != null) {
				for (FPair order : orderColumns) {
					queryBuilder.orderBy((String) order.first,
							(Boolean) order.second);
				}
			}

			if (rawWhere != null) {
				queryBuilder.where().raw(rawWhere);
			}
			queryBuilder.limit(limit);
			queryBuilder.offset(offset);
			return dao.query(queryBuilder.prepare());
		} catch (Exception e) {
		}
		return null;
	}

	public int count(String rawWhere) {
		try {
			QueryBuilder queryBuilder = dao.queryBuilder();
			if (rawWhere != null) {
				queryBuilder.where().raw(rawWhere);
			}
			return dao.query(queryBuilder.prepare()).size();
		} catch (Exception e) {
		}
		return 0;
	}

	public void update(T obj) {
		try {
			dao.update(obj);
		} catch (Exception e) {
		}
	}

	public T find(int id) {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public long count() {
		try {
			return dao.countOf();
		} catch (SQLException e) {
		}
		return 0L;
	}

	@SuppressWarnings("unchecked")
	public void callBatchInsertUpdates(final List<T> dataList) {

		new AsyncTask<List<T>, Integer, Long>() {
			@Override
			protected Long doInBackground(List<T>... params) {
				try {
					dao.callBatchTasks(new Callable<Integer>() {
						@Override
						public Integer call() throws Exception {
							for (T data : dataList) {
								add(data);
							}
							return 0;
						}
					});
				} catch (Exception e) {
				}
				return 1L;
			}
		}.execute(dataList);
	}

	public T getMax() {
		List<T> objects = fetch(0, 1, ListUtil.getList(new FPair("id", false)),
				null);
		if (!ListUtil.isNullOrEmpty(objects)) {
			return objects.get(0);
		}
		return null;
	}
}
