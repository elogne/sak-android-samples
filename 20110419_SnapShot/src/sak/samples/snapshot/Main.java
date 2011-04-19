package sak.samples.snapshot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main extends Activity {

    private MyView mView;
    private Paint mPaint;
    private MyAdapter mAdapter;

    private ArrayList<Item> mItems = new ArrayList<Item>();

    private int ITEM_NUM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*
         * ギャラリー
         */
        final Gallery gallery = (Gallery)findViewById(R.id.gallery);

        mAdapter = new MyAdapter(this);
        gallery.setAdapter(mAdapter);

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filename = position + ".png";
                loadFromFile(filename);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int i=0; i<ITEM_NUM; i++) {
            mItems.add(new Item(i + ".png"));
        }

        /*
         * キャンバス
         */
        FrameLayout layout = (FrameLayout)findViewById(R.id.content);

        mView = new MyView(this);

        layout.addView(mView);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        /*
         * 操作ボタン
         */
        Button btnRed = (Button)findViewById(R.id.red);
        btnRed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPaint.setColor(Color.RED);
            }
        });

        Button btnGreen = (Button)findViewById(R.id.green);
        btnGreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPaint.setColor(Color.GREEN);
            }
        });

        Button btnBlue = (Button)findViewById(R.id.blue);
        btnBlue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPaint.setColor(Color.BLUE);
            }
        });

        Button btnClear = (Button)findViewById(R.id.clear);
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mView.clear();
            }
        });

        Button btnSnapshot = (Button)findViewById(R.id.snapshot);
        btnSnapshot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String filename = gallery.getSelectedItemPosition() + ".png";
                saveToFile(filename);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        for (int i=0; i<ITEM_NUM; i++) {
            String filename = i + ".png";
            deleteFile(filename);
        }

        mAdapter.notifyDataSetChanged();
    }

    private void saveToFile(String filename) {
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);

            mView.setDrawingCacheEnabled(false);
            mView.setDrawingCacheEnabled(true);
            Bitmap bitmap0 = Bitmap.createBitmap(mView.getDrawingCache());
            bitmap0.compress(CompressFormat.PNG, 100, out);

            /*
             * 画像を縮尺して保存する場合はコメントアウト
             */
//            float scale = 0.25f;
//
//            int width = bitmap0.getWidth();
//            int height = bitmap0.getHeight();
//
//            Matrix matrix = new Matrix();
//            matrix.postScale(scale, scale);
//
//            Bitmap bitmap = Bitmap.createBitmap(bitmap0, 0, 0, width, height, matrix, true);
//            bitmap.compress(CompressFormat.PNG, 100, out);

            out.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private void loadFromFile(String filename) {
        try {
            FileInputStream in = openFileInput(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            mView.drawBitmap(bitmap);
            in.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

    public class MyView extends View {

        private Bitmap  mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;

        private int width;
        private int height;;
        private int BGCOLOR = 0xFFAAAAAA;

        public MyView(Context c) {
            super(c);

            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            width = display.getWidth();
            height = display.getHeight();

            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(BGCOLOR);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        public void drawBitmap(Bitmap bitmap) {
            mCanvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
            invalidate();
        }

        public void clear() {
            Paint paint = new Paint();
            paint.setColor(BGCOLOR);
            mCanvas.drawRect(0, 0, width, height, paint);
            invalidate();
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
            }
        }
        private void touch_up() {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }

    private class Item {
        String icon;
        Item(String icon) {
            this.icon = icon;
        }
    };

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context context;

        public MyAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
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
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Bitmap image = null;
            InputStream in;
            try {
                in = context.openFileInput(mItems.get(position).icon);
                image = BitmapFactory.decodeStream(in);
                try {
                    in.close();
                } catch (IOException e) {}

            } catch (FileNotFoundException e) {}

            holder.ivIcon.setImageDrawable(new BitmapDrawable(image));

            return convertView;
        }

        class ViewHolder {
            ImageView ivIcon;
        }
    }
}


