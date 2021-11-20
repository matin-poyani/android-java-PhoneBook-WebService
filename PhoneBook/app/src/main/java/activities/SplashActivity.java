package activities;

import android.os.Bundle;

import ir.ncis.phonebook.ActivityEnhanced;
import ir.ncis.phonebook.App;
import ir.ncis.phonebook.R;

public class SplashActivity extends ActivityEnhanced {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        App.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                App.startActivity(MainActivity.class, true);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        App.toast(getString(R.string.loading));
    }
}
