package com.dnake.kit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanKit {

	private static final Map<Class, List<String>> CLASS_FIELDS_CACHE = new ConcurrentHashMap<>();

	private static boolean loadFieldsByReflect(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<String> list = new ArrayList<>(fields.length);
		for (Field field : fields) {
			list.add(field.getName());
		}
		CLASS_FIELDS_CACHE.put(clazz, list);
		return true;
	}

	public static List<String> getFieldList(Class clazz) {
		List<String> list = CLASS_FIELDS_CACHE.get(clazz);
		if (list == null) {
			loadFieldsByReflect(clazz);
		}
		return Collections.unmodifiableList(CLASS_FIELDS_CACHE.get(clazz));
	}

	public static String getFields(Class<?> clazz) {
		String str = Arrays.toString(getFieldList(clazz).toArray());
		return str.substring(1, str.length() - 1);
	}

	public static Object getFieldValue(Object object, String field) {
		try {
			String getter = getter(field, false);
			Method method = object.getClass().getMethod(getter);
			return method.invoke(object);
		} catch (Exception e) {
			throw new RuntimeException("can't get the value in " + field + " by " + object);
		}
	}

	public static boolean setFieldValue(Object object, String field, Object value) {
		//TODO
		return false;
	}

	private static String getter(String field, boolean boole) {
		String str = StringKit.firstToUpper(field);
		return (boole ? "is" : "get") + str;
	}

	private static String setter(String field) {
		return "set" + StringKit.firstToUpper(field);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericType(Class<?> clazz, int index) {
		if (clazz == null || index < 0) {
			return null;
		}
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return null;
		}

		Type[] types = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= types.length) {
			return null;
		}
		Type type = types[index];

		return type instanceof Class ? (Class<T>) type : null;
	}

	public static <T> Class<T> getGenericType(Class<?> clazz) {
		return getGenericType(clazz, 0);
	}

	public static boolean hasPersistent(Object value) {
		if (value == null) {
			return false;
		}

		Class clazz = value.getClass();
		if (clazz == String.class) {
			return ((String) value).trim().length() > 0;
		}
		if (clazz == int.class || clazz == Integer.class) {
			return (Integer) value > 0;
		}
		return (clazz == long.class || clazz == Long.class) && (Long) value > 0;
	}
}
