package sak.samples.activityalias;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class Main extends TabActivity {

	private static final String TAB_1 = "sak.samples.activityalias.Activity1Alias";
	private static final String TAB_2 = "sak.samples.activityalias.Activity2Alias";
	private static final String TAB_3 = "sak.samples.activityalias.Activity3Alias";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec(TAB_1)
                .setIndicator("タブ 1")
                .setContent(new Intent(this, Activity1.class)));

        tabHost.addTab(tabHost.newTabSpec(TAB_2)
                .setIndicator("タブ 2")
                .setContent(new Intent(this, Activity2.class)));

        tabHost.addTab(tabHost.newTabSpec(TAB_3)
                .setIndicator("タブ 3")
                .setContent(new Intent(this, Activity3.class)));

        String componentName = getIntent().getComponent().getClassName();
        if (TAB_1.equals(componentName)) {
            tabHost.setCurrentTabByTag(TAB_1);
        } else if (TAB_2.equals(componentName)) {
            tabHost.setCurrentTabByTag(TAB_2);
        } else if (TAB_3.equals(componentName)) {
            tabHost.setCurrentTabByTag(TAB_3);
        } else {
            tabHost.setCurrentTabByTag(TAB_1);
        }
    }
}