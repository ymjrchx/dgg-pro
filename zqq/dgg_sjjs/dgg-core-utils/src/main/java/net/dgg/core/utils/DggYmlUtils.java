package net.dgg.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class DggYmlUtils {

	public static <T> T load(String filename, Class<T> clazz) {
		InputStream in = null;
		try {
			in = Files.newInputStream(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return load(in, clazz);
	}

	public static <T> T load(InputStream in, Class<T> clazz) {
		Yaml yaml = new Yaml(new Constructor(clazz));
		return yaml.loadAs(in, clazz);
	}

	@SuppressWarnings("rawtypes")
	public static Map load(String filename) {
		Yaml yaml = new Yaml();
		InputStream in = null;
		try {
			in = Files.newInputStream(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterable<Object> iterable = yaml.loadAll(in);
		return (Map) iterable.iterator().next();
	}

	@SuppressWarnings("rawtypes")
	public static Map load(InputStream in) {
		Yaml yaml = new Yaml();
		Iterable iterable = yaml.loadAll(in);
		return (Map) iterable.iterator().next();
	}

}
