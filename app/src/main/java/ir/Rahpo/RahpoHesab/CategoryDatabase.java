package ir.Rahpo.RahpoHesab;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CategoryDatabase extends SQLiteOpenHelper {
    private Context context;
    public static final String DB_NAME = "CategoryDB";
    public static final int DB_VERSION = 1;
    public static final String NAME = "NAME";
    public static final String COMMISSION_PERCENT = "COMMISSION_PERCENT";
    public static final String PROCESS_PRICE = "PROCESS_PRICE";
    public CategoryDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
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

    public boolean queryDuplicateName(String name) {
        boolean result;
        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query("CATEGORY", new String[] {"NAME"} , "NAME = ?", new String[] {name}, null, null, null);
            if(cursor.moveToFirst()) {
                result = true;
                Toast.makeText(context, context.getString(R.string.error_duplicate_category_name), Toast.LENGTH_SHORT).show();
            } else {
                result = false;
            }
            cursor.close();
            db.close();

        } catch (SQLException e) {
            result = true;
            Toast.makeText(context, context.getString(R.string.error_defining_category), Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
