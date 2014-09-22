package app.keepworks.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListUtil {

	public static <T> boolean isNullOrEmpty(List<T> l) {
		return l == null || l.size() == 0;
	}

	public static List getList(Object... objList) {
		List list = new ArrayList();
		for (Object obj : objList) {
			if (obj != null) {
				list.add(obj);
			}
		}
		return list;
	}

}
