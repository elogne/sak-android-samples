package sak.samples.draganddrop;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity {
    private DragAndDropView mView1;
    private DragAndDropView mView2;
    private MyAdapter mAdapter1;
    private MyAdapter mAdapter2;
    private ArrayList<Item> mItems1 = new ArrayList<Item>();
    private ArrayList<Item> mItems2 = new ArrayList<Item>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*
         * ５つの要素を用意する。
         */
        mItems1.add(new Item(R.drawable.icon, "1"));
        mItems1.add(new Item(R.drawable.icon, "2"));
        mItems1.add(new Item(R.drawable.icon, "3"));
        mItems1.add(new Item(R.drawable.icon, "4"));
        mItems1.add(new Item(R.drawable.icon, "5"));

        /*
         * 上の View の定義
         */
        mAdapter1 = new MyAdapter(this, mItems1);

        mView1 = (DragAndDropView) findViewById(R.id.list);
        mView1.setAdapter(mAdapter1);
        mView1.setOnDragnDropListener(new DragAndDropListener() {
            @Override
            public void dropped(int from, int x, int y) {
                Rect rect = new Rect();
                mView2.getHitRect(rect);
                if (rect.contains(x, y)) {
                    Item item = mAdapter1.getItem(from);
                    mAdapter1.remove(item);
                    mAdapter2.add(item);
                }
            }
        });

        /*
         * 下の View の定義
         */
        mAdapter2 = new MyAdapter(this, mItems2);

        mView2 = (DragAndDropView) findViewById(R.id.list2);
        mView2.setAdapter(mAdapter2);
        mView2.setOnDragnDropListener(new DragAndDropListener() {
            @Override
            public void dropped(int from, int x, int y) {
                Rect rect = new Rect();
                mView1.getHitRect(rect);
                if (rect.contains(x, y)) {
                    Item item = mAdapter2.getItem(from);
                    mAdapter2.remove(item);
                    mAdapter1.add(item);
                }
            }
        });
    }

    /*
     * 要素
     */
    private class Item {
        public int icon;        // リソースID
        public String name;
        Item(int icon, String name) {
            this.icon = icon;
            this.name = name;
        }
    }

    /*
     * 表示用アダプタ
     */
    private class MyAdapter extends ArrayAdapter<Item> {
        private LayoutInflater mInflater;
        private ArrayList<Item> items;

        public MyAdapter(Context context, ArrayList<Item> items) {
            super(context, R.layout.item, items);
            mInflater = LayoutInflater.from(context);
            this.items = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item, null);
                holder = new ViewHolder();
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.icon);
                holder.tvName = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ivIcon.setImageResource(items.get(position).icon);
            holder.tvName.setText(items.get(position).name);
            return convertView;
        }
    }

    class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
    }
}
