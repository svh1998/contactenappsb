package nl.sandhoofd.contactenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by svanh on 2-10-2017.
 */

public class ContactDbHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "contacts";
    public static final int DB_VERSION = 2;
    public static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + ContactContract.ContactEntry.TABLE_NAME+
            "( " + ContactContract.ContactEntry.COL1  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            ContactContract.ContactEntry.COL2 + " TEXT, " + ContactContract.ContactEntry.COL3 + " TEXT, " +
            ContactContract.ContactEntry.COL4 + " TEXT, "+ ContactContract.ContactEntry.COL5+" BLOB);";
    public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME + ";";



    public ContactDbHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
        Log.d("DB OPer", "Database Created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Query", CREATE_QUERY + " is uitgevoerd");
        Log.d("DB OPer", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_QUERY);
        Log.d("DB OPer", "db updated");
    }

    public void putInformation(String name, String email, String phone, String image, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactContract.ContactEntry.COL2, name);
        contentValues.put(ContactContract.ContactEntry.COL3, email);
        contentValues.put(ContactContract.ContactEntry.COL4, phone);
        contentValues.put(ContactContract.ContactEntry.COL5, image);
        long l = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, contentValues);
        Log.d("DB OPer", "Row insterted");

    }

    public Cursor getInformation(SQLiteDatabase db) {
        Log.d("GET INFO", "opvragen info");
        String[] projection = {ContactContract.ContactEntry.COL1, ContactContract.ContactEntry.COL2, ContactContract.ContactEntry.COL3,
                ContactContract.ContactEntry.COL4, ContactContract.ContactEntry.COL5};
        Cursor cursor = db.query(ContactContract.ContactEntry.TABLE_NAME, projection, null,null,null,null,null);
        Log.d("GET INFO", "info opgevraagd");
        return cursor;

    }


}
