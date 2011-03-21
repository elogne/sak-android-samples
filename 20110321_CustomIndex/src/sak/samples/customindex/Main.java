package sak.samples.customindex;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.Groups;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity {

    private class MyItem {
        long id;
        String title;
        MyItem(long id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    private ArrayList<MyItem> mItems = new ArrayList<MyItem>();

    private TextView tvText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*
         * テキスト
         */
        tvText = (TextView)findViewById(R.id.text);

        /*
         * グループを追加する
         */
        String sort = Groups.TITLE + " DESC";

        Cursor c = getContentResolver().query(
                        Groups.CONTENT_URI,
                        new String[] {
                            Groups._ID,      // 0
                            Groups.TITLE,    // 1
                        },
                        null,
                        null,
                        sort);

        int firstPos = 0;
        while (c.moveToNext()) {
            long id = c.getLong(0);
            String title = c.getString(1);
            if (title.equals("Starred in Android")) {
                continue;    // skip
            } else if (title.startsWith("System Group: My Contacts")) {
                continue;    // skip
            } else if (title.startsWith("System Group: Friends")) {
                title = "友人";
            } else if (title.startsWith("System Group: Family")) {
                title = "家族";
            } else if (title.startsWith("System Group: Coworkers")) {
                title = "同僚";
            }
            mItems.add(new MyItem(id, title));
            firstPos ++;
        }
        c.close();

        /*
         * 全部、お気に入りを追加する
         */
        mItems.add(new MyItem(-1, "全部"));
        mItems.add(new MyItem(-2, "☆"));

        /*
         * あかさたなを追加する
         */
        mItems.add(new MyItem(-11, "あ"));
        mItems.add(new MyItem(-12, "か"));
        mItems.add(new MyItem(-13, "さ"));
        mItems.add(new MyItem(-14, "た"));
        mItems.add(new MyItem(-15, "な"));
        mItems.add(new MyItem(-16, "は"));
        mItems.add(new MyItem(-17, "ま"));
        mItems.add(new MyItem(-18, "や"));
        mItems.add(new MyItem(-19, "ら"));
        mItems.add(new MyItem(-20, "わ"));
        mItems.add(new MyItem(-21, "＃"));

        MyAdapter adapter = new MyAdapter(this);

        Gallery g = (Gallery) findViewById(R.id.gallery);

        g.setFocusableInTouchMode(true);
        g.setFadingEdgeLength(150);
        g.setCallbackDuringFling(false);

        g.setAdapter(adapter);

        g.setSelection(firstPos);    // setAdapter の後ろで指定する。

        g.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String title = mItems.get(position).title;
                tvText.setText(title);

                long _id = mItems.get(position).id;
                if (_id < -10) {
                    tvText.setBackgroundColor(Color.GREEN);
                } else if (_id < 0) {
                    tvText.setBackgroundColor(Color.BLUE);
                } else {
                    tvText.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item, null);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTitle.setText(mItems.get(position).title);

            return convertView;
        }

        class ViewHolder {
            TextView tvTitle;
        }
    }
}
