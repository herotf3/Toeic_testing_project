package com.example.huong.toeic_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.huong.toeic_project.classes.KetQua;

import java.util.ArrayList;

/**
 * Created by user on 7/5/2016.
 */
public class SQLForSave extends SQLiteOpenHelper {
    Context context;
    private static String DbName="data_save";
    public SQLForSave(Context _context)
    {
        super(_context,DbName,null,1);
        this.context=_context;
        getWritableDatabase();
    }
    private String TABLE_KETQUA="CREATE TABLE "+ KetQua.KQ_Tablename+"("+KetQua.KQ_id+" INTEGER PRIMARY KEY AUTOINCREMENT , "
            +KetQua.KQ_name + " TEXT , "
            +KetQua.KQ_Score + " INTEGER , "
            +KetQua.KQ_time + " TEXT ) ";

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_KETQUA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public ArrayList<KetQua> getKQ()  {
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<KetQua> arraylist=new ArrayList<KetQua>();
        String sql="SELECT * FROM " +KetQua.KQ_Tablename;
        Cursor cursor=db.rawQuery(sql,null);
        KetQua ketQua;

        if(cursor.moveToFirst()){
            do{
                ketQua=new KetQua();
                ketQua.setId(cursor.getInt(cursor.getColumnIndex(KetQua.KQ_id)));
                ketQua.setName(cursor.getString(cursor.getColumnIndex(KetQua.KQ_name)));
                ketQua.setScore(cursor.getInt(cursor.getColumnIndex(KetQua.KQ_Score)));
                String time=cursor.getString(cursor.getColumnIndex(KetQua.KQ_time));
                ketQua.setTime(time);
                arraylist.add(ketQua);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return arraylist;
    }

    public void InsertKQ(String name,String time,int score){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KetQua.KQ_time,time);
        contentValues.put(KetQua.KQ_Score,score);
        contentValues.put(KetQua.KQ_name,name);
        db.insert(KetQua.KQ_Tablename,null,contentValues);
        db.close();
    }
}
