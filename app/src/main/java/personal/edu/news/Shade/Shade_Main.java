package personal.edu.news.Shade;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class Shade_Main {
    public static void setGuide(Context context, boolean guide){
        SharedPreferences spf=context.getSharedPreferences("Guide",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("mean",guide);
        ed.commit();
    }
    public static boolean getGuide(Context context){
        SharedPreferences spf=context.getSharedPreferences("Guide",Context.MODE_PRIVATE);
        return spf.getBoolean("mean",false);
    }

    public static void setadd(Context context,boolean guide){
        SharedPreferences spf=context.getSharedPreferences("title",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("mean",guide);
        ed.commit();
    }
    public static boolean getadd(Context context){
        SharedPreferences spf=context.getSharedPreferences("title",Context.MODE_PRIVATE);
        return spf.getBoolean("mean",false);
    }

    public static void setnews(Context context,int guide){
        SharedPreferences spf=context.getSharedPreferences("news",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putInt("mean",guide);
        ed.commit();
    }
    public static int getnews(Context context){
        SharedPreferences spf=context.getSharedPreferences("news",Context.MODE_PRIVATE);
        return spf.getInt("mean",0);
    }
    public static void setwidth(Context context,int guide){
        SharedPreferences spf=context.getSharedPreferences("width",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putInt("mean",guide);
        ed.commit();
    }
    public static int getwidth(Context context){
        SharedPreferences spf=context.getSharedPreferences("width",Context.MODE_PRIVATE);
        return spf.getInt("mean",0);
    }

    public static void setcourier(Context context,String guide){
        SharedPreferences spf=context.getSharedPreferences("courier",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString("mean",guide);
        ed.commit();
    }
    public static String getcourier(Context context){
        SharedPreferences spf=context.getSharedPreferences("courier",Context.MODE_PRIVATE);
        return spf.getString("mean","请选择快递公司");
    }
    public static void setcouriername(Context context,String guide){
        SharedPreferences spf=context.getSharedPreferences("couriername",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString("mean",guide);
        ed.commit();
    }
    public static String getcouriername(Context context){
        SharedPreferences spf=context.getSharedPreferences("couriername",Context.MODE_PRIVATE);
        return spf.getString("mean","请选择快递公司");
    }
}
