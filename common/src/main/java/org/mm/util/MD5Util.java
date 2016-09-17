package org.mm.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;

public class MD5Util {
	public static byte[] md5(String input) {
		try {
			byte[] intputBytes = input.getBytes("utf-8");
			MessageDigest md = MessageDigest.getInstance("md5");
			return md.digest(intputBytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	public static String md5Hex(String input) {
		byte[] md5Byte = md5(input);
		String md5String = Hex.encodeHexString(md5Byte);
		return md5String;
	}

}
