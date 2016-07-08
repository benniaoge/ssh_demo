package test.org.abin.core.util;

import org.abin.core.util.EncryptUtils;

public class EncryptTest {
	
	public static void main(String[] args) {
		String s = "1E2DFA89-496A-47FD-9941-DF1FC4E6484A";
		System.out.println(EncryptUtils.md5(s));
		System.out.println("40c7084b4845eebce9d07b8a18a055fc");
	}

}
