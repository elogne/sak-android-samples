package sak.samples.hellofragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyFragment3 extends Fragment {

    private static final int MENU_FRAGMENT_3 = Menu.FIRST + 300;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setHasOptionsMenu(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment3, container, false);
        return v;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        menu.add(0, MENU_FRAGMENT_3, 0, "Fragment3")
            .setIcon(android.R.drawable.ic_menu_edit)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        switch (item.getItemId()){
        case MENU_FRAGMENT_3:
            Toast.makeText(getActivity(), "MENU_FRAGMENT_3 が選択された。", Toast.LENGTH_LONG).show();
            break;
        default:
            result = super.onOptionsItemSelected(item);
            break;
        }
        return result;
    }
}
