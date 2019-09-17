package net.dgg.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @description 加密解密工具类
 * @author Ma Hong
 * @date 2018-09-11 17:11
 * @since 1.0
 */
public class DggCryptUtil {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * Base64编码
	 * 
	 * @param text 明文
	 * @return Base64编码后的字符串
	 */
	public static String encodeBase64(String text) {
		return Base64.encodeBase64String(text.getBytes());
	}

	/**
	 * Base64解码
	 * 
	 * @param text Base64编码后的字符串
	 * @return 明文字符串
	 */
	public static String decodeBase64(String text) {
		return new String(Base64.decodeBase64(text));
	}

	/**
	 * MD5加密
	 * 
	 * @param text 明文
	 * @return MD5密文字符串
	 */
	public static String md5(String text) {
		return DigestUtils.md5Hex(text);
	}

	/**
	 * AES加密
	 * 
	 * @param content 明文
	 * @param key     密钥
	 * @return 密文
	 */
	public static String encryptAES(String content, String key) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			byte[] bt = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
			byte[] result = cipher.doFinal(bt);
			return Base64.encodeBase64String(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 * 
	 * @param content 密文
	 * @param key     密钥
	 * @return 明文
	 */
	public static String decryptAES(String content, String key) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
			byte[] result = cipher.doFinal(Base64.decodeBase64(content));
			return new String(result, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static SecretKeySpec getSecretKey(final String password) throws NoSuchAlgorithmException {
		KeyGenerator key = KeyGenerator.getInstance(KEY_ALGORITHM);
		key.init(128, new SecureRandom(password.getBytes()));
		SecretKey secretKey = key.generateKey();
		return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
	}

}