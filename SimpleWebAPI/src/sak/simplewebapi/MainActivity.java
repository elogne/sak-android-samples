package sak.simplewebapi;

import java.util.List;

import sak.simplewebapi.MyParserBase.Item;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText mEditText1;
	private EditText mEditText2;
	private Button mButton01;
	
	private String result = "";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mEditText1 = (EditText) findViewById(R.id.EditText01);
        mEditText2 = (EditText) findViewById(R.id.EditText02);
        mButton01 = (Button) findViewById(R.id.Button01);
        
        mButton01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		    	String zipcode = mEditText2.getText().toString();
		    	String uri = "http://zip.cgis.biz/xml/zip.php?zn=" + zipcode;
		    	try {
					MyParser parser = new MyParser();
					parser.parse(uri);
					
					List<Item> items = parser.getItems();

					String state = "";
					String city = "";
					String address = "";
					for (Item item : items) {
						state = item.get("state");
						city = item.get("city");
						address = item.get("address");
					}
					result = state + city + address;
					
			        mEditText1.setText(result);
			        
		    	} catch (MyParserException e) {
					e.printStackTrace();
				}
			}
        });
    }
}