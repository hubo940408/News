package personal.edu.news.sjk;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import personal.edu.news.entity.ViewPager_User;
import personal.edu.news.entity.WebView_User;


public class TitleExpress {
    Context context;
    SQLiteDatabase db;

    public TitleExpress(Context context) {
        this.context = context;
        TitleDbHelper titleDbHelper=new TitleDbHelper(context);
        db=titleDbHelper.getReadableDatabase();
    }

    public void addTo1(ViewPager_User viewPager_user){  //增加
        ContentValues cv=new ContentValues();//获得一个数据暂存器
        cv.put("channelId",viewPager_user.getChannelId());
        cv.put("name",viewPager_user.getName());
        db.insert("title1",null,cv);//第一个参数你要操作的表，第二个参数 是否有主键，第三个参数你存储的暂存器
    }

    public void remover1(String name){//删除
        db.delete("title1","name=?",new String[]{name});
        //第一个表名，第二个你要操作的列，第三个你操作的依据
    }
    public void removerAll(){//删除全部
        db.delete("title1",null,null);
    }
    public void addTo2(ViewPager_User viewPager_user){  //增加
        ContentValues cv=new ContentValues();//获得一个数据暂存器
        cv.put("channelId",viewPager_user.getChannelId());
        cv.put("name",viewPager_user.getName());
        db.insert("title2",null,cv);//第一个参数你要操作的表，第二个参数 是否有主键，第三个参数你存储的暂存器
    }

    public void remover2(String name){//删除
        db.delete("title2","name=?",new String[]{name});
        //第一个表名，第二个你要操作的列，第三个你操作的依据
    }
    public ArrayList<ViewPager_User> checkOne1(String name){//查询个人
        //先声明游标
        Cursor cu=null;
        ArrayList<ViewPager_User> stu;
        stu=new ArrayList<ViewPager_User>();
        cu= db.rawQuery("select * from title1 where name =?",new String[]{name});
        if(cu!=null){
            ViewPager_User st=null;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String channelId=cu.getString(cu.getColumnIndex("channelId"));
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                st= new ViewPager_User(channelId,namemsg);
                stu.add(st);
            }
        }
        return stu;
    }
    public ArrayList<ViewPager_User> checkOne2(String name){//查询个人
        //先声明游标
        Cursor cu=null;
        ArrayList<ViewPager_User> stu;
        stu=new ArrayList<ViewPager_User>();
        cu= db.rawQuery("select * from title2 where name =?",new String[]{name});
        if(cu!=null){
            ViewPager_User st=null;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String channelId=cu.getString(cu.getColumnIndex("channelId"));
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                st= new ViewPager_User(channelId,namemsg);
                stu.add(st);
            }
        }
        return stu;
    }

    public ArrayList<ViewPager_User> check1(){
        //先声明游标
        Cursor cu=null;
        ArrayList<ViewPager_User> stu=new ArrayList<ViewPager_User>();
        cu= db.rawQuery("select * from title1  ",null);
        if(cu!=null){
            ViewPager_User st=null;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String channelId=cu.getString(cu.getColumnIndex("channelId"));
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                st= new ViewPager_User(channelId,namemsg);
                stu.add(st);
            }
        }
        return stu;
    }

    public ArrayList<ViewPager_User> check2(){
        //先声明游标
        Cursor cu=null;
        ArrayList<ViewPager_User> stu=new ArrayList<ViewPager_User>();
        cu= db.rawQuery("select * from title2  ",null);
        if(cu!=null){
            ViewPager_User st=null;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String channelId=cu.getString(cu.getColumnIndex("channelId"));
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                st= new ViewPager_User(channelId,namemsg);
                stu.add(st);
            }
        }
        return stu;
    }

    /**
     * 增加搜索历史记录
     * @param string
     */
    public void addScore(String string){  //增加
        ContentValues cv=new ContentValues();//获得一个数据暂存器
        cv.put("name",string);
        db.insert("score",null,cv);//第一个参数你要操作的表，第二个参数 是否有主键，第三个参数你存储的暂存器
    }

    /**
     * 搜索历史记录
     * @return
     */
    public ArrayList<String> checkScore(){
        //先声明游标
        Cursor cu=null;
        ArrayList<String> stu=new ArrayList<>();
        cu= db.rawQuery("select * from score ",null);
        if(cu!=null){
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                stu.add(namemsg);
            }
        }
        return stu;
    }

    /**
     * 搜索单个历史记录
     * @param name
     * @return
     */
    public ArrayList<String> checkOneScore(String name){//查询个人
        //先声明游标
        Cursor cu=null;
        ArrayList<String> stu;
        stu=new ArrayList<String >();
        cu= db.rawQuery("select * from score where name =?",new String[]{name});
        if(cu!=null){
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                stu.add(namemsg);
            }
        }
        return stu;
    }

    /**
     * 收藏网页
     * @param webView_user
     */
    public void addwebview(WebView_User webView_user){  //增加
        ContentValues cv=new ContentValues();//获得一个数据暂存器
        cv.put("name",webView_user.getName());
        cv.put("url",webView_user.getUrl());
        db.insert("webview",null,cv);//第一个参数你要操作的表，第二个参数 是否有主键，第三个参数你存储的暂存器
    }

    /**
     * 查询收藏网页
     * @return
     */
    public ArrayList<WebView_User> checkwebview(){
        //先声明游标
        Cursor cu=null;
        ArrayList<WebView_User> stu=new ArrayList<>();
        cu= db.rawQuery("select * from webview ",null);
        if(cu!=null){
            WebView_User webView_user;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                String url=cu.getString(cu.getColumnIndex("url"));
                webView_user=new WebView_User(namemsg,url);
                stu.add(webView_user);
            }
        }
        return stu;
    }

    /**
     * 查询网页是否存在
     * @param url
     * @return
     */
    public ArrayList<WebView_User> check_webView_users(String url){
        //先声明游标
        Cursor cu=null;
        ArrayList<WebView_User> stu;
        stu=new ArrayList<WebView_User>();
        cu= db.rawQuery("select * from webview where url =?",new String[]{url});
        if(cu!=null){
            WebView_User webView_user;
            while(cu.moveToNext()){//游标依次下移
                //取出该行 name列的数据
                String namemsg=cu.getString(cu.getColumnIndex("name"));
                //先通过列名拿到那一列的下标，再通过那一列的下标拿到数据
                //产生一个对象
                webView_user=new WebView_User(namemsg,url);
                stu.add(webView_user);
            }
        }
        return stu;
    }

    /**
     * 删除收藏网页
     * @param url
     */
    public void removerwebview(String url){//删除个人
        db.delete("webview","url=?",new String[]{url});
        //第一个表名，第二个你要操作的列，第三个你操作的依据
    }

}