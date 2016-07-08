/**
 * 
 */
package test.org.abin.core.util;

import java.net.InetAddress;



/**
 * @author ZhangBin
 * 
 */
public class InetAddressTest {
	
	public static void main(String[] args) throws Exception {
		/*
		Enumeration<NetworkInterface> nie = NetworkInterface.getNetworkInterfaces();
		while(nie.hasMoreElements()) {
			NetworkInterface ni = nie.nextElement();
			Enumeration<InetAddress> iae = ni.getInetAddresses();
			while(iae.hasMoreElements()) {
				InetAddress ia = iae.nextElement();
				
			}
		}
		*/
		
		InetAddress ia = InetAddress.getLocalHost();
		System.out.println(ia.getHostAddress());
		
		System.out.println(Integer.MAX_VALUE);
		
		
	}

}
