package sak.samples.hellofragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main extends Activity {

    private static final int MENU_FRAGMENT_0_0 = Menu.FIRST + 0;
    private static final int MENU_FRAGMENT_0_1 = Menu.FIRST + 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_FRAGMENT_0_0, 0, "Main_0")
            .setIcon(android.R.drawable.ic_menu_help)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(0, MENU_FRAGMENT_0_1, 0, "Main_1")
            .setIcon(android.R.drawable.ic_menu_compass)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        switch (item.getItemId()){
        case MENU_FRAGMENT_0_0:
            Toast.makeText(this, "MENU_FRAGMENT_0_0 が選択された。", Toast.LENGTH_LONG).show();
            break;
        case MENU_FRAGMENT_0_1:
            Toast.makeText(this, "MENU_FRAGMENT_0_1 が選択された。", Toast.LENGTH_LONG).show();
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }
        return result;
    }
}