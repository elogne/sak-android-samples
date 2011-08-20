package sak.samples.hellofragment2;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MyFragment1 df1 = MyFragment1.newInstance();
        MyFragment2 df2 = MyFragment2.newInstance();
        MyFragment3 df3 = MyFragment3.newInstance();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        
        ft.replace(R.id.fragment1, df1);
        ft.replace(R.id.fragment2, df2);
        ft.replace(R.id.fragment3, df3);
        
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}