package sak.simplewebapi;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class MyParserBase {

    protected static final int LEVEL_ROOT = 0;
    protected static final String TAG_NONE = "none";
    
    private HashMap<String, MyParserListener> listeners = 
    							new HashMap<String, MyParserListener>();
	private ArrayList<Item> items = new ArrayList<Item>();

	protected Item item = null;			// for work
    protected int level = LEVEL_ROOT;	// for work
    
    protected XmlPullParser parser = null;
    
	public ArrayList<Item> getItems() { return items; }
	
	protected void addItem(Item item) { 
		items.add(item); 
	}
	
    protected void addListener(String tag, MyParserListener l) {
		listeners.put(tag, l);	
    }
    
    public abstract class Item {
    	protected HashMap<String, String> values;
    	
    	public Item() {
    		values = new HashMap<String, String>();
    	}
    	
    	public void put(String key, String val) { 
    		if (values.get(key) != null)
    			return;
    		values.put(key, val); 
    	}

    	public String get(String key) { return values.get(key); }
    }
    
    public abstract class MyParserListener {
		public void startTag(String tag) {}
    	public void endTag(String tag) {}
    	public void text(String tag, String text) {};
    }

	public void parse(String url) throws MyParserException {
		parse(url, "utf-8");
	}
	
	public void parse(String url, String charset) throws MyParserException {

        try {
			String str = MyParserUtils.getHtmlString(url, charset);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        parser = factory.newPullParser();

	        parser.setInput( new StringReader ( str ) );
	        int eventType = parser.getEventType();
	        
	        // initialize
	        level = LEVEL_ROOT;
	        item = null;
	        String tag = TAG_NONE;
	        
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	
	        	if (eventType == XmlPullParser.START_TAG) {
	        		String key = parser.getName();
	        		tag = key;
	        		MyParserListener l = listeners.get(key);
	        		if (l != null) {
	        			l.startTag(key);
	        		}
	        	} 
	        	else if(eventType == XmlPullParser.END_TAG) {
	        		String key = parser.getName();
	        		MyParserListener l = listeners.get(key);
	        		if (l != null) {
	        			l.endTag(key);
	        		}
	        	} 
	        	else if (eventType == XmlPullParser.TEXT) {
	        		MyParserListener l = listeners.get(tag);
	        		if (l != null) {
	        			l.text(tag, parser.getText());
	        		}
	        	}
	        	eventType = parser.next();
	        }
	 
		} catch (XmlPullParserException e) {
			throw new MyParserException("XmlPullParserException in parse");
		} catch (IOException e) {
			throw new MyParserException("IOException in parse");
		}
    }
}
