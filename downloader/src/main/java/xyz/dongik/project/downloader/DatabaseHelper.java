package xyz.dongik.project.downloader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dongik on 17. 12. 28.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DownloadTrial.db";
    public static final String TRIALS_TABLE_NAME = "trials";
    public static final String TRIALS_COLUMN_ID = "id";
    public static final String TRIALS_COLUMN_TOTAL = "total";
    public static final String TRIALS_COLUMN_SUCCESS = "success";
    public static final String TRIALS_COLUMN_FAILURE = "failure";
    public static final String TRIALS_COLUMN_URL = "url";
    private HashMap hp;

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table trials " +
                "(id integer primary key, url text,total integer,failure integer, success integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS trials");
        onCreate(db);
    }

    public boolean insertResult(Result result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIALS_COLUMN_URL,result.getUrl());
        contentValues.put(TRIALS_COLUMN_SUCCESS,result.getSuccess());
        contentValues.put(TRIALS_COLUMN_FAILURE,result.getSuccess());
        contentValues.put(TRIALS_COLUMN_TOTAL,result.getSuccess());
        db.insert(TRIALS_TABLE_NAME,null,contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from trials where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TRIALS_TABLE_NAME);
        return numRows;
    }

    public boolean updateTrials(int id ,Result result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIALS_COLUMN_URL, result.getUrl());
        contentValues.put(TRIALS_COLUMN_SUCCESS, result.getSuccess());
        contentValues.put(TRIALS_COLUMN_FAILURE, result.getFail());
        contentValues.put(TRIALS_COLUMN_TOTAL, result.getTotal());
        db.update(TRIALS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteTrials (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TRIALS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<Result> getAllTrials() {
        ArrayList<Result> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from trials", null );
        res.moveToFirst();
        Result result = new Result();
        while(res.isAfterLast() == false){
//            res.getString()
//            res.getString(TRIALS_COLUMN_URL);
            result.setUrl(res.getString(res.getColumnIndex(TRIALS_COLUMN_URL)));
            result.setSuccess(res.getInt(res.getColumnIndex(TRIALS_COLUMN_SUCCESS)));
            result.setFail(res.getInt(res.getColumnIndex(TRIALS_COLUMN_FAILURE)));
            array_list.add(result);
//            array_list.add(res.getString(res.getColumnIndex(TRIALS_COLUMN_URL)));
            res.moveToNext();
        }
        return array_list;
    }
}

