package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Hex;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class LibFx {

	private static final String SIP_M = "M fetion.com.cn SIP-C/2.0";
	private static final String SIP_S = "S fetion.com.cn SIP-C/2.0";
	private static String URL_SD = "http://221.176.31.42/ht/sd.aspx";
	private static String URL_LOGIN = "https://uid.fetion.com.cn/ssiportal/SSIAppSignIn.aspx";
	private static String URL_CONFIG = "http://nav.fetion.com.cn/nav/getsystemconfig.aspx";

	private int call, seq;
	private String username, password;
	private String ssic, sid, guid, domain, uri;

	private HashMap<String, BuddyInfo> allBuddys = new HashMap<String, BuddyInfo>();

	private class resResult {
		int status;
		Map<String, List<String>> header;
		String response;
	}

	public class BuddyInfo {
		String uri;
		String mobile;
		String localname;
		String nickname;
		String buddytype;
		String displayname;
	}

	public LibFx(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void getSystemConfig() {
		String data = "<config><user mobile-no=\"" + username;
		data += "\" /><client type=\"PC\" version=\"3.2.0540\" platform=\"W5.1\" />";
		data += "<servers version=\"0\" /><service-no version=\"0\" /><parameters version=\"0\" />";
		data += "<hints version=\"0\" /><http-applications version=\"0\" /></config>";

		resResult res = httpRequest(URL_CONFIG, data, null);

		try {
			String[] resSplit = res.response.split("/r/n");
			String xmlData = resSplit[0];

			Document xml = DocumentHelper.parseText(xmlData);

			Element httptunnel = (Element) xml.selectObject("/config/servers/http-tunnel");
			URL_SD = httptunnel.getStringValue();

			Element ssiappsignin = (Element) xml.selectObject("/config/servers/ssi-app-sign-in");
			URL_LOGIN = ssiappsignin.getStringValue();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	} 
	
	// [start] 登录,注册到服务器,退出
	public String Login() {
		String debugMsg = "";
		try {
			String data = "mobileno=" + username + "&pwd=" + password + "";
			resResult res = httpRequest(URL_LOGIN, data, null);

			if (res == null) {
				debugMsg = "login error";
				return debugMsg;
			}
			String header = res.header.values().toString();
			Pattern ssicPattern = Pattern.compile("ssic=(.*?);");
			Matcher matcher = ssicPattern.matcher(header);

			if (matcher.find()) {
				ssic = matcher.group(1);
			}

			Document document = DocumentHelper.parseText(res.response);
			Element root = document.getRootElement();
			Element user = root.element("user");
			// 示例:<user uri="sip:857179488@fetion.com.cn;p=6006" mobile-no="15910510159" user-status="101" user-id="607221518">
			uri = user.attribute("uri").getValue();
			int a = uri.indexOf("@");
			int b = uri.indexOf(";");

			sid = uri.substring(4, a);
			domain = uri.substring(a + 1, b);

		} catch (Exception ex) {
			debugMsg += ex.getMessage();
			return debugMsg;
		}

		return "OK" + debugMsg;
	}

	public String Register() {
		String debugMsg = "";

		String arg = "<args><device type=\"PC\" version=\"2009112603\" client-version=\"3.5.2540\" />";
		arg += "<caps value=\"simple-im;im-session;temp-group;personal-group;im-relay;xeno-im;direct-sms;sms2fetion\" />";
		arg += "<events value=\"contact;permission;system-message;personal-group;compact\" /><user-info attributes=\"all\" />";
		arg += "<presence><basic value=\"400\" desc=\"\" /></presence></args>";

		String response = null, nonce = null;

		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "1 R");
		String msg = createSipData("R fetion.com.cn SIP-C/2.0", fields, arg) + "SIPP";
		// 发送请求
		response = sendRequest(nextUrl("i"), msg).response;
		response = sendRequest(nextUrl("s"), "SIPP").response;

		Pattern noncePattern = Pattern.compile("nonce=\"(//w+)\"");
		Matcher m = noncePattern.matcher(response);
		if (m.find()) {
			nonce = m.group(1);
		} else {
			return "not find noce!" + response;
		}

		String salt = "777A6D03";
		String cnonce = calc_cnonce();
		String calcResponse = calc_response(nonce, cnonce);
		String strA = "Digest algorithm=\"SHA1-sess\",response=\"" + calcResponse + "\",";
		strA += "cnonce=\"" + cnonce + "\",salt=\"" + salt + "\",ssic=\"" + ssic + "\"";

		fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "2 R");
		fields.put("A", strA);
		msg = createSipData("R fetion.com.cn SIP-C/2.0", fields, arg) + "SIPP";
		// 发送请求
		response = sendRequest(nextUrl("s"), msg).response;
		response = sendRequest(nextUrl("s"), "SIPP").response;
		if (response == null) {
			return "not 2R";
		}

		return "OK" + debugMsg;
	}

	public boolean logout() {
		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", "1");
		fields.put("Q", "2 R");
		fields.put("X", "0");
		String msg = createSipData("R fetion.com.cn SIP-C/2.0", fields, null) + "SIPP";

		String response = sendRequest(nextUrl("s"), msg).response;
		System.out.println("退出:" + response);
		if (response == null || response.equals("")) {
			return false;
		}
		return true;
	}
	// [end]

	// [start] 相关算法
	private String nextCall() {
		call += 1;
		return String.valueOf(call);
	}

	private String nextUrl(String t) {
		seq++;
		System.out.println(URL_SD + "?t=" + t + "&i=" + seq);

		return new StringBuffer(URL_SD).append("?t=").append(t).append("&i=").append(seq).toString();
	}

	private String calc_cnonce() {
		MessageDigest md5 = null;
		String md5Str = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(UUID.randomUUID().toString().getBytes("UTF-8"));
			md5Str = new String(Hex.encodeHex(md5.digest())).toUpperCase();
		} catch (Exception e) {
			return null;
		}
		return md5Str;
	}

	private String calc_response(String nonce, String cnonce) {

		String temp = ":";
		String strTemp = "REGISTER";
		char[] saltChars = { 0x77, 0x7A, 0x6D, 0x03 };
		String salt = String.valueOf(saltChars);

		String h3 = null;
		try {
			int sidLength = sid.getBytes("UTF-8").length;
			int domainLength = domain.getBytes("UTF-8").length;
			int nonceLength = nonce.getBytes("UTF-8").length;
			int tempLength = temp.getBytes("UTF-8").length;
			int strTempLength = strTemp.getBytes("UTF-8").length;
			int cnonceLength = cnonce.getBytes("UTF-8").length;

			ByteBuffer buf = null;

			// 晕,这部有点多余可以省略,就是把原来加密过的字符串转成字节
			// byte[] decode = Hex.decodeHex(binstr.toUpperCase().toCharArray());

			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

			// 密码使用SHA-1加密
			sha1 = MessageDigest.getInstance("SHA-1");

			sha1.update(password.getBytes("UTF-8"));
			byte[] src = sha1.digest();
			// 加密后把{ 0x77, 0x7A, 0x6D, 0x03 }这四个字符加上用SHA-1加密过的密码
			buf = ByteBuffer.allocate(src.length + salt.getBytes("UTF-8").length);
			buf.put(salt.getBytes("UTF-8"));
			buf.put(src);
			buf.flip();
			sha1.update(buf);
			buf.clear();
			// 然后再把相加过的`字节数组`用SHA-1加密
			byte[] decode = sha1.digest();

			// 用SHA-1加密```形式如 sid:domain:decode
			buf = ByteBuffer.allocate(sidLength + domainLength + decode.length + (tempLength * 2));
			buf.put(sid.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(domain.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(decode);
			buf.flip();
			sha1.update(buf);

			byte[] keyBytes = sha1.digest();
			// 用MD5加密```形式如 key:nonce:cnonce 上面生成的就是key 生成h1
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			buf = ByteBuffer.allocate(keyBytes.length + nonceLength + cnonceLength + (tempLength * 2));
			buf.put(keyBytes);
			buf.put(temp.getBytes("UTF-8"));
			buf.put(nonce.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(cnonce.getBytes("UTF-8"));
			buf.flip();
			md5.update(buf);
			buf.clear();

			String h1 = new String(Hex.encodeHex(md5.digest())).toUpperCase();

			// 用MD5加密```形式如 REGISTER:sid 生成h2
			buf = ByteBuffer.allocate(sidLength + strTempLength + tempLength);
			buf.put(strTemp.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(sid.getBytes("UTF-8"));
			buf.flip();
			md5.update(buf);
			buf.clear();

			String h2 = new String(Hex.encodeHex(md5.digest())).toUpperCase();

			// 用MD5加密```形式如 h1:nonce:h2 生成h2
			buf = ByteBuffer.allocate(h1.getBytes("UTF-8").length + nonceLength + h2.getBytes("UTF-8").length + (tempLength * 2));
			buf.put(h1.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(nonce.getBytes("UTF-8"));
			buf.put(temp.getBytes("UTF-8"));
			buf.put(h2.getBytes("UTF-8"));
			buf.flip();
			md5.update(buf);
			buf.clear();

			h3 = new String(Hex.encodeHex(md5.digest())).toUpperCase();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return h3;

	}

	private String createSipData(String invite, HashMap<String, String> fields, String arg) {
		StringBuffer sip = new StringBuffer(invite).append("/r/n");
		for (Map.Entry<String, String> entry : fields.entrySet()) {
			sip.append(entry.getKey()).append(": ").append(entry.getValue()).append("/r/n");
		}
		if (arg != null) {
			try {
				sip.append("L: ").append(arg.getBytes("UTF-8").length).append("/r/n/r/n").append(arg);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}

		return sip.toString();
	}
	// [end]

	// [start] http请求
	private resResult httpRequest(String strUrl, String data, Map<String, String> heads) {
		resResult res = new resResult();

		URL url = null;
		HttpURLConnection conn = null;
		for (int i = 0; i < 10; i++) {// 如果请求失败,则重试5次
			try {
				url = new URL(strUrl);
				conn = (HttpURLConnection) url.openConnection();

				conn.setDoOutput(true);// 设置输出
				conn.setUseCaches(false);// 不使用缓存
				conn.setRequestMethod("POST");

				// 循环设置请求头部,比如cookie
				if (heads != null) {
					for (Entry<String, String> entry : heads.entrySet())
						conn.setRequestProperty(entry.getKey(), entry.getValue());
				}

				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(data);// 写入请求(POST)数据
				out.flush();
				out.close();

				InputStream in = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
				String line = "", response = "";
				while ((line = br.readLine()) != null) {
					response += line + "/r/n";
				}

				Map<String, List<String>> resHeads = conn.getHeaderFields();

				res.status = conn.getResponseCode();
				System.out.println("conn_code:" + res.status);

				res.response = response;
				res.header = resHeads;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (conn != null)
					conn.disconnect();
			}

			if (res.status == 200) {
				break;// 重试若干次,当返回200则跳出
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		return res;
	}

	private resResult sendRequest(String strUrl, String data) {
		if (guid == null) {
			guid = UUID.randomUUID().toString();
		}
		resResult res = new resResult();
		try {

			Map<String, String> cookies = new HashMap<String, String>();

			cookies.put("User-Agent", "IIC2.0/PC 3.5.2540");
			cookies.put("Cookie", "ssic=" + ssic);
			cookies.put("Content-Type", "application/oct-stream");
			cookies.put("Pragma", "xz4BBcV" + guid);

			// StringRequestEntity m_data = new StringRequestEntity(data, "text/xml", "utf-8");
			res = httpRequest(strUrl, data, cookies);

		} catch (Exception e) {
			return null;
		}
		return res;
	}
	// [end]

	// /以下是飞信应用API
	public HashMap<String, BuddyInfo> getContactsInfo() {

		allBuddys = getContactsList();

		StringBuffer arg = new StringBuffer("<args><contacts attributes=\"all\">");
		for (Entry<String, BuddyInfo> buddy : allBuddys.entrySet()) {
			arg.append("<contact uri=\"").append(buddy.getKey()).append("\" />");
		}
		arg.append("</contacts></args>");

		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "1 S");
		fields.put("N", "GetContactsInfo");
		String msg = createSipData(SIP_S, fields, arg.toString()) + "SIPP";

		try {
			sendRequest(nextUrl("s"), msg);
			Thread.sleep(2000);
			String response = sendRequest(nextUrl("s"), "SIPP").response;

			String[] resSplit = response.split("/r/n/r/n");
			String xmlData = resSplit[1].replace("</results>SIPP", "</results>");

			Document xml = DocumentHelper.parseText(xmlData);

			List contacts = xml.selectNodes("/results/contacts/contact");
			Iterator itc = contacts.iterator();
			while (itc.hasNext()) {
				Element elt = (Element) itc.next();
				Attribute uri = elt.attribute("uri");
				BuddyInfo buddy = allBuddys.get(uri.getStringValue());

				Element personal = elt.element("personal");
				Attribute mobile = personal.attribute("mobile-no");
				Attribute nickname = personal.attribute("nickname");

				buddy.mobile = mobile.getStringValue();
				buddy.nickname = nickname.getStringValue();
				buddy.buddytype = "mobile";

				String display = buddy.localname;
				if (display.equals(""))
					display = buddy.nickname;
				if (display.equals(""))
					display = buddy.mobile;
				if (display.equals(""))
					display = "猪头啦";
				buddy.displayname = display;
			}

		} catch (Exception ex) {

		}

		return allBuddys;

	}

	private HashMap<String, BuddyInfo> getContactsList() {
		String arg = "<args><contacts><buddy-lists /><buddies attributes=\"all\" />";
		arg += "<mobile-buddies attributes=\"all\" /><chat-friends /><blacklist /></contacts></args>";

		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "1 S");
		fields.put("N", "GetContactList");
		String msg = createSipData(SIP_S, fields, arg) + "SIPP";

		sendRequest(nextUrl("s"), msg);
		String response = sendRequest(nextUrl("s"), "SIPP").response;

		HashMap<String, BuddyInfo> lstbuddys = new HashMap<String, BuddyInfo>();

		try {
			String[] resSplit = response.split("/r/n/r/n");
			String xmlData = resSplit[1].replace("</results>SIPP", "</results>");

			Document xml = DocumentHelper.parseText(xmlData);

			// 获取飞信好友
			List buddys = xml.selectNodes("/results/contacts/buddies/buddy");
			Iterator itb = buddys.iterator();
			while (itb.hasNext()) {
				Element elt = (Element) itb.next();
				Attribute uri = elt.attribute("uri");
				Attribute localname = elt.attribute("local-name");

				BuddyInfo buddy = new BuddyInfo();
				buddy.uri = uri.getStringValue();
				buddy.localname = localname.getStringValue();
				buddy.buddytype = "buddy";
				lstbuddys.put(buddy.uri, buddy);
			}

			// 获取手机好友
			List mobiles = xml.selectNodes("/results/contacts/mobile-buddies/mobile-buddy");
			Iterator itm = mobiles.iterator();
			while (itm.hasNext()) {
				Element elt = (Element) itm.next();
				Attribute uri = elt.attribute("uri");
				Attribute localname = elt.attribute("local-name");

				BuddyInfo buddy = new BuddyInfo();
				buddy.uri = uri.getStringValue();
				buddy.localname = localname.getStringValue();
				buddy.buddytype = "mobile";
				lstbuddys.put(buddy.uri, buddy);
			}

		} catch (Exception ex) {

		}

		return lstbuddys;
	}

	private String getUri(String mobile) {
		if (allBuddys.entrySet().size() == 0)
			getContactsInfo();
		for (Entry<String, BuddyInfo> entry : allBuddys.entrySet()) {
			if (entry.getValue().mobile.equals(mobile))
				return entry.getKey();
		}

		return null;
	}

	public boolean sendCatSms(String content) {
		return sendCatSms(null, content);
	}

	public boolean sendCatSms(String to, String content) {

		// T试了好像只能是标准的sip地址，不知道有没有什么办法能换成手机号，sip:57358215@fetion.com.cn;p=4009中4009的计算公式：
		// 13开头的手机号
		// p=手机号码前6位 - 134099
		// 15开头的手机号
		// p=手机号码前6位 - 153099

		// String toMobile = buddys.get(to);

		// if (toMobile == null || "".equals(toMobile))
		// return false;

		String sendto = to;
		if (!to.startsWith("sip"))
			sendto = getUri(to);
		if (sendto == null)
			return false;

		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "1 M");
		fields.put("T", to == null ? uri : sendto);
		fields.put("N", "SendCatSMS");
		String msg = createSipData(SIP_M, fields, content) + "SIPP";

		sendRequest(nextUrl("s"), msg);
		String response = sendRequest(nextUrl("s"), "SIPP").response;

		if (response.indexOf("Send SMS OK") > 0)
			return true;
		else
			return false;
	}

	public boolean sendSms(String to, String content) {
		int len = content.length();
		HashMap<String, String> fields = new HashMap<String, String>();
		fields.put("F", sid);
		fields.put("I", nextCall());
		fields.put("Q", "0 M");
		fields.put("T", uri);
		fields.put("C", "text/html-fragment");
		fields.put("K", "SaveHistory");
		fields.put("L", String.valueOf(len));
		String msg = createSipData(SIP_M, fields, content) + "SIPP";

		String response = sendRequest(nextUrl("s"), msg).response;
		response = sendRequest(nextUrl("s"), "SIPP").response;

		if (response.indexOf("Send SMS OK") > 0)
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		LibFx l = new LibFx("13439348224", "yijifen");
		l.sendCatSms("13439348224", "我是外星人。");
	}
}
