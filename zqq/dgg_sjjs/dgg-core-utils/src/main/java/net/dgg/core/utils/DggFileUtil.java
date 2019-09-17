package net.dgg.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @description 文件操作工具类
 * @author Ma Hong
 * @date 2018-09-11 17:43
 * @since 1.0
 */
public class DggFileUtil {

	/**
	 * 判断文件是否存在
	 * 
	 * @param path 文件地址
	 * @return 存在返回 true ，反之。
	 */
	public static boolean exists(String path) {
		return Files.exists(Paths.get(path));
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path 文件地址
	 * @return 不存在返回 true ，反之。
	 */
	public static boolean notExists(String path) {
		return Files.notExists(Paths.get(path));
	}

	/**
	 * 删除文件
	 * 
	 * @param path 文件地址
	 * @return 删除成功返回 true ，反之。
	 */
	public static boolean delete(String path) throws IOException {
		return Files.deleteIfExists(Paths.get(path));
	}

	/**
	 * 创建输入流
	 * 
	 * @param path 文件地址
	 * @return InputStream
	 */
	public static InputStream newInputStream(String path) throws IOException {
		return Files.newInputStream(Paths.get(path));
	}

	/**
	 * 创建输出流
	 * 
	 * @param path 文件地址
	 * @return OutputStream
	 */
	public static OutputStream newOutputStream(String path) throws IOException {
		return Files.newOutputStream(Paths.get(path));
	}

	public static BufferedReader newBufferedReader(String path) throws IOException {
		return Files.newBufferedReader(Paths.get(path));
	}

	/**
	 * 判断文件目录是否存在
	 * 
	 * @param path 文件地址
	 * @return 存在返回 true ，反之。
	 */
	public static boolean isDirectory(String path) {
		return Files.isDirectory(Paths.get(path));
	}

	/**
	 * 复制文件
	 * 
	 * @param source 源文件地址
	 * @param target 目标文件地址
	 * @throws IOException
	 */
	public static void copy(String source, String target) throws IOException {
		Files.copy(Paths.get(source), Paths.get(target));
	}

	/**
	 * 移动文件
	 * 
	 * @param source 源文件地址
	 * @param target 目标文件地址
	 * @throws IOException
	 */
	public static void move(String source, String target) throws IOException {
		Files.move(Paths.get(source), Paths.get(target));
	}

	public static byte[] readAllBytes(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	public static List<String> readAllLines(String path) throws IOException {
		return Files.readAllLines(Paths.get(path));
	}

	/**
	 * 创建文件
	 * 
	 * @param path 需要创建的文件地址
	 * @return 创建的文件
	 * @throws IOException
	 */
	public static File createFile(String path) throws IOException {
		Path p = Files.createFile(Paths.get(path));
		return p.toFile();
	}

	public static String createDirectories(String path) throws IOException {
		Files.createDirectories(Paths.get(path));
		return path;
	}

	/**
	 * 创建文件目录
	 * 
	 * @param path 文件地址
	 * @throws IOException
	 */
	public static String createDirectory(String path) throws IOException {
		Files.createDirectory(Paths.get(path));
		return path;
	}

	public static String getSizeStr(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String sizeStr = "0";
		if (size < 1024) {
			sizeStr = df.format((double) size) + "B";
		} else if (size < 1048576) {
			sizeStr = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			sizeStr = df.format((double) size / 1048576) + "M";
		} else {
			sizeStr = df.format((double) size / 1073741824) + "G";
		}
		return sizeStr;
	}

}
