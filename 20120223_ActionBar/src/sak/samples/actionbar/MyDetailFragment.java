package sak.samples.actionbar;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyDetailFragment extends Fragment {

    private int num = 0;
    private int color = Color.WHITE;
    
    public MyDetailFragment(int num, int color) {
        this.num = num;
        this.color = color;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        ActionBar actionBar = getActivity().getActionBar();   
        actionBar.setDisplayHomeAsUpEnabled(true);   
        
        setHasOptionsMenu(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail, container, false);
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        TextView tv = (TextView)getView().findViewById(R.id.text);
        tv.setText("Fragment " + num);
        tv.setTextColor(Color.WHITE);
        
        View frame = (View)getView().findViewById(R.id.frame);
        frame.setBackgroundColor(color);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();

        ActionBar actionBar = getActivity().getActionBar();   
        actionBar.setDisplayHomeAsUpEnabled(false);   
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.detail, menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ((Main)getActivity()).goHome();
        } else if (id == R.id.action_six) {
            Toast.makeText(getActivity(), "アクション「６」が選択されました", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_seven) {
            Toast.makeText(getActivity(), "アクション「７」が選択されました", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_eight) {
            Toast.makeText(getActivity(), "アクション「８」が選択されました", Toast.LENGTH_SHORT).show();
        }  else {
            return false;
        }
        return true;
    }
}
