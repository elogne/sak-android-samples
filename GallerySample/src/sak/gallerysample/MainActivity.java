package sak.gallerysample;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Gallery g = (Gallery) findViewById(R.id.Gallery01);
        g.setAdapter(new ImageAdapter(this));
//        g.setSpacing(5);
//        g.setUnselectedAlpha(0.4f);
        g.setSelected(true);
        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
            }
        });
        
//        // Get a cursor with all people
//        Cursor c = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
//        startManagingCursor(c);
//        
//        SpinnerAdapter adapter = new SimpleCursorAdapter(this,
//        		android.R.layout.simple_gallery_item,
//        		c,
//        		new String[] {People.NAME},
//        		new int[] { android.R.id.text1 });
//        
//        Gallery g = (Gallery)findViewById(R.id.Gallery01);
//        g.setAdapter(adapter);
    }
    
    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        
        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray a = obtainStyledAttributes(R.styleable.G1);
            mGalleryItemBackground = a.getResourceId(
                    R.styleable.G1_android_galleryItemBackground, 0);
            a.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
//            ImageView imageView;
//            if (convertView == null) {
//                imageView = new ImageView(mContext);
//                imageView.setLayoutParams(new Gallery.LayoutParams(60,60));
//                imageView.setAdjustViewBounds(false);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setPadding(8,8,8,8);
//            } else {
//                imageView=(ImageView)convertView;
//            }
//            imageView.setImageResource(mImageIds[position]);
//            return imageView;
            
             ImageView i = new ImageView(mContext);

            i.setImageResource(mImageIds[position]);
//            i.setScaleType(ImageView.ScaleType.FIT_CENTER);
            i.setScaleType(ImageView.ScaleType.FIT_XY);
//            i.setLayoutParams(new Gallery.LayoutParams(136, 88));
//            i.setLayoutParams(new Gallery.LayoutParams(136, 88));
            i.setLayoutParams(new Gallery.LayoutParams(250, 250)); 
            
            // The preferred Gallery item background
            i.setBackgroundResource(mGalleryItemBackground);
            
            return i;
        }
        
        public float getScale(boolean focused, int offset) {
            /* Formula: 1 / (2 ^ offset) */
        	return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset)));
        } 
        
        private Context mContext;

        private Integer[] mImageIds = {
                android.R.drawable.ic_menu_add,
                android.R.drawable.ic_menu_agenda,
                android.R.drawable.ic_menu_always_landscape_portrait,
                android.R.drawable.ic_menu_call,
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_menu_close_clear_cancel,
                android.R.drawable.ic_menu_compass,
                android.R.drawable.ic_menu_crop,
                android.R.drawable.ic_menu_day,
                android.R.drawable.ic_menu_delete,
                android.R.drawable.ic_menu_directions,
                android.R.drawable.ic_menu_edit,
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_help,
                android.R.drawable.ic_menu_info_details,
                android.R.drawable.ic_menu_manage,
                android.R.drawable.ic_menu_mapmode,
                android.R.drawable.ic_menu_month,
                android.R.drawable.ic_menu_more,
                android.R.drawable.ic_menu_my_calendar,
                android.R.drawable.ic_menu_mylocation,
                android.R.drawable.ic_menu_myplaces,
                android.R.drawable.ic_menu_preferences,
                android.R.drawable.ic_menu_recent_history,
                android.R.drawable.ic_menu_report_image,
                android.R.drawable.ic_menu_revert,
                android.R.drawable.ic_menu_rotate,
                android.R.drawable.ic_menu_save,
                android.R.drawable.ic_menu_search,
                android.R.drawable.ic_menu_send,
                android.R.drawable.ic_menu_set_as,
                android.R.drawable.ic_menu_share,
                android.R.drawable.ic_menu_slideshow,
                android.R.drawable.ic_menu_sort_alphabetically,
                android.R.drawable.ic_menu_sort_by_size,
                android.R.drawable.ic_menu_today,
                android.R.drawable.ic_menu_upload,
                android.R.drawable.ic_menu_upload_you_tube,
                android.R.drawable.ic_menu_view,
                android.R.drawable.ic_menu_week,
                android.R.drawable.ic_menu_zoom,
        };
    }
}