/**
 * 
 */
package test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import com.sun.jmx.snmp.EnumRowStatus;



/**
 * @author ZhangBin
 * 
 */
public class Test {
	
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
