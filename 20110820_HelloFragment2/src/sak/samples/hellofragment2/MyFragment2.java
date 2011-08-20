package sak.samples.hellofragment2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyFragment2 extends Fragment {

    private static final int MENU_FRAGMENT_2 = Menu.FIRST + 200;
    
    private static MyFragment2 instance = null;
    
    public static MyFragment2 newInstance() {
        if (instance == null) {
            instance = new MyFragment2();
        }
        return instance;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setHasOptionsMenu(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);
        return v;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        menu.add(0, MENU_FRAGMENT_2, 0, "Fragment2")
            .setIcon(android.R.drawable.ic_menu_add)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        switch (item.getItemId()){
        case MENU_FRAGMENT_2:
            Toast.makeText(getActivity(), "MENU_FRAGMENT_2 が選択された。", Toast.LENGTH_LONG).show();
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }
        return result;
    }
}
