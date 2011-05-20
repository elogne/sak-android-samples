package sak.samples.multichoicelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

    private ListView lv;

    private static String[] mItems = {
        "01: 北海道", "02: 青森県", "03: 岩手県", "04: 宮城県", "05: 秋田県",
        "06: 山形県", "07: 福島県", "08: 茨城県", "09: 栃木県", "10: 群馬県",
        "11: 埼玉県", "12: 千葉県", "13: 東京都", "14: 神奈川県", "15: 新潟県",
        "16: 富山県", "17: 石川県", "18: 福井県", "19: 山梨県", "20: 長野県",
        "21: 岐阜県", "22: 静岡県", "23: 愛知県", "24: 三重県", "25: 滋賀県",
        "26: 京都府", "27: 大阪府", "28: 兵庫県", "29: 奈良県", "30: 和歌山県",
        "31: 鳥取県", "32: 島根県", "33: 岡山県", "34: 広島県", "35: 山口県",
        "36: 徳島県", "37: 香川県", "38: 愛媛県", "39: 高知県", "40: 福岡県",
        "41: 佐賀県", "42: 長崎県", "43: 熊本県", "44: 大分県", "45: 宮崎県",
        "46: 鹿児島県", "47: 沖縄県" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MyAdapter adapter = new MyAdapter(this);

        lv = (ListView)findViewById(R.id.list);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);

        Button btnOk = (Button)findViewById(R.id.ok);
        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                StringBuilder sb = new StringBuilder();
                sb.append("以下の都道府県が選択されました。\n");
                for (long id: lv.getCheckItemIds()) {
                    sb.append(mItems[(int)id]);
                    sb.append("\n");
                }
                String text = new String(sb);
                Toast.makeText(Main.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private int background_normal = Color.parseColor("#ff000000");
        private int background_selected = Color.parseColor("#44888888");

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mItems.length;
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
                holder.tvName = (TextView) convertView.findViewById(R.id.name);
                holder.tvCheck = (TextView) convertView.findViewById(R.id.check);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(mItems[position]);

            boolean checked = lv.isItemChecked(position);

            /*
             * チェックボックス画像
             */
            boolean buttonless = true;
            if (buttonless) {
                holder.tvCheck.setBackgroundResource(R.drawable.btn_check_buttonless_on);
                holder.tvCheck.setVisibility(checked ? View.VISIBLE : View.GONE);
            } else {
                holder.tvCheck.setBackgroundResource(
                        checked ? R.drawable.btn_check_on : R.drawable.btn_check_off);
            }

            /*
             * 背景色
             */
            convertView.setBackgroundColor(checked ? background_selected : background_normal);

            return convertView;
        }

        class ViewHolder {
            TextView tvName;
            TextView tvCheck;
        }
    }
}