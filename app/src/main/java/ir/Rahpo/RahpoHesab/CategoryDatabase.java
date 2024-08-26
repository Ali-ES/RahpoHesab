package ir.Rahpo.RahpoHesab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoryDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "CategoryDB";
    public static final int DB_VERSION = 1;
    public CategoryDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CATEGORY(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "COMMISSION_PERCENT TEXT," +
                "PROCESS_PRICE TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
