package sak.mycontentprovider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

	protected static final String DATABASE_NAME = "mycontent.db";
	protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_NAME = "mycontent";
    protected static final String AUTHORITY = "sak.provider.mycontent";
    protected static final String LIVEFOLDER_NAME = "live_folders";		// For LIVE_FOLDER
    
    protected static final int ITEMS = 1;
    protected static final int ITEM_ID = 2;
    protected static final int LIVEFOLDER_ITEMS = 3;					// For LIVE_FOLDER
    
    protected static UriMatcher sUriMatcher;
    protected static HashMap<String, String> sProjectionMap;
    protected static HashMap<String, String> sLiveFolderProjectionMap;	// For LIVE_FOLDER
    
    
    public abstract static class MyColumns implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
        public static final Uri LF_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + LIVEFOLDER_NAME + "/" + TABLE_NAME);	// For LIVE_FOLDER
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY;

        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
//        public static final String ICON = "icon";
    }
    
    
	private static class MyDatabaseHelper extends SQLiteOpenHelper {

		MyDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			 db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
	                    + BaseColumns._ID + " INTEGER PRIMARY KEY,"
	                    + MyColumns.TITLE + " TEXT,"
	                    + MyColumns.DESCRIPTION + " TEXT"
	                    + ");");	
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        	onCreate(db);
		}
		
	}
	
	private MyDatabaseHelper databaseHelper;
	
	@Override
	public boolean onCreate() {
		databaseHelper = new MyDatabaseHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
       switch (sUriMatcher.match(uri)) {
        case ITEMS:
            return MyColumns.CONTENT_TYPE;
        case ITEM_ID:
            return MyColumns.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
        case ITEMS:
            qb.setTables(TABLE_NAME);
            qb.setProjectionMap(sProjectionMap);
            break;
        case ITEM_ID:
            qb.setTables(TABLE_NAME);
            qb.setProjectionMap(sProjectionMap);
            qb.appendWhere(BaseColumns._ID + "=" + uri.getPathSegments().get(1));
            break;
        case LIVEFOLDER_ITEMS:									// For LIVE_FOLDER
            qb.setTables(TABLE_NAME);							// For LIVE_FOLDER
            qb.setProjectionMap(sLiveFolderProjectionMap);		// For LIVE_FOLDER
            break;												// For LIVE_FOLDER
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != ITEMS) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (initialValues == null) {
        	return null;	// error
        }
        
        ContentValues values = new ContentValues(initialValues);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(TABLE_NAME, null, values);
        if (rowId <= 0) {
        	throw new SQLException("Failed to insert row into " + uri);
        }

        Uri contentUri = ContentUris.withAppendedId(MyColumns.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(contentUri, null);
        return contentUri;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
        case ITEMS:
            count = db.update(TABLE_NAME, values, where, whereArgs);
            break;
        case ITEM_ID:
            String itemId = uri.getPathSegments().get(1);
            count = db.update(TABLE_NAME, values, BaseColumns._ID + "=" + itemId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)) {
        case ITEMS:
            count = db.delete(TABLE_NAME, where, whereArgs);
            break;
        case ITEM_ID:
            String itemId = uri.getPathSegments().get(1);
            count = db.delete(TABLE_NAME, BaseColumns._ID + "=" + itemId
                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, ITEMS);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", ITEM_ID);
        sUriMatcher.addURI(AUTHORITY, LIVEFOLDER_NAME + "/" + TABLE_NAME, LIVEFOLDER_ITEMS);	// For LIVE_FOLDER

        sProjectionMap = new HashMap<String, String>();
        sProjectionMap.put(BaseColumns._ID, BaseColumns._ID);
        sProjectionMap.put(MyColumns.TITLE, MyColumns.TITLE);
        sProjectionMap.put(MyColumns.DESCRIPTION, MyColumns.DESCRIPTION);
        
        sLiveFolderProjectionMap = new HashMap<String, String>();			// For LIVE_FOLDER
        sLiveFolderProjectionMap.put(BaseColumns._ID, BaseColumns._ID);		// For LIVE_FOLDER
        sLiveFolderProjectionMap.put(MyColumns.TITLE, "title as name");		// For LIVE_FOLDER
        sLiveFolderProjectionMap.put(MyColumns.DESCRIPTION, "description as description");		// For LIVE_FOLDER
//        sLiveFolderProjectionMap.put(MyColumns.ICON, "icon as icon");		// For LIVE_FOLDER
    }
}
