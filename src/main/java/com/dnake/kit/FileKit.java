package com.dnake.kit;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileKit {

	//.../target/classes/
	public static String targetRoot() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		return url == null ? null : url.toString();
	}

	//...src/main
	public static String originalRoot() {
		String root = targetRoot();
		if (root == null) {
			return null;
		}
		return root.replace("/target/classes/", "/") + "src/main/";
	}

	//get file path
	public static Path getPath(String first, String... more) {
		if (first == null) {
			return null;
		}

		first = first.replaceAll("file:/", "");

		return Paths.get(first, more);
	}

	//create if not exist
	public static boolean createPath(Path path) {
		assert path != null;
		if (Files.exists(path)) {
			return true;
		}
		try {
			Path parent = path.getParent();
			if (parent != null && Files.notExists(parent)) {
				Files.createDirectories(parent);
			}

			Files.createFile(path);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean write(Path path, String str) {
		if (path == null || str == null) {
			return false;
		}
		try {
			Files.write(path, str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<Class<?>> scanPackage(String dir) {
		List<Class<?>> list = new ArrayList<>();

		String path = dir.replace('.', '/');
		URL url = ClassLoader.getSystemClassLoader().getResource(path);
		if (url == null) {
			throw new RuntimeException("No resource for " + path);
		}

		File directory;
		try {
			directory = new File(url.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(dir + " (" + url + ") does not appear to be a valid URL / URI.", e);
		} catch (IllegalArgumentException e) {
			directory = null;
		}

		//class file
		if (directory != null && directory.exists()) {
			String[] files = directory.list();
			if (files != null && files.length > 0) {
				String suffix = ".class";
				for (String file : files) {
					if (file.endsWith(suffix)) {
						String clazz = dir + '.' + file.substring(0, file.length() - suffix.length());
						try {
							list.add(Class.forName(clazz));
						} catch (ClassNotFoundException e) {
							throw new RuntimeException("ClassNotFoundException loading " + clazz);
						}
					}
				}
			}
			return list;
		}

		//jar
		path = url.getFile();
		JarFile jarFile = null;
		try {
			String jarPath = path.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
			jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String entryName = entry.getName();
				if (entryName.startsWith(path) && entryName.length() > (path.length() + "/".length())) {
					String clazz = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
					try {
						list.add(Class.forName(clazz));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("ClassNotFoundException loading " + clazz);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(dir + " (" + directory + ") does not appear to be a valid package", e);
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
