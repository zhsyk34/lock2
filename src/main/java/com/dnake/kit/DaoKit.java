package com.dnake.kit;

public class DaoKit {

	private static final Match DEFAULT = String::equals;
	private static final Match SCAN = (ref, str) -> str.startsWith(ref);

	private static int findIndex(StackTraceElement[] elements, String name, Match match, boolean desc) {
		String className;
		if (desc) {
			for (int i = elements.length - 1; i > -1; i--) {
				className = elements[i].getClassName();
				if (match.test(name, className)) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < elements.length; i++) {
				className = elements[i].getClassName();
				if (match.test(name, className)) {
					return i;
				}
			}
		}
		return -1;
	}

	private static String getInterface(String className, int index) {
		if (index < 0 || ValidateKit.empty(className)) {
			return null;
		}
		try {
			return Class.forName(className).getInterfaces()[index].getName();
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private static String namespace(String className, String methodName, boolean isFace) {
		if (ValidateKit.empty(className) || ValidateKit.empty(methodName)) {
			return null;
		}
		if (isFace) {
			className = getInterface(className, 0);
		}
		return className == null ? null : className + "." + methodName;
	}

	public static String statement(String name) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		int i = findIndex(elements, name, SCAN, true);
		return i == -1 ? null : namespace(elements[i].getClassName(), elements[i].getMethodName(), true);
	}

	public static String methodCaller(String name) {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		int i = findIndex(elements, name, SCAN, false);
		return i < 0 ? null : elements[i].getMethodName();
	}

	private interface Match {
		boolean test(String ref, String str);
	}

}
