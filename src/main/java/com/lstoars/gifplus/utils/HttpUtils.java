package com.lstoars.gifplus.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	private static Logger logger = Logger.getLogger(HttpUtils.class);

	public static String getContent(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(url);
		int statusCode = client.executeMethod(get);
		if (statusCode != 200) {
			logger.error("getListPageId@statusCode is not 200,page:" + url);
			return null;
		}
		String responseContext = get.getResponseBodyAsString();
		return responseContext;
	}
}
