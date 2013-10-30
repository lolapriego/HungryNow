package com.example.hungrynow;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class OrderDbAdapter {

	    public static final String KEY_PRODUCT = "product";
	    public static final String KEY_PRICE = "price";
	    public static final String KEY_NUMBER = "number";
	    public static final String KEY_TIME = "time";
	    public static final String KEY_NAME = "contact";
	    public static final String KEY_ROWID = "_id";

	    private static final String TAG = "OrderDbAdapter";
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;
	    
	    public static int count = 0;

	    /**
	     * Database creation statement
	     */
	    private static final String DATABASE_CREATE =
	        "create table orders (_id integer primary key autoincrement, "
	        + "product text not null, price text not null, number text not null, contact text not null, time text not null);"; 
	        		
	        //+ " time text not null);";
	    		
	    		
	    private static final String DATABASE_NAME = "data";
	    private static final String DATABASE_TABLE = "orders";
	    private static final int DATABASE_VERSION = 2;

	    private final Context mCtx;

	    private static class DatabaseHelper extends SQLiteOpenHelper {

	        DatabaseHelper(Context context) {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {

	            db.execSQL(DATABASE_CREATE);
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS notes");
	            onCreate(db);
	        }
	    }

	    /**
	     * Constructor - takes the context to allow the database to be
	     * opened/created
	     * 
	     * @param ctx the Context within which to work
	     */
	    public OrderDbAdapter(Context ctx) {
	        this.mCtx = ctx;
	    }

	    /**
	     * Open the notes database. If it cannot be opened, try to create a new
	     * instance of the database. If it cannot be created, throw an exception to
	     * signal the failure
	     * 
	     * @return this (self reference, allowing this to be chained in an
	     *         initialization call)
	     * @throws SQLException if the database could be neither opened or created
	     */
	    public OrderDbAdapter open() throws SQLException {
	        mDbHelper = new DatabaseHelper(mCtx);
	        mDb = mDbHelper.getWritableDatabase();
	        return this;
	    }

	    public void close() {
	        mDbHelper.close();
	    }


	    /**
	     * Create a new order. If the order is
	     * successfully created return the new rowId for that note, otherwise return
	     * a -1 to indicate failure.
	     * 
	     * @param title the title of the note
	     * @param body the body of the note
	     * @return rowId or -1 if failed
	     */
	    public long createOrder(mPFood list, String contactNumber) {
	        ContentValues initialValues = new ContentValues();
	        
	        for(int i = 0; i<list.mFood.length; i++){
	        	if (list.mFood[i].quantityL > 0){
			        initialValues.put(KEY_PRODUCT, list.mFood[i].name);
			        initialValues.put(KEY_PRICE, list.mFood[i].priceL);
			        initialValues.put(KEY_NUMBER, list.mFood[i].name);
	        	}
	        	if (list.mFood[i].quantityM > 0){
			        initialValues.put(KEY_PRODUCT, list.mFood[i].name);
			        initialValues.put(KEY_PRICE, list.mFood[i].priceM);
			        initialValues.put(KEY_NUMBER, list.mFood[i].name);
	        	}
	        	if (list.mFood[i].quantityS > 0){
			        initialValues.put(KEY_PRODUCT, list.mFood[i].name);
			        initialValues.put(KEY_PRICE, list.mFood[i].priceS);
			        initialValues.put(KEY_NUMBER, list.mFood[i].name);
	        	}
	        }
	        Calendar calendar = Calendar.getInstance();
	        
	        Log.i("CONTACTO NAME", contactNumber + "--------");
	        initialValues.put(KEY_NAME, contactNumber);
	        initialValues.put(KEY_TIME, "Day:" +  calendar.get(Calendar.DAY_OF_MONTH) + " Hour: " + calendar.get(Calendar.HOUR) + " Minute: " + calendar.get(Calendar.MINUTE));
	        
	        count++;
	        
	        return mDb.insert(DATABASE_TABLE, null, initialValues);
	    }

}
