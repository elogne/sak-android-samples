package sak.samples.actionbar;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends Activity {

    private FragmentManager fm;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        getActionBar().setTitle("ActionBar");
        
        MyListFragment f = new MyListFragment();
        
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, f, "container");
        
        ft.commit();
    }
    
    public void goDetail(int num, int color) {
        FragmentTransaction ft = fm.beginTransaction();
        
        MyDetailFragment f = new MyDetailFragment(num, color);
        ft.replace(R.id.container, f);
        ft.addToBackStack(null);
        
        ft.commit();
    }
    
    public void goHome() {
        fm.popBackStack();
    }
}