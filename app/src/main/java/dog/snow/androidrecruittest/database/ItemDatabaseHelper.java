package dog.snow.androidrecruittest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dog.snow.androidrecruittest.model.Item;


public class ItemDatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DB_NAME = "ITEM";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_ICON = "ICON";
    private static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    private static final String COLUMN_URL = "URL";
    private static final String TABLE_ITEM = "ITEM";

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
        contentValues.put(COLUMN_NAME, item.getName());
        contentValues.put(COLUMN_DESCRIPTION, item.getDescription());
        contentValues.put(COLUMN_ICON, item.getIcon());
        contentValues.put(COLUMN_TIMESTAMP, item.getTimestamp());
        contentValues.put(COLUMN_URL, item.getUrl());
        long result = database.insertWithOnConflict(TABLE_ITEM,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_IGNORE);

        if(result == -1){
            String[] ids = {item.getId().toString()};
            database.update(TABLE_ITEM, contentValues, "_id=?", ids);
        }
    }

    public List<Item> selectAllItems(){
        database = this.getReadableDatabase();
        Cursor cursor = this.database.query(TABLE_ITEM, new String[]{"*"}, null, null, null, null, null);
        List<Item> items = loadItems(cursor);
        if(!cursor.isClosed())
            cursor.close();

        database.close();
        return items;
    }

    public List<Item>selectMatchingItems(String searched){
        database = this.getReadableDatabase();
        Cursor cursor = this.database.query(TABLE_ITEM, new String[]{"*"}, null, null, null, null, null);
        List<Item> items = loadItems(cursor);
        if(!cursor.isClosed())
            cursor.close();

        database.close();
        return items;
    }

    private List<Item> loadItems(Cursor cursor){
        List<Item> items = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Item item = new Item.Builder()
                        .setId(cursor.getLong(0))
                        .setName(cursor.getString(1))
                        .setDescription(cursor.getString(2))
                        .setIcon(cursor.getString(3))
                        .setTimestamp(cursor.getLong(4))
                        .setUrl(cursor.getString(5))
                        .build();
                items.add(item);
            }while (cursor.moveToNext());
        }
        return items;
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
