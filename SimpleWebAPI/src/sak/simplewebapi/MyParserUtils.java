package sak.simplewebapi;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.util.Log;

public class MyParserUtils {
    
	public static HttpEntity getHtmlEntity(String url) throws MyParserException {
		HttpEntity entity = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() != 200) {
				Log.d("RSSParser", "Bad status code. StatusLine: " + statusLine.toString());
				return null;
			}
			entity = response.getEntity();

		} catch (ClientProtocolException e) {
			throw new MyParserException("ClientProtocolException in getHtmlEntity");
		} catch (IOException e) {
			throw new MyParserException("IOException in getHtmlEntity");
		}
		return entity;
	}

	public static String getHtmlString(String url) throws MyParserException {
		return getHtmlString(url, "utf-8");
	}
	
	public static String getHtmlString(String url, String charset) throws MyParserException {
		String str = "";
		try {
			HttpEntity entity = getHtmlEntity(url);
			if (entity != null) {
				str = EntityUtils.toString(entity, charset);
			}
		} catch (IOException e) {
			throw new MyParserException("IOException in getHtmlString");
		}
		return str;
	}
	
	public static InputStream getHtmlInputStream(String url) {
		InputStream in = null;
		try {
			HttpEntity entity = getHtmlEntity(url);
			if (entity != null) {
				in = entity.getContent();
			}
		} catch (IOException e) {
			// Do nothing
			Log.d("MyParser", "IOException in getHtmlInputStream");
		} catch (MyParserException e) {
			// Do nothing
			Log.d("MyParser", "MyParserException in getHtmlInputStream");
		}
		return in;
	}
}
