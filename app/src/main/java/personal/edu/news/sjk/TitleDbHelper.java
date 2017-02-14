package personal.edu.news.sjk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class TitleDbHelper extends SQLiteOpenHelper {
    public TitleDbHelper(Context context) {
        super(context, "titleSjk.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //储存标签
        db.execSQL("create table title1(channelId tex,name text)");
        db.execSQL("create table title2(channelId tex,name text)");
        db.execSQL("create table score(name text)");
        db.execSQL("create table webview(name text,url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
