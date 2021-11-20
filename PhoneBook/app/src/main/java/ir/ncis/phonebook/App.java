package ir.ncis.phonebook;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import CustomControls.CustomTextViewBold;
import Database.DB;

public class App extends Application {
    public static final Handler HANDLER = new Handler();
    public static final String URL_SERVICE = "http://192.168.1.103/phonebook/ws.php?";
    public static AppCompatActivity ACTIVITY;
    public static Context CONTEXT;
    public static LayoutInflater INFLATER;
    public static SQLiteDatabase DB;
    public static Typeface FONT_BOLD;
    public static Typeface FONT_NORMAL;

    public static void startActivity(Class targetActivity) {
        startActivity(targetActivity, false);
    }

    public static void startActivity(Class targetActivity, boolean finish) {
        Intent intent = new Intent(ACTIVITY, targetActivity);
        ACTIVITY.startActivity(intent);
        if (finish) {
            ACTIVITY.finish();
        }
    }

    public static void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    public static void toast(String message, int duration) {
        View view = INFLATER.inflate(R.layout.toast, null);
        CustomTextViewBold txtMessage = (CustomTextViewBold) view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);
        Toast toast = new Toast(CONTEXT);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static String timeStamp() {
        return String.valueOf(System.currentTimeMillis()).substring(0, 10);
    }

    public static int getColorCompact(int colorId) {
        return ContextCompat.getColor(CONTEXT, colorId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        DB = new DB().getWritableDatabase();
        INFLATER = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        AssetManager assetManager = getAssets();
        FONT_BOLD = Typeface.createFromAsset(assetManager, "font/IRANSans_Bold.ttf");
        FONT_NORMAL = Typeface.createFromAsset(assetManager, "font/IRANSans_Normal.ttf");
    }
}
