package sak.simplewebapi;

import java.util.ArrayList;

/*
<?xml version="1.0" encoding="utf-8" ?>
<ZIP_result>
<result name="ZipSearchXML" />
<result version="1.01" />
<result request_url="http%3A%2F%2Fzip.cgis.biz%2Fxml%2Fzip.php%3Fzn%3D1030000" />
<result request_zip_num="1030000" />
<result request_zip_version="none" />
<result result_code="1" />
<result result_zip_num="1030000" />
<result result_zip_version="0" />
<result result_values_count="1" />
<ADDRESS_value>
<value state_kana="ﾄｳｷｮｳﾄ" />
<value city_kana="ﾁｭｳｵｳｸ" />
<value address_kana="ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ" />
<value company_kana="none" />
<value state="東京都" />

<value city="中央区" />
<value address="以下に掲載がない場合" />
<value company="none" />
</ADDRESS_value>
</ZIP_result>
 */

public class MyParser extends MyParserBase {

	private static final int LEVEL_ZIP_RESULT    = LEVEL_ROOT + 1;
    private static final int LEVEL_ADDRESS_VALUE = LEVEL_ROOT + 2;

    public static final String TAG_ZIP_RESULT    = "ZIP_result";
    public static final String TAG_ADDRESS_VALUE = "ADDRESS_value";
    public static final String TAG_VALUE         = "value";
    
	private static final String ATTR_STATE       = "state";
	private static final String ATTR_CITY        = "city";
	private static final String ATTR_ADDRESS     = "address";

	public MyParser() {
		addListener(TAG_ZIP_RESULT, new ZipResultListener());
		addListener(TAG_ADDRESS_VALUE, new AddressValueListener());
		addListener(TAG_VALUE, new ItemListener());
	}
	
    private class ZipResultListener extends MyParserListener {
		public void startTag(String tag) {
    		level = LEVEL_ZIP_RESULT;
    	}
    	public void endTag(String tag) {
    		level = LEVEL_ROOT;
    	}
    }
    
    private class AddressValueListener extends MyParserListener {
    	public void startTag(String tag) {
    		level = LEVEL_ADDRESS_VALUE;
			item = new MyItem();
    	}
    	public void endTag(String tag) {
    		level = LEVEL_ZIP_RESULT;
			addItem(item);
    	}
    }

    private class ItemListener extends MyParserListener {
    	private ArrayList<String> list = new ArrayList<String>(); 
    	ItemListener() {
    		list.add(ATTR_STATE);
    		list.add(ATTR_CITY);
    		list.add(ATTR_ADDRESS);
    	}
    	public void startTag(String tag) {
			for (String key : list) {
				String str = parser.getAttributeValue(null, key);
				if (str != null && !str.equals("")) {
					item.put(key, parser.getAttributeValue(null, key));
				}
			}
    	}
    }

    private class MyItem extends Item {
    }	
}
