package org.abin.core.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtils {
	
	public static String md5(String text) {
		return DigestUtils.md5Hex(text);
	}
	
	public static String sha(String text) {
		return DigestUtils.shaHex(text);
	}

}
