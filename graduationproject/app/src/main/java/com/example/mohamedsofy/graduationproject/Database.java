package com.example.mohamedsofy.graduationproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;



public class Database extends SQLiteOpenHelper{

    private Context mycontext;

    private static final String database_name = "data_menu.db";
    private static final String table_name_menu1 = "login";
    private static final String create_table="CREATE TABLE "+table_name_menu1+
            "( id TEXT , username TEXT, email TEXT,  password TEXT,  status TEXT)";

    public Database(Context context) {
        super(context, database_name, null, 1);
        mycontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(create_table);

        }catch(Exception e) {
            Toast.makeText(mycontext, "error in create table"+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name_menu1);

    }

    // insert login row in database
    public boolean insert_status(String id,String username ,String email,String password,
                                 String status,String table_name)
    {

        SQLiteDatabase ss = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //"( id TEXT , username TEXT, email TEXT,  password TEXT,  status TEXT)";
        contentValues.put("id",id);
        contentValues.put("username",username);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("status",status);
        long result = ss.insert(table_name,null,contentValues);
        if (result == -1)
            return false;
        else {
            return true;
        }
    }
    // check user exist or not
    public boolean check_status(String table_name)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor re =sqLiteDatabase.rawQuery("select * from "+table_name, null);
        re.moveToFirst();
        String status ;

        while(re.isAfterLast()==false)
        {
            status=re.getString(4);
            if(status.equals("true"))
            {
                return true;
            }
            re.moveToNext();
        }
        return false;
    }


    // check user make login or not
    public boolean check_login(String username , String Password , String table_name){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor re =sqLiteDatabase.rawQuery("select * from "+table_name, null);
        re.moveToFirst();
        String name  , pass ;
        while(re.isAfterLast()==false)
        {
            name =re.getString(1);
            pass =re.getString(3);
            if(name.equals(username) && pass.equals(Password))
            {
                re_login("login");
                return true;
            }
            re.moveToNext();
        }
        return false;

    }


    // make logout
    public boolean logout_function(String table_name)
    {
        SQLiteDatabase aa = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("status","false");
        //aa.update(table_name, contentValues,"id="+i,null) ;
        aa.update(table_name, contentValues,null,null);

        return true;

    }

   public boolean re_login(String table_name){

       SQLiteDatabase aa = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();

       contentValues.put("status","true");
       //aa.update(table_name, contentValues,"id="+i,null) ;
       aa.update(table_name, contentValues,null,null);

       return true;

   }
}
