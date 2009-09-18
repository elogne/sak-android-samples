package sak.hookbrowser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent i = getIntent();
        String action = i.getAction();
        Log.d("debug", "action ==> " + action);

        String scheme = i.getScheme();
        Log.d("debug", "scheme ==> " + scheme);
        
        Uri uri = i.getData();
        if (uri != null) {
	        String host = uri.getHost();
	        Log.d("debug", "uri_host ==> " + host);

	        String flagment = uri.getFragment();
	        Log.d("debug", "uri_flagment ==> " + flagment);
	        
	        String query = uri.getQuery();
	        Log.d("debug", "uri_query ==> " + query);
	        
	        Log.d("debug", "uri_query_len = " + query.length());
        }
    }
}