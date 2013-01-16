/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lstoars.gifplus.qplus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author nbzhang
 */
abstract class QPlusService {

	private static final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected int appid;

	protected javax.crypto.Mac mac = null;

	protected QPlusService(int appid, String appsecret) {
		this.appid = appid;
		if (appsecret != null) {
			try {
				mac = javax.crypto.Mac.getInstance("HmacSHA1");
				mac.init(new SecretKeySpec((appsecret).getBytes(), "HmacSHA1"));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	protected String send(final String url, final String param)
			throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded");
		conn.setRequestMethod("GET");
		if (isNotEmpty(param)) {
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", "" + param.length());
			conn.setDoOutput(true);
			conn.getOutputStream().write(param.getBytes());
		}
		conn.setInstanceFollowRedirects(false);
		conn.connect();
		int code = conn.getResponseCode();
		if (code != 200)
			throw new IOException("error http code(" + code + ")");
		String content = new String(read(conn.getInputStream()), "UTF-8");
		conn.disconnect();
		return content;
	}

	protected String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(hex[((b >> 4) & 0xF)]).append(hex[(b & 0xF)]);
		}
		return sb.toString();
	}

	protected byte[] read(InputStream in) throws IOException {
		int pos = -1;
		byte[] buf = new byte[1024 * 8];
		ByteArrayOutputStream out = new ByteArrayOutputStream(256);
		while ((pos = in.read(buf)) != -1) {
			out.write(buf, 0, pos);
		}
		return out.toByteArray();
	}

	protected boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	protected boolean isNotEmpty(CharSequence str) {
		return str != null && str.length() > 0;
	}

	protected String createSigValue(String value) {
		if (mac == null || value == null)
			return null;
		return toHexString(mac.doFinal((value).getBytes()));
	}

	protected String createHttpParam(List<String> list) {
		Collections.sort(list);
		StringBuilder str = new StringBuilder(list.size() * 32);
		for (String param : list) {
			if (str.length() > 0)
				str.append('&');
			str.append(param);
		}
		return str.toString();
	}

	protected String encode(String value) {
		if (value == null)
			return null;
		try {
			return java.net.URLEncoder.encode(value, "UTF-8");
		} catch (IOException ex) {
			return value;
		}
	}

	protected int intNow() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	protected long random() {
		return (long) (Math.random() * System.nanoTime());
	}
}
