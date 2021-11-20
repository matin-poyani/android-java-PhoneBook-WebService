package Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ir.ncis.phonebook.App;

public class DB extends SQLiteOpenHelper {
    private static final String DB_NAME = "pb.sqlite";
    private static final ArrayList<String> TABLE_SCHEMA = new ArrayList<>();
    private static int DB_VERSION = 1;

    public DB() {
        super(App.CONTEXT, DB_NAME, null, DB_VERSION);
        TABLE_SCHEMA.add("CREATE  TABLE  IF NOT EXISTS \"persons\" (" +
                "\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "\"name\" TEXT NOT NULL  UNIQUE , " +
                "\"mobile\" TEXT, " +
                "\"address\" TEXT, " +
                "\"male\" BOOL, " +
                "\"friend\" BOOL, " +
                "\"score\" FLOAT," +
                "\"stored\" INTEGER" +
                ")");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        create(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        drop(db);
        create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        drop(db);
        create(db);
    }

    private void create(SQLiteDatabase db) {
        db.beginTransaction();
        for (String createTable : TABLE_SCHEMA) {
            db.execSQL(createTable);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS \"persons\"");
    }
}
