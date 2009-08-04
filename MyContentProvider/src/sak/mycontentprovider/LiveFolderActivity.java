package sak.mycontentprovider;

import sak.mycontentprovider.MyContentProvider.MyColumns;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.LiveFolders;

public class LiveFolderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Intent intent = getIntent();
		if (intent != null) {
			final String action = intent.getAction();
			if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(action)) {
				Intent i = new Intent();
				i.setData(MyColumns.LF_CONTENT_URI);
				i.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT, new Intent(Intent.ACTION_VIEW, MyColumns.CONTENT_URI));
				i.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE, LiveFolders.DISPLAY_MODE_LIST);
				i.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, getResources().getString(R.string.live_folders_name));
				i.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON, Intent.ShortcutIconResource.fromContext(this, R.drawable.icon));
				setResult(RESULT_OK, i);
			} else {
				setResult(RESULT_CANCELED);
			}
		}
		finish();
	}
}
