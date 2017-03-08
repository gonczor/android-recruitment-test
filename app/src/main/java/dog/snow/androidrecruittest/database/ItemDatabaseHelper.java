package dog.snow.androidrecruittest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dog.snow.androidrecruittest.model.Item;


public class ItemDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ITEM";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ICON = "ICON";
    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    public static final String COLUMN_URL = "URL";

    private static final int DB_VERSION = 1;
    private SQLiteDatabase database;

    private ItemDatabaseHelper(Context context,
                               String name,
                               SQLiteDatabase.CursorFactory factory,
                               int version) {
        super(context, name, factory, version);
    }

    private ItemDatabaseHelper(Context context,
                               String name,
                               SQLiteDatabase.CursorFactory factory,
                               int version,
                               DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ITEM " +
                "(_id INTEGER PRIMARY KEY, " +
                "NAME TEXT," +
                "DESCRIPTION TEXT," +
                "ICON TEXT," +
                "TIMESTAMP LONG," +
                "URL TEXT)");
    }

    public void openDatabase(){
        database = this.getWritableDatabase();
    }

    public void closeDatabase(){
        database.close();
    }

    public void insertItem(Item item){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", item.getId());
        contentValues.put("NAME", item.getName());
        contentValues.put("DESCRIPTION", item.getDescription());
        contentValues.put("ICON", item.getIcon());
        contentValues.put("TIMESTAMP", item.getTimestamp());
        contentValues.put("URL", item.getUrl());
        database.insert(DB_NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Builder{

        private Context context;
        private SQLiteDatabase.CursorFactory factory = null;
        private DatabaseErrorHandler errorHandler = null;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setFactory(SQLiteDatabase.CursorFactory factory) {
            this.factory = factory;
            return this;
        }

        public Builder setErrorHandler(DatabaseErrorHandler errorHandler) {
            this.errorHandler = errorHandler;
            return this;
        }

        public ItemDatabaseHelper build() {
            if(errorHandler == null)
                return new ItemDatabaseHelper(context, DB_NAME, factory, DB_VERSION);
            else
                return new ItemDatabaseHelper(context, DB_NAME, factory, DB_VERSION, errorHandler);
        }
    }
}
