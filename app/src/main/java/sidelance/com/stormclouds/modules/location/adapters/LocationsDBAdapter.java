package sidelance.com.stormclouds.modules.location.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class LocationsDBAdapter {

    private static final String TAG = LocationsDBAdapter.class.getSimpleName();

    private static final String ROW_ID = "ROW_ID";
    private static final String LOCATION_NAME = "LOCATION_NAME";
    private static final String DATE = "DATE";

    //SQL Commands
    private final static String CREATE_TABLE = "CREATE TABLE table1(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT NOT NUL, position TEXT NOT NULL);";

    //DB Props
    private static final String DB_NAME = "SavedLocations";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "table1";

    final Context mContext;
    SQLiteDatabase db;

    public LocationsDBAdapter(Context context) {
        this.mContext = context;


    }


    //Helper class
    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DB_NAME, factory, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
