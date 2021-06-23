package hanu.a2_1801040189.data.dbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 2;


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBSchema.CartTable.TABLENAME + "("
                + DBSchema.CartTable.Cols.ID + " INTEGER PRIMARY KEY , " +
                DBSchema.CartTable.Cols.NAME + " TEXT, " +
                DBSchema.CartTable.Cols.THUMB + " TEXT, " +
                DBSchema.CartTable.Cols.PRICE + " INTEGER," +
                DBSchema.CartTable.Cols.QUAN + " INTEGER" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBSchema.CartTable.TABLENAME);
        onCreate(db);
    }


}
