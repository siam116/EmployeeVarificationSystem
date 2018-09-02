package com.example.ssiam.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Mysql extends SQLiteOpenHelper {
    private static final String DATABSE_NAME = "Employee_details.db";
    private static final String TABLE_NAME = "Employee_details";
    private static final int VERSION_NUMBER = 1;
    private static final String NAME = "EmployeeName";
    private static final String TYPE = "EmployeeType";
    private static final String ID = "EmployeeId";
    private static final String ADDRESS = "EmployeeAddress";
    private static final String DATE = "EmployeeDateofJoining";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + NAME + " VARCHAR(20)," + TYPE + " VARCHAR(30)," + ID + " VARCHAR(20) PRIMARY KEY," + ADDRESS + " VARCHAR(100)," + DATE + " VARCHAR(20));";
    private Context context;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;


    public Mysql(Context context) {
        super(context, DATABSE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Toast.makeText(context, "Table is Created! ", Toast.LENGTH_LONG).show();

            sqLiteDatabase.execSQL(CREATE_TABLE);
        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            Toast.makeText(context, "OnUpgrade is called ! ", Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();

        }

    }

    public long insertData(String name, String type, String id, String address, String date) {

        SQLiteDatabase sqlitedatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(TYPE, type);
        contentValues.put(ID, id);
        contentValues.put(ADDRESS, address);
        contentValues.put(DATE, date);

        long rowid = sqlitedatabase.insert(TABLE_NAME, null, contentValues);

    return rowid;

    }

  public Cursor  displayAllData()
    {
        SQLiteDatabase sqlitedatabase = this.getWritableDatabase();
        Cursor cursor= sqlitedatabase.rawQuery(SELECT_ALL,null);
        return cursor;

    }

    public boolean updatedata( String name,String type,String id ,String address,String date)
    {
        SQLiteDatabase sqlitedatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(TYPE, type);
        contentValues.put(ID, id);
        contentValues.put(ADDRESS, address);
        contentValues.put(DATE, date);

        sqlitedatabase.update(TABLE_NAME,contentValues,ID+" = ?",new String[]{id});


    return true;
    }

    public int deletedata(String id)
    {
        SQLiteDatabase sqlitedatabase = this.getWritableDatabase();
       return sqlitedatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});

    }


    public Boolean findPassword(String uname, String pass) {


        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME , null);
        System.out.println(cursor.getCount());
        Boolean result= false;
        if (cursor.getCount()==0) {
            Toast.makeText(context, "NO DATA FOUND", Toast.LENGTH_LONG).show();

        }
        else {

            while (cursor.moveToNext()) {

                String username = cursor.getString(0);
                String password = cursor.getString(2);

               // System.out.println(username);
                //System.out.println(password);

                if ((username.equals(uname))&&(password.equals(pass))) {
                    result = true;
                    break;

                }
            }


        }

        return result;

    }







}





