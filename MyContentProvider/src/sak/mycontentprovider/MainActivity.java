package sak.mycontentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getIntent().setData(Uri.parse("content://sak.provider.mycontentprovider"));

        ContentValues values = new ContentValues();
        values.put("name", "Pen");
        values.put("description", "This is a pen");
        getContentResolver().insert(getIntent().getData(), values);

        Cursor cur = managedQuery(getIntent().getData(), null, null, null, null);
        while (cur.moveToNext()) {
            Log.d(cur.getString(1), cur.getString(2));
        }
        
        setContentView(R.layout.main);
    }
}